<%@ page language="java" contentType="application/msexcel;charset=UTF-8" session="true" isELIgnored="false"
%><%@ taglib uri="http://www.anotheria.net/ano-tags" prefix="ano"
%>"producer: ${producer.producerId},<%--
--%> category: ${producer.category},<%--
--%> subsystem: ${producer.subsystem},<%--
--%> class: ${producer.producerClassName}."
<%--
--%><ano:iterate type="net.anotheria.moskito.webui.shared.bean.StatDecoratorBean" id="decorator" name="decorators"><%--
--%>"${decorator.name}";
"";"Name"<%-- 
--%><ano:iterate name="decorator" property="captions" type="net.anotheria.moskito.webui.shared.bean.StatCaptionBean" id="caption" indexId="ind">;"${caption.caption}"</ano:iterate>s<%--

--%><ano:iterate name="decorator" property="stats" id="stat" type="net.anotheria.moskito.webui.shared.bean.StatBean">
"";"${stat.name}";<%--
--%><ano:iterate name="stat" property="values" id="value" type="net.anotheria.moskito.webui.producers.api.StatValueAO"><%--
--%>;${value.value}<%--
--%></ano:iterate></ano:iterate></ano:iterate>

"Generated at <ano:write name="timestampAsDate"/>, timestamp: <ano:write name="timestamp"/>"
