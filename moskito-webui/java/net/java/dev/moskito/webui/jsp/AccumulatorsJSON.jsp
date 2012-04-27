<%@ page language="java" contentType="application/json;charset=UTF-8" session="true"
%><%@ taglib uri="http://www.anotheria.net/ano-tags" prefix="ano"
%>{
<ano:present name="data">
	"data":  [<ano:iterate name="data" id="value" indexId="i"><ano:notEqual name="i" value="0">,</ano:notEqual><ano:write name="value"/></ano:iterate>];
	"accNames": [<ano:iterate name="accNames" type="java.lang.String" id="name" indexId="i"><ano:notEqual name="i" value="0">,</ano:notEqual>"<ano:write name="name"/>"</ano:iterate>], 
</ano:present>
	
	"accumulators": [
		<ano:iterate name="accumulators" type="net.java.dev.moskito.webui.bean.AccumulatorInfoBean" id="accumulator" indexId="index">
		<ano:notEqual name="index" value="0">,</ano:notEqual>
		{
		"id": "<ano:write name="accumulator" property="id"/>",
		"name": "<ano:write name="accumulator" property="name"/>",
		"path": "<ano:write name="accumulator" property="path"/>",
		"values": "<ano:write name="accumulator" property="numberOfValues"/>",
		"lasttimestamp": "<ano:write name="accumulator" property="lastValueTimestamp"/>"
		}
		</ano:iterate>]
}