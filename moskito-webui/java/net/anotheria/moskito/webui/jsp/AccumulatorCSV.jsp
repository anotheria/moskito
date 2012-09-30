<%@ page language="java" contentType="text/comma-separated-values;charset=UTF-8" session="false"
%><%@ taglib uri="http://www.anotheria.net/ano-tags" prefix="ano"
%>"timestamp";"isoTimestamp";"value"
<ano:iterate name="accumulatorData" property="data" id="row" indexId="ind"
>"<ano:write name="row" property="timestamp"/>";"<ano:write name="row" property="isoTimestamp"/>";"<ano:write name="row" property="firstValue"/>"
</ano:iterate>