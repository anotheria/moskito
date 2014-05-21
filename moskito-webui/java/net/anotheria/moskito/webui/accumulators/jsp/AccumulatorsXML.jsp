<%@ page language="java" contentType="text/xml;charset=UTF-8" session="false" isELIgnored="false"
%><%@ taglib uri="http://www.anotheria.net/ano-tags" prefix="ano"
%><?xml version="1.0" encoding="UTF-8"?>
<accumulators>
    <ano:iterate name="accumulators" type="net.anotheria.moskito.webui.accumulators.api.AccumulatorDefinitionAO" id="accumulator">
        <accumulator>
            <id>${accumulator.id}</id>
            <name>${accumulator.name}</name>
            <path>${accumulator.path}</path>
            <numberOfValues>${accumulator.numberOfValues}</numberOfValues>
            <lastValueTimestamp>${accumulator.lastValueTimestamp}</lastValueTimestamp>
        </accumulator>
    </ano:iterate>
</accumulators>
