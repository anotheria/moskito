<%@ page trimDirectiveWhitespaces="true" %>
<%@ page language="java" contentType="application/json;charset=UTF-8" session="true"%>
<%@ taglib uri="http://www.anotheria.net/ano-tags" prefix="ano"%>
{
    "query": "CumulatedProducers",
    "interval": "<ano:write name="currentInterval"/>",
    "timestamp": "<ano:write name="timestamp"/>",
    "date": "<ano:write name="timestampAsDate"/>",

    "decorator": {
        "name": "<ano:write name="decorator" property="name"/>",
        "stats": [
            <ano:iterate name="decorator" property="producers" id="producer" type="net.anotheria.moskito.webui.producers.api.ProducerAO" lastId="isLastProducer">
            <ano:iterate name="producer" property="lines" id="statLine" type="net.anotheria.moskito.webui.producers.api.StatLineAO" lastId="isLastStat">
            {
                "name": "<ano:write name="statLine" property="statName"/>",
                "producerId": "<ano:write name="producer" property="producerId" />",
                "values": [
                    <ano:iterate name="statLine" property="values" id="value" type="net.anotheria.moskito.core.decorators.value.StatValueAO" lastId="isLastValue">
                    {
                        "name": "<ano:write name="value" property="name" />",
                        "type": "<ano:write name="value" property="type" />",
                        "value": "<ano:write name="value" property="value" />"
                    }<ano:iF test="${!isLastValue}">,</ano:iF>
                    </ano:iterate>
                ]
            }<ano:iF test="${!isLastStat || !isLastProducer || decorator.cumulatedStat != null}">,</ano:iF>
            </ano:iterate>
            </ano:iterate>

            <ano:present name="decorator" property="cumulatedStat">
            <ano:define id="statLine" name="decorator" property="cumulatedStat" type="net.anotheria.moskito.webui.producers.api.StatLineAO"/>
            {
                "name": "<ano:write name="statLine" property="statName"/>",
                "values": [
                    <ano:iterate name="statLine" property="values" id="value" type="net.anotheria.moskito.core.decorators.value.StatValueAO" lastId="isLastValue">
                    {
                        "name": "<ano:write name="value" property="name" />",
                        "type": "<ano:write name="value" property="type" />",
                        "value": "<ano:write name="value" property="value" />"
                    }<ano:iF test="${!isLastValue}">,</ano:iF>
                    </ano:iterate>
                ]
            }
            </ano:present>
        ]
    }
}
