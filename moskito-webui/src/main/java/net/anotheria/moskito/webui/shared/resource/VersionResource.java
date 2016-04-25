package net.anotheria.moskito.webui.shared.resource;

import net.anotheria.util.maven.MavenVersion;
import net.anotheria.webutils.util.VersionUtil;

import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 28.06.15 00:17
 */
@Path("version")
public class VersionResource extends AbstractResource{

	@GET
	public ReplyObject getVersion(@Context ServletContext context){
		try{
			MavenVersion moskitoVersion = VersionUtil.getWebappLibVersion(context, "moskito-webui");
			if (moskitoVersion == null)
				return ReplyObject.error("Can't read version");
			return ReplyObject.success("version", moskitoVersion);
		}catch(Exception any){
			return ReplyObject.error(any);
		}
	}
}
