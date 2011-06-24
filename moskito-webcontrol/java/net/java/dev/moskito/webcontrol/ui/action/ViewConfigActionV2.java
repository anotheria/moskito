package net.java.dev.moskito.webcontrol.ui.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.json.JSONObject;

import net.anotheria.maf.action.ActionForward;
import net.anotheria.maf.action.ActionMapping;
import net.anotheria.maf.bean.FormBean;
import net.anotheria.util.StringUtils;
import net.java.dev.moskito.webcontrol.configuration.ConfigurationRepository;
import net.java.dev.moskito.webcontrol.configuration.ViewConfiguration;
import net.java.dev.moskito.webcontrol.configuration.ViewField;
import net.java.dev.moskito.webcontrol.repository.TotalFormulaType;

public class ViewConfigActionV2 extends BaseMoskitoWebcontrolAction {
	
	private final static Logger log = Logger.getLogger(ViewConfigActionV2.class);

	@Override
	public ActionForward execute(ActionMapping mapping, FormBean form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String action = request.getParameter("action");
		if (!StringUtils.isEmpty(action) && "save".equalsIgnoreCase(action)) {
			JSONObject views = new JSONObject("{" + request.getParameter("json") + "}");
			ConfigurationRepository.INSTANCE.updateViews(views);
		}

		List<ViewField> columns = ConfigurationRepository.INSTANCE.getAvailableColumns();

		log.debug("columns = " + columns);

		List<String> allCategories = ConfigurationRepository.INSTANCE.getAvailableCategories();
		
		log.debug("categories = " + allCategories);
		
		List<ViewConfiguration> views = ConfigurationRepository.INSTANCE.getAllViews();
		for (ViewConfiguration view : views) {
			for (ViewField field : view.getFields()) {
				for (ViewField column : columns) {
					if (field.getPath().equalsIgnoreCase(column.getPath())) {
						field.setCategory(column.getCategory());
						field.setAttributeName(column.getAttributeName());
					}
				}
			}
		}

		ViewConfiguration currentViewConfiguration = views.get(0);

		if (request.getSession().getAttribute("currentViewConfiguration") != null) {
			currentViewConfiguration = (ViewConfiguration) request.getSession().getAttribute("currentViewConfiguration");
		}

		if (request.getParameter("currentViewConfiguration") != null) {
			if (ConfigurationRepository.INSTANCE.getView(request.getParameter("currentViewConfiguration")) == null) {
				currentViewConfiguration = new ViewConfiguration(request.getParameter("currentViewConfiguration"));
			}
			currentViewConfiguration = ConfigurationRepository.INSTANCE.getView(request.getParameter("currentViewConfiguration"));
		}
		// if (request.getParameter("views")!=null){
		// for (String str:(String[])request.getParameterMap().get("views")){
		// if (views.indexOf(str)==-1){
		// ConfigurationRepository.INSTANCE.addView(new ViewConfiguration(str));
		// views = ConfigurationRepository.INSTANCE.getAllViews();
		// }
		// }
		// }

		List<ViewField> tempColumns = new ArrayList<ViewField>();
		int i = 0;
		String[] tempString;

		if (request.getParameter("path") != null) {
			for (String str : (String[]) request.getParameterMap().get("path")) {
				for (ViewField vf : columns) {
					if (vf.getPath().equalsIgnoreCase(str)) {
						ViewField tempField = new ViewField("", "");
						tempField.setAttributeName(vf.getAttributeName());
						tempString = (String[]) request.getParameterMap().get("fieldName");
						tempField.setFieldName(tempString[i]);
						tempString = (String[]) request.getParameterMap().get("visible");
						tempField.setVisible(!StringUtils.isEmpty(tempString[i]) ? Boolean.valueOf(tempString[i].toLowerCase()) : false);

						tempString = (String[]) request.getParameterMap().get("javaType");
						tempField.setJavaType(tempString[i]);
						tempString = (String[]) request.getParameterMap().get("total");
						for (TotalFormulaType tf : TotalFormulaType.values()) {
							if (tempString[i].equalsIgnoreCase(tf.name())) {
								tempField.setTotal(tf);
							}
						}
						tempString = (String[]) request.getParameterMap().get("guard");
						tempField.setGuard(vf.getGuard());
						tempString = (String[]) request.getParameterMap().get("path");
						tempField.setPath(vf.getPath());
						tempString = (String[]) request.getParameterMap().get("type");
						tempField.setType(vf.getType());
						tempString = (String[]) request.getParameterMap().get("format");
						tempField.setFormat(tempString[i]);
						tempColumns.add(tempField);
					}
				}
				i++;
				views.get(views.indexOf(currentViewConfiguration)).clear();
			}
			// if (tempColumns!=null){
			// for (ViewField tf:tempColumns){
			// System.out.println("attributeName:"+tf.getAttributeName()+" fieldName:"+tf.getFieldName()+" visible:"+tf.getVisible()+" javaType:"+tf.getJavaType()+" total:"+tf.getTotal()+" guard:"+tf.getGuard()+" path:"+tf.getPath()+" type:"+tf.getType()+" format:"+tf.getFormat());
			// }
			// }

			for (ViewField tf : tempColumns) {
				// System.out.println(tf.getAttributeName()+" is added or rewritten!");
				views.get(views.indexOf(currentViewConfiguration)).addField(tf);
			}

		}
		request.setAttribute("availableColumns", columns);
		request.setAttribute("allCategories", allCategories);
		request.setAttribute("views", views);

		request.getSession().setAttribute("currentViewConfiguration", currentViewConfiguration);

		return mapping.findForward("success");
	}

}
