package net.java.dev.moskito.webcontrol.ui.action;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.anotheria.maf.action.ActionForward;
import net.anotheria.maf.action.ActionMapping;
import net.anotheria.maf.bean.FormBean;
import net.java.dev.moskito.webcontrol.IOUtils;
import net.java.dev.moskito.webcontrol.configuration.ConfigurationRepository;
import net.java.dev.moskito.webcontrol.configuration.SourceConfiguration;
import net.java.dev.moskito.webcontrol.configuration.ViewField;
import net.java.dev.moskito.webcontrol.feed.FeedGetter;
import net.java.dev.moskito.webcontrol.feed.HttpGetter;
import net.java.dev.moskito.webcontrol.ui.MoskitoWebcontrolUIFilter;
import net.java.dev.moskito.webcontrol.ui.beans.PatternWithName;

import org.json.JSONObject;
import org.w3c.dom.Document;

public class ViewConfigAction extends BaseMoskitoWebcontrolAction {

	@Override
	public ActionForward execute(ActionMapping mapping, FormBean form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String interval = null;
		List<String> intervals = ConfigurationRepository.INSTANCE.getIntervalsNames();
		if (intervals.size() > 0) {
			interval = intervals.size() > 0 ? intervals.get(0) : "default";
		}

		SourceConfiguration source = null;
		List<SourceConfiguration> sourceList = ConfigurationRepository.INSTANCE.getSources();
		if (sourceList.size() > 0) {
			source = sourceList.get(0);
		} else {
			throw new RuntimeException("there is no any server config");
		}

		List<ViewField> paths = fillPaths(source, interval);

		return mapping.findForward("success");
	}

	public static List<ViewField> fillPaths(SourceConfiguration source, String interval) throws Exception {
		List<ViewField> res = new ArrayList<ViewField>();
		FeedGetter getter = new HttpGetter();
		SourceConfiguration sourceConf = new SourceConfiguration(source.getName(), source.getUrl() + "&pInterval=" + interval);
		Document doc = getter.retreive(sourceConf);
		
		InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("views.json");
		String content = IOUtils.getInputStreamAsString(is);
		JSONObject views = new JSONObject(content);
		
		JSONObject baseColumn = views.getJSONObject("baseColumn");
		
		ViewField baseField = ConfigurationRepository.prepareField(baseColumn);
		
		List<PatternWithName> result = new ArrayList<PatternWithName>();
		result.add(new PatternWithName("", baseField.getPath()));
		MoskitoWebcontrolUIFilter.buildFullPath(result, doc);
		for (PatternWithName p : result) {
			ViewField config = (ViewField)baseField.clone();
			config.setAttributeName(p.getFieldName());
			config.setFieldName(p.getFieldName());
			config.setPath(p.getPattern());
			res.add(config);
		}
		return res;
	}

}
