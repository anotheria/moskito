package net.anotheria.moskito.webcontrol.ui.beans;

import java.util.ArrayList;
import java.util.List;

import net.anotheria.moskito.webcontrol.guards.Condition;
import net.anotheria.util.StringUtils;

public class ViewTable {

    private final String viewName;

    private Condition color = Condition.GREEN;

    private List<ColumnBean> rowNames;

    private List<OrderedSourceAttributesBean> values;

    public ViewTable(String viewName) {
	this.viewName = viewName;
    }

    public String getViewName() {
	return viewName;
    }

    public void setRowNames(List<ColumnBean> rowNames) {
	this.rowNames = rowNames;
    }

    public List<ColumnBean> getRowNames() {
	return rowNames;
    }

    public void setValues(List<OrderedSourceAttributesBean> values) {
	this.values = values;
    }

    public List<OrderedSourceAttributesBean> getValues() {
	return values;
    }

    public boolean addRowName(ColumnBean rowName) throws Exception {
	if (rowNames == null) {
	    rowNames = new ArrayList<ColumnBean>();
	}
	return rowNames.add(rowName);
    }

    public boolean addValueBean(OrderedSourceAttributesBean bean) throws Exception {
	if (values == null) {
	    values = new ArrayList<OrderedSourceAttributesBean>();
	}
	return values.add(bean);
    }

    public void setColor(String aColor) {
	color = Condition.getValueByColor(aColor);
	for (OrderedSourceAttributesBean osab : values) {
	    for (AttributeBean abean : osab.getAttributeValues()) {
		if (StringUtils.isEmpty(abean.getColor()))
		    continue;

		Condition colorCondition = Condition.getValueByColor(abean.getColor());

		if (color.getPriority() > colorCondition.getPriority())
		    color = colorCondition;

		if (color == Condition.RED)
		    return;
	    }
	}
    }

    public Condition getColor() {
	return color;
    }

}
