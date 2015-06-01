package pack;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * <code>\\build-apps.eng.vmware.com\apps\bin\gobuild target info phonehome --branch main --output json</code>
 * <p>
 * in order for the gobuild command to work you will have to execute <br>
 * <code>\\build-apps.eng.vmware.com\apps\bin\p4_login --all -user rburov --password yourPassGoesHere</code>
 *
 */
public class GobuildTargetInfoCaller {
	public static InputStream call(String target, String branch) throws IOException {

		File fOut = File.createTempFile("gobuild-target-info", "_stdout");
		File fErr = File.createTempFile("gobuild-target-info", "_stderr");
		String folder = "P:\\bin";
		String cmd = "gobuild.cmd";
		FileOutputStream stdout = new FileOutputStream(fOut);
		FileOutputStream stderr = new FileOutputStream(fErr);
		try {
			File buildappsbin = new File(folder);
			if (!buildappsbin.isDirectory())
				throw new IllegalArgumentException(buildappsbin.getAbsolutePath() + " is not a folder");

			File gobuild = new File(buildappsbin, cmd);
			if (!gobuild.isFile())
				throw new IllegalArgumentException(gobuild.getAbsolutePath() + " is not a file");

			ShellCommand.execute(buildappsbin, new String[] { cmd, "target", "info", target, "--branch", branch,
					"--output", "json" }, stdout, stderr, null);
			stdout.flush();
			stderr.flush();
		} finally {
			stdout.close();
			stderr.close();
		}
		return new FileInputStream(fOut);
	}
}
