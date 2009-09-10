<%@ page language="java" contentType="text/html;charset=iso-8859-15" session="true"
%><%@ taglib uri="/tags/struts-bean" prefix="bean" 
%><%@ taglib uri="/tags/struts-logic" prefix="logic" 
%><%@ page isELIgnored ="false" 
%>
<div id="Interval" class="selector">
Interval: <select id="IntervalSelector">
<logic:iterate name="intervals" id="interval" type="net.java.dev.moskito.webui.bean.IntervalBean">
	<option value="<msk:write name="interval" property="name"/>" ${currentInterval == interval.name?'selected':''}><msk:write name="interval" property="name"/></option>
</logic:iterate>
</select>
</div>

<script>
YAHOO.util.Event.on("IntervalSelector", "change", function(event){
	var value = event.target.options[event.target.selectedIndex].value;
	Moskito.setRequestedInterval(value);
	Moskito.update();
},this, true);
</script>