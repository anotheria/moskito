<%@ page language="java" contentType="text/comma-separated-values;charset=UTF-8" session="false" isELIgnored="false"
%><%@ taglib uri="http://www.anotheria.net/ano-tags" prefix="ano"
%>"id";"name";"path";"values";"lasttimestamp"
<ano:iterate name="accumulators" type="net.anotheria.moskito.webui.accumulators.api.AccumulatorDefinitionAO" id="accumulator"
>"${accumulator.id}";"${accumulator.name}";"${accumulator.path}";"${accumulator.numberOfValues}";"${accumulator.lastValueTimestamp}"
</ano:iterate>
