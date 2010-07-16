package net.java.dev.moskito.webcontrol.ui.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.anotheria.maf.action.Action;
import net.anotheria.maf.action.ActionMapping;
import net.java.dev.moskito.webcontrol.configuration.ConfigurationRepository;
import net.java.dev.moskito.webcontrol.configuration.SourceConfiguration;
import net.java.dev.moskito.webcontrol.configuration.ViewConfiguration;
import net.java.dev.moskito.webcontrol.configuration.ViewField;
import net.java.dev.moskito.webcontrol.repository.Attribute;
import net.java.dev.moskito.webcontrol.repository.Repository;
import net.java.dev.moskito.webcontrol.repository.Snapshot;
import net.java.dev.moskito.webcontrol.repository.SnapshotSource;
import net.java.dev.moskito.webcontrol.ui.beans.OrderedSourceAttributesBean;
import net.java.dev.moskito.webcontrol.ui.beans.ViewTable;

public abstract class BaseMoskitoWebcontrolAction implements Action {
	
	protected static final String REQUEST_PARAM_VIEW_NAME = "pViewName";
	protected static final String REQUEST_PARAM_INTERVAL = "pInterval";

	protected static final String SERVER_COLUMN_NAME = "Source";

	protected String getLinkToCurrentPage(HttpServletRequest req) {
		return "";
	}

	@Override
	public void postProcess(ActionMapping mapping, HttpServletRequest req, HttpServletResponse res) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void preProcess(ActionMapping mapping, HttpServletRequest req, HttpServletResponse res) throws Exception {
		// TODO Auto-generated method stub

	}

	protected ViewTable prepareView(String viewName, String interval) throws Exception {
		ViewTable view = new ViewTable(viewName);
		
		String containerName = ConfigurationRepository.INSTANCE.getContainerName(interval);

		ViewConfiguration config = ConfigurationRepository.INSTANCE.getView(viewName);
		List<SourceConfiguration> sources = ConfigurationRepository.INSTANCE.getSources();
		List<ViewField> fields = config.getFields();

		view.addRowName(SERVER_COLUMN_NAME);
		for (ViewField field : fields) {
			view.addRowName(field.getFieldName());
		}

		for (SourceConfiguration source : sources) {
			OrderedSourceAttributesBean attrsBean = new OrderedSourceAttributesBean(source.getName());
			List<String> values = new ArrayList<String>(fields.size());
			Snapshot snapshot = null;
			try {
				// create snapshot
				snapshot = Repository.INSTANCE.getSnapshot(containerName, SnapshotSource.valueOf(source));
			} catch (Exception e) {
				attrsBean.setAvailable(false);
				// TODO log
			}
			for (ViewField field : fields) {
				if (snapshot != null) {
					Attribute att = snapshot.getAttribute(field.getAttributeName());
					values.add(att == null ? "n.a." : att.getValueString());
				} else {
					values.add("");
				}
			}
			attrsBean.setAttributeValues(values);
			view.addValueBean(attrsBean);
		}
		return view;
	}

}
