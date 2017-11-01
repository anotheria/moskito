<%@ page trimDirectiveWhitespaces="true" %>
<%@ page language="java" contentType="application/msexcel;charset=UTF-8" session="true" isELIgnored="false" %>
<%@ taglib uri="http://www.anotheria.net/ano-tags" prefix="ano" %><%--
--%>"ProducerId";"StatName"<%--
--%><ano:iterate name="decorator" property="captions" type="net.anotheria.moskito.core.decorators.value.StatCaptionBean" id="caption"><%--
--%>;"${caption.caption}"<%--
--%></ano:iterate><%--

--%><ano:iterate name="decorator" property="producers" id="producer" type="net.anotheria.moskito.webui.producers.api.ProducerAO"><%--
--%><ano:iterate name="producer" property="lines" id="statLine" type="net.anotheria.moskito.webui.producers.api.StatLineAO">
"${producer.producerId}";"${statLine.statName}"<%--
--%><ano:iterate name="statLine" property="values" id="value" type="net.anotheria.moskito.core.decorators.value.StatValueAO"><%--
--%>;${value.value}<%--
--%></ano:iterate></ano:iterate></ano:iterate><%--

--%><ano:present name="decorator" property="cumulatedStat"><%--
--%><ano:define id="statLine" name="decorator" property="cumulatedStat" type="net.anotheria.moskito.webui.producers.api.StatLineAO"/>
"Total";"<ano:write name="statLine" property="statName"/>"<%--
--%><ano:iterate name="statLine" property="values" id="value" type="net.anotheria.moskito.core.decorators.value.StatValueAO"><%--
--%>;<ano:write name="value" property="value"/><%--
--%></ano:iterate></ano:present>