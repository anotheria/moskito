package net.anotheria.moskito.webui.util;

import net.anotheria.util.maven.MavenVersion;
import net.anotheria.util.maven.MavenVersionReader;

import javax.servlet.ServletContext;
import java.io.File;

/**
 * Copied this file from ano-web because it was the single dependency.
 *
 * @author lrosenberg
 * @since 26.04.16 09:55
 */
public class VersionUtil {
	public static MavenVersion getWebappVersion(ServletContext context){
		//this is pretty hacky now.
		String pathToMetaInf = context.getRealPath("/META-INF");
		return MavenVersionReader.findVersionInDirectory(new File(pathToMetaInf));
	}

	public static MavenVersion getWebappLibVersion(ServletContext context, String libname){
		String pathToLib = context.getRealPath("/WEB-INF/lib");
		return MavenVersionReader.findJarInDirectory(new File(pathToLib), libname);
	}

}
