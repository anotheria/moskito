<%@ page language="java" contentType="text/html;charset=iso-8859-15" session="true"
%><%@ taglib uri="/tags/struts-bean" prefix="bean" 
%><%@ taglib uri="/tags/struts-logic" prefix="logic" 
%><%@ page isELIgnored ="false" 
%>
<bean:define id="currentUnit" name="moskito.CurrentUnit" property="unitName"/>
<div id="TimeUnit" class="selector">
Time Unit: <select id="TimeUnitsSelector">
<logic:iterate name="units" id="unit" type="net.java.dev.moskito.webui.bean.UnitBean">
	<option value="<bean:write name="unit" property="unitName"/>" ${currentUnit == unit.unitName?'selected':''}><bean:write name="unit" property="unitName"/></option>
</logic:iterate>
</select>
</div>

<script>
YAHOO.util.Event.on("TimeUnitsSelector", "change", function(event){
	var value = event.target.options[event.target.selectedIndex].value;
	Moskito.setRequestedTimeUnit(value);
	Moskito.update();
},this, true);
</script>