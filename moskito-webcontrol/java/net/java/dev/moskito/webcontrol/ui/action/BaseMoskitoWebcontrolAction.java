package net.java.dev.moskito.webcontrol.ui.action;

import java.text.DecimalFormat;
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
import net.java.dev.moskito.webcontrol.guards.Condition;
import net.java.dev.moskito.webcontrol.repository.Attribute;
import net.java.dev.moskito.webcontrol.repository.Repository;
import net.java.dev.moskito.webcontrol.repository.Snapshot;
import net.java.dev.moskito.webcontrol.repository.SnapshotSource;
import net.java.dev.moskito.webcontrol.ui.beans.AttributeBean;
import net.java.dev.moskito.webcontrol.ui.beans.ColumnBean;
import net.java.dev.moskito.webcontrol.ui.beans.OrderedSourceAttributesBean;
import net.java.dev.moskito.webcontrol.ui.beans.ViewTable;

public abstract class BaseMoskitoWebcontrolAction implements Action {

	protected static final String REQUEST_PARAM_VIEW_NAME = "pViewName";
	protected static final String REQUEST_PARAM_INTERVAL = "pInterval";

	protected static final String REQUEST_PARAM_SORT_BY = "pFilterSortBy";
	protected static final String REQUEST_PARAM_SORT_ORDER = "pFilterSortOrder";

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

		view.addRowName(new ColumnBean(SERVER_COLUMN_NAME, SERVER_COLUMN_NAME));
		for (ViewField field : fields) {
			view.addRowName(new ColumnBean(field.getFieldName(), field.getAttributeName()));
		}

		for (SourceConfiguration source : sources) {
			OrderedSourceAttributesBean attrsBean = new OrderedSourceAttributesBean(source.getName());
			List<AttributeBean> values = new ArrayList<AttributeBean>(fields.size());
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
					AttributeBean bean = new AttributeBean();

					if (field.getFormat() != null && att != null && att.getValue() instanceof Number) {
						DecimalFormat format = (DecimalFormat) DecimalFormat.getInstance();
						format.applyPattern(field.getFormat());
						bean.setValue(att == null || att.getValue() == null ? "n.a." : format.format(((Number) att.getValue()).doubleValue()));
					} else {
						bean.setValue(att == null || att.getValue() == null ? "n.a." : att.getValueString());
					}

					bean.setColor(att == null || att.getValue() == null ? Condition.DEFAULT.getColor() : att.getCondition().getColor());
					values.add(bean);
				} else {
					AttributeBean bean = new AttributeBean();
					bean.setValue("");
					bean.setColor(Condition.DEFAULT.getColor());
					values.add(bean);
				}
			}
			attrsBean.setAttributeValues(values);
			view.addValueBean(attrsBean);
		}
		return view;
	}

}
