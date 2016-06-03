package net.anotheria.moskito.webui.more.action;

import net.anotheria.maf.action.ActionCommand;
import net.anotheria.maf.action.ActionMapping;
import net.anotheria.maf.bean.FormBean;
import net.anotheria.moskito.webui.shared.bean.NaviItem;
import net.anotheria.util.NumberUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * This action checks if there is a newer version.
 *
 * @author lrosenberg
 * @since 13.03.13 23:51
 */
public class UpdateAction extends BaseAdditionalAction {

	/**
	 * Url to load from central.
	 */
	private String MAVEN_URL = "http://search.maven.org/solrsearch/select?q=a:%22moskito-webui%22&rows=20&wt=json";

	@Override
	protected String getLinkToCurrentPage(HttpServletRequest req) {
		return null;  //To change body of implemented methods use File | Settings | File Templates.
	}

	@Override
	public ActionCommand execute(ActionMapping actionMapping, FormBean formBean, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {


		String version = "notFound";
		long timestamp = 0;
		try{
			URL url = new URL(MAVEN_URL);
			URLConnection con = url.openConnection();
			InputStream in = con.getInputStream();
			StringBuilder jsonReply = new StringBuilder();
			while(in.available()>0){
				jsonReply.append((char)in.read());
			}
			JSONObject obj = new JSONObject(jsonReply.toString());
			JSONObject response = obj.getJSONObject("response");
			JSONArray docs = response.getJSONArray("docs");
			JSONObject artifact = docs.getJSONObject(0);
			timestamp = artifact.getLong("timestamp");
			version = artifact.getString("latestVersion");

		}catch(Exception e){
			version = "ERROR: "+e.getMessage();
		}

		httpServletRequest.setAttribute("versionTimestamp", NumberUtils.makeISO8601TimestampString(timestamp));
		httpServletRequest.setAttribute("version",   version);

		return actionMapping.success();
	}
	@Override
	protected NaviItem getCurrentSubNaviItem() {
		return NaviItem.MORE_UPDATE;
	}

}
