<%@ page language="java" contentType="text/html;charset=iso-8859-15" session="true"
%><%@ taglib uri="/tags/struts-bean" prefix="bean" 
%><%@ taglib uri="/tags/struts-logic" prefix="logic" 
%><%@ page isELIgnored ="false" 
%>
<div id="Subsystem" class="selector">
Subsystems: <select id="SubsystemSelector">
	<option value="">none</option>
	<logic:iterate name="subsystems" id="subsystem" type="net.java.dev.moskito.webui.bean.UnitCountBean">
	<option value="<msk:write name="subsystem" property="unitName"/>" ${currentSubsystem == subsystem.unitName?'selected':''}><msk:write name="subsystem" property="unitName"/>(<msk:write name="subsystem" property="unitCount"/>)</option>
	</logic:iterate>
</select>
</div>

<script>
YAHOO.util.Event.on("SubsystemSelector", "change", function(event){
	var value = event.target.options[event.target.selectedIndex].value;
	Moskito.setRequestedSubsystem(value);
	Moskito.update();
},this, true);
</script>

