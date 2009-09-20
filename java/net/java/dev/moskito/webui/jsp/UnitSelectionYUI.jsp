<%@ page language="java" contentType="text/html;charset=UTF-8" session="true"
%><%@ taglib uri="/tags/moskito" prefix="msk" 
%><%@ page isELIgnored ="false" 
%>
<msk:define id="currentUnit" name="moskito.CurrentUnit" property="unitName"/>
<div id="TimeUnit" class="selector">
Time Unit: <select id="TimeUnitsSelector">
<msk:iterate name="units" id="unit" type="net.java.dev.moskito.webui.bean.UnitBean">
	<option value="<msk:write name="unit" property="unitName"/>" ${currentUnit == unit.unitName?'selected':''}><msk:write name="unit" property="unitName"/></option>
</msk:iterate>
</select>
</div>

<script>
YAHOO.util.Event.on("TimeUnitsSelector", "change", function(event){
	var value = event.target.options[event.target.selectedIndex].value;
	Moskito.setRequestedTimeUnit(value);
	Moskito.update();
},this, true);
</script>