<%@ page language="java" contentType="application/msexcel;charset=UTF-8" session="true"
%><%@ taglib uri="http://www.anotheria.net/ano-tags" prefix="msk" 
%>"producer: <msk:write name="producer" property="id"/>,<%--
--%> category: <msk:write name="producer" property="category"/>,<%--
--%> subsystem: <msk:write name="producer" property="subsystem"/>,<%--
--%> class: <msk:write name="producer" property="className"/>."
<%--
--%><msk:iterate type="net.java.dev.moskito.webui.bean.StatDecoratorBean" id="decorator" name="decorators"><%--
--%>"<msk:write name="decorator" property="name"/>";
"";"Name"<%-- 
--%><msk:iterate name="decorator" property="captions" type="net.java.dev.moskito.webui.bean.StatCaptionBean" id="caption" indexId="ind"><%--
--%>;"<msk:write name="caption" property="caption"/>"<%--
--%></msk:iterate>s<%--

--%><msk:iterate name="decorator" property="stats" id="stat" type="net.java.dev.moskito.webui.bean.StatBean">
"";"<msk:write name="stat" property="name"/>";<%--
--%><msk:iterate name="stat" property="values" id="value" type="net.java.dev.moskito.webui.bean.StatValueBean"><%--
--%>;<msk:write name="value" property="value"/><%--
--%></msk:iterate></msk:iterate></msk:iterate>

"Generated at <msk:write name="timestampAsDate"/>, timestamp: <msk:write name="timestamp"/>"
