<%@ page language="java" contentType="text/html;charset=UTF-8" session="true"
%><%@ taglib uri="http://www.anotheria.net/ano-tags" prefix="msk" 
%><%@ page isELIgnored ="false" 
%>
<div id="Subsystem" class="selector">
Subsystems: <select id="SubsystemSelector">
	<option value="">none</option>
	<msk:iterate name="subsystems" id="subsystem" type="net.java.dev.moskito.webui.bean.UnitCountBean">
	<option value="<msk:write name="subsystem" property="unitName"/>" ${currentSubsystem == subsystem.unitName?'selected':''}><msk:write name="subsystem" property="unitName"/>(<msk:write name="subsystem" property="unitCount"/>)</option>
	</msk:iterate>
</select>
</div>

<script>
YAHOO.util.Event.on("SubsystemSelector", "change", function(event){
	var value = event.target.options[event.target.selectedIndex].value;
	Moskito.setRequestedSubsystem(value);
	Moskito.update();
},this, true);
</script>

