<%@ page language="java" contentType="application/json;charset=UTF-8" session="true" 
%><%@ taglib uri="http://www.anotheria.net/ano-tags" prefix="ano" 
%>
{
	"intervals": [<ano:iterate name="intervals" id="interval" type="net.java.dev.moskito.webui.bean.IntervalBean" indexId="intIndex"><ano:notEqual name="intIndex" value="0">,</ano:notEqual>"<ano:write name="interval" property="name"/>"</ano:iterate>],
   	"units": [<ano:iterate name="units" id="unit" type="net.java.dev.moskito.webui.bean.UnitBean" indexId="unitIndex"><ano:notEqual name="unitIndex" value="0">,</ano:notEqual>"<ano:write name="unit" property="unitName"/>"</ano:iterate>],
  	"producers": [
    <ano:iterate name="producerAndTypes" id="producer" type="net.java.dev.moskito.webui.bean.ProducerAndTypeBean" indexId="producersIndex"><ano:notEqual name="producersIndex" value="0">,</ano:notEqual>
		{
			"id": "<ano:write name="producer" property="producerId"/>", 
			"type": "<ano:write name="producer" property="type"/>",
			"statNames": [<ano:iterate name="producer" property="statNames" type="java.lang.String" id="statName" indexId="statIndex"><ano:notEqual name="statIndex" value="0">,</ano:notEqual>"<ano:write name="statName"/>"</ano:iterate>]
		}<%--
    --%></ano:iterate>
	],
	"types": [
    <ano:iterate name="typeAndValueNames" id="type" type="net.java.dev.moskito.webui.bean.TypeAndValueNamesBean" indexId="typeIndex"><ano:notEqual name="typeIndex" value="0">,</ano:notEqual>
    	{
    	    "name": "<ano:write name="type" property="type"/>",
			"valueNames": [<ano:iterate name="type" property="valueNames" type="java.lang.String" id="valueName" indexId="valueindex"><ano:notEqual name="valueindex" value="0">,</ano:notEqual>"<ano:write name="valueName"/>"</ano:iterate>]
		}<%--
    --%></ano:iterate>
	]
   
}