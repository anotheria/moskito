<%@ page language="java" contentType="text/comma-separated-values;charset=UTF-8" session="false"
%><%@ taglib uri="http://www.anotheria.net/ano-tags" prefix="ano"
%>"id";"name";"path", "values", "lasttimestamp"
<ano:iterate name="accumulators" type="net.java.dev.moskito.webui.bean.AccumulatorInfoBean" id="accumulator" indexId="index">"<
   ano:write name="accumulator" property="id"/>";"<
   ano:write name="accumulator" property="name"/>";"<
   ano:write name="accumulator" property="path"/>";"<
   ano:write name="accumulator" property="numberOfValues"/>";"<
   ano:write name="accumulator" property="lastValueTimestamp"/>"
</ano:iterate>
