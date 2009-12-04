<%@ page language="java" contentType="text/html;charset=UTF-8" session="true"
%><%@ taglib uri="http://www.anotheria.net/ano-tags" prefix="msk" 
%><%@ page isELIgnored ="false" 
%>
<div id="Category" class="selector">
Categories: <select id="CategorySelector">
	<option value="">none</option>
	<msk:iterate name="categories" id="category" type="net.java.dev.moskito.webui.bean.UnitCountBean">
	<option value="<msk:write name="category" property="unitName"/>" ${currentCategory == category.unitName?'selected':''}><msk:write name="category" property="unitName"/>(<msk:write name="category" property="unitCount"/>)</option>
	</msk:iterate>
</select>
</div>

<script>
YAHOO.util.Event.on("CategorySelector", "change", function(event){
	var value = event.target.options[event.target.selectedIndex].value;
	Moskito.setRequestedCategory(value);
	Moskito.update();
},this, true);
</script>