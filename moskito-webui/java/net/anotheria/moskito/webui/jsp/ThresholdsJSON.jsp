<%@ page language="java" contentType="application/json;charset=UTF-8" session="true"
%><%@ taglib uri="http://www.anotheria.net/ano-tags" prefix="ano"
%>{
"system": {
	"color": "<ano:write name="systemStatusColor"/>",
	"state": "<ano:write name="systemStatus"/>"
},
"setups":[<%--
	--%><ano:iterate name="infos" type="net.java.dev.moskito.webui.bean.ThresholdInfoBean" id="info" indexId="i"><%--
	--%><ano:notEqual name="i" value="0">,</ano:notEqual><ano:write name="value"/>
	{
	 	"id": "<ano:write name="info" property="id"/>",
		"name": "<ano:write name="info" property="name"/>",
		"producerName": "<ano:write name="info" property="producerName"/>",
		"statName": "<ano:write name="info" property="statName"/>",
		"valueName": "<ano:write name="info" property="valueName"/>",
		"intervalName": "<ano:write name="info" property="intervalName"/>",
		"descriptionString": "<ano:write name="info" property="descriptionString"/>",
		"guards": [<ano:iterate name="info" property="guards" id="guard" type="java.lang.String">"<ano:write name="guard"/>",</ano:iterate>""]
	}<%--
--%></ano:iterate>],
"thresholds":[
	<ano:iterate name="thresholds" type="net.java.dev.moskito.webui.bean.ThresholdBean" id="threshold" indexId="i">
		<ano:notEqual name="i" value="0">,</ano:notEqual><ano:write name="value"/>
	{
		"setup": "setup<ano:write name="threshold" property="id"/>",
		"color": "<ano:write name="threshold" property="colorCode"/>",
		"status": "<ano:write name="threshold" property="status"/>",
		"value": "<ano:write name="threshold" property="value"/>",
		"change": "<ano:write name="threshold" property="change"/>", 
		"timestamp": "<ano:write name="threshold" property="timestamp"/>", 
		"description": "<ano:write name="threshold" property="description"/>"
	}
	</ano:iterate>],
"alerts": [
	<ano:iterate name="alerts" type="net.java.dev.moskito.webui.bean.ThresholdAlertBean" id="alert" indexId="i">
		<ano:notEqual name="i" value="0">,</ano:notEqual><ano:write name="value"/>
	{
		"timestamp": "<ano:write name="alert" property="timestamp"/>",
		"name": "<ano:write name="alert" property="name"/>",
		"oldStatus": "<ano:write name="alert" property="oldStatus"/>",
		"newStatus": "<ano:write name="alert" property="newStatus"/>",
		"oldValue": "<ano:write name="alert" property="oldValue"/>",
		"newValue": "<ano:write name="alert" property="newValue"/>"
	}
	</ano:iterate>]
}