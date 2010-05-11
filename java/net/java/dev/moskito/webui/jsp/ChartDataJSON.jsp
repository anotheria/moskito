<%@ page language="java" contentType="application/json;charset=UTF-8" session="true" 
%><%@ taglib uri="http://www.anotheria.net/ano-tags" prefix="msk" 
%>{
	"interval": "<msk:write name="interval"/>",
	"unit": "<msk:write name="unit"/>",
	"data": [<msk:iterate name="data" id="entity" type="net.java.dev.moskito.webui.bean.ChartDataEntityBean" indexId="i"><msk:notEqual name="i" value="0">,</msk:notEqual>{
	"producerId": "<msk:write name="entity" property="producerId"/>", 
	"statName": "<msk:write name="entity" property="statName"/>",
	"statValueName": "<msk:write name="entity" property="statValueName"/>",
	"statValue": <msk:write name="entity" property="statValue"/>
  }</msk:iterate>]
} 