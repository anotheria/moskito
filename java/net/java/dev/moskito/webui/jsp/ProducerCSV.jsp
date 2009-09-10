<%@ page language="java" contentType="application/msexcel;charset=iso-8859-15" session="true"
%><%@ taglib uri="/tags/moskito" prefix="msk" 
%><%@ taglib uri="/tags/struts-logic" prefix="logic" 
%>"producer: <msk:write name="producer" property="id"/>,<%--
--%> category: <msk:write name="producer" property="category"/>,<%--
--%> subsystem: <msk:write name="producer" property="subsystem"/>,<%--
--%> class: <msk:write name="producer" property="className"/>."
<%--
--%><logic:iterate type="net.java.dev.moskito.webui.bean.StatDecoratorBean" id="decorator" name="decorators"><%--
--%>"<msk:write name="decorator" property="name"/>";
"";"Name"<%-- 
--%><logic:iterate name="decorator" property="captions" type="net.java.dev.moskito.webui.bean.StatCaptionBean" id="caption" indexId="ind"><%--
--%>;"<msk:write name="caption" property="caption"/>"<%--
--%></logic:iterate>s<%--

--%><logic:iterate name="decorator" property="stats" id="stat" type="net.java.dev.moskito.webui.bean.StatBean">
"";"<msk:write name="stat" property="name"/>";<%--
--%><logic:iterate name="stat" property="values" id="value" type="net.java.dev.moskito.webui.bean.StatValueBean"><%--
--%>;<msk:write name="value" property="value"/><%--
--%></logic:iterate></logic:iterate></logic:iterate>

"Generated at <msk:write name="timestampAsDate"/>, timestamp: <msk:write name="timestamp"/>"
