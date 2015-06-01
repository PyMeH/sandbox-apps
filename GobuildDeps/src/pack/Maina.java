package pack;

import java.io.InputStream;
import java.util.List;

import pack.GobuildTargetInfoParser.GobuildComponent;

public class Maina {

	public static void main(String[] args) throws Throwable {
		InputStream in = GobuildTargetInfoCaller.call("phonehome", "main");
		//FileInputStream in = new FileInputStream("C:\\Users\\rou\\Desktop\\1.json");
		List<GobuildComponent> dependencies = new GobuildTargetInfoParser().readDependencies(in);
		System.out.println(dependencies);
	}

}
