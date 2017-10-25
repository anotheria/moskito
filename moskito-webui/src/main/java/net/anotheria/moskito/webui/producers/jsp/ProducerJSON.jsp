<%@ page trimDirectiveWhitespaces="true" %>
<%@ page import="net.anotheria.moskito.core.decorators.value.StatCaptionBean"%>
<%@ page language="java" contentType="application/json;charset=UTF-8" session="true"%>
<%@ taglib uri="http://www.anotheria.net/ano-tags" prefix="ano"%>
{
    "query": "Producer",
    "interval": "<ano:write name="currentInterval"/>",
    "timestamp": "<ano:write name="timestamp"/>",
    "date": "<ano:write name="timestampAsDate"/>",

    "producerId": "<ano:write name="producer" property="producerId"/>",
    "category": "<ano:write name="producer" property="category"/>",
    "subsystem": "<ano:write name="producer" property="subsystem"/>",
    "class": "<ano:write name="producer" property="producerClassName"/>",

    "stats": [
        <ano:iterate name="decorators" id="decorator" type="net.anotheria.moskito.webui.shared.bean.StatDecoratorBean" lastId="isLastDecorator">
            <ano:iterate name="decorator" property="stats" id="statBean" type="net.anotheria.moskito.webui.shared.bean.StatBean" lastId="isLastStat">
            {
                "name": "<ano:write name="statBean" property="name"/>",
                "values": [
                <ano:define name="decorator" property="captions" type="java.util.List" id="captions" />
                <ano:iterate name="statBean" property="values" id="value" type="net.anotheria.moskito.core.decorators.value.StatValueAO" indexId="ind" lastId="isLastValue">
                    {
                        "name": "<%= ((StatCaptionBean)captions.get(ind.intValue())).getCaption() %>",
                        "type": "<ano:write name="value" property="type" />",
                        "value": "<ano:write name="value" property="value" />"
                    }<ano:iF test="${!isLastValue}">,</ano:iF>
                </ano:iterate>
                ]
            }<ano:iF test="${!isLastStat || !isLastDecorator}">,</ano:iF>
            </ano:iterate>
        </ano:iterate>
    ]
}
