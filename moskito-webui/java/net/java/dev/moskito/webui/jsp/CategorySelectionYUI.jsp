<%@ page language="java" contentType="text/html;charset=iso-8859-15" session="true"
%><%@ taglib uri="/tags/moskito" prefix="bean" 
%><%@ taglib uri="/tags/struts-logic" prefix="logic" 
%><%@ page isELIgnored ="false" 
%>
<div id="Category" class="selector">
Categories: <select id="CategorySelector">
	<option value="">none</option>
	<logic:iterate name="categories" id="category" type="net.java.dev.moskito.webui.bean.UnitCountBean">
	<option value="<msk:write name="category" property="unitName"/>" ${currentCategory == category.unitName?'selected':''}><msk:write name="category" property="unitName"/>(<msk:write name="category" property="unitCount"/>)</option>
	</logic:iterate>
</select>
</div>

<script>
YAHOO.util.Event.on("CategorySelector", "change", function(event){
	var value = event.target.options[event.target.selectedIndex].value;
	Moskito.setRequestedCategory(value);
	Moskito.update();
},this, true);
</script>