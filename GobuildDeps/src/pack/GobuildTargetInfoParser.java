package pack;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * Convert the result of gobuild-target-info command to a list of gobuild
 * dependencies
 * <p>
 * <code>\\build-apps.eng.vmware.com\apps\bin\gobuild target info phonehome --branch main --output json</code>
 * <p>
 * in order for the gobuild command to work you will have to execute <br>
 * <code>\\build-apps.eng.vmware.com\apps\bin\p4_login --all -user rburov --password yourPassGoesHere</code>
 *
 */
public class GobuildTargetInfoParser {
	public static class GobuildComponent {

		public GobuildComponent(String target, String branch, String type, String cl) {
			this.target = target;
			this.branch = branch;
			this.type = type;
			this.cl = cl;
		}

		public final String target;// identity-server
		public final String branch;// lotus-2013
		public final String type;// release
		public final String cl;// 226580

		@Override
		public String toString() {
			return target + " / " + branch + " / " + type + " @" + cl;
		}

	}

	ObjectMapper mapper = new ObjectMapper();

	public List<GobuildComponent> readDependencies(InputStream json) {
		List<GobuildComponent> list = new LinkedList<GobuildComponent>();

		JsonNode rootNode;
		try {
			rootNode = mapper.readTree(json);
			ObjectNode components = (ObjectNode) rootNode.path("components");
			Iterator<Entry<String, JsonNode>> fields = components.fields();
			while (fields.hasNext()) {
				Entry<String, JsonNode> c = fields.next();
				String target = c.getKey();
				String branch = c.getValue().path("branch").asText();// lotus-2013
				String cl = c.getValue().path("change").asText();// 226580
				String type = c.getValue().path("buildtype").asText();// release

				GobuildComponent gc = new GobuildComponent(target, branch, type, cl);
				list.add(gc);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return list;

	}
}
