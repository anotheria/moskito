<%@ page language="java" contentType="application/msexcel;charset=iso-8859-15" session="true"
%><%@ taglib uri="/tags/struts-bean" prefix="bean"  
%><%@ taglib uri="/tags/struts-logic" prefix="logic" 
%>"producer: <bean:write name="producer" property="id"/>,<%--
--%> category: <bean:write name="producer" property="category"/>,<%--
--%> subsystem: <bean:write name="producer" property="subsystem"/>,<%--
--%> class: <bean:write name="producer" property="className"/>."
<%--
--%><logic:iterate type="net.java.dev.moskito.webui.bean.StatDecoratorBean" id="decorator" name="decorators"><%--
--%>"<bean:write name="decorator" property="name"/>";
"";"Name"<%-- 
--%><logic:iterate name="decorator" property="captions" type="net.java.dev.moskito.webui.bean.StatCaptionBean" id="caption" indexId="ind"><%--
--%>;"<bean:write name="caption" property="caption"/>"<%--
--%></logic:iterate>s<%--

--%><logic:iterate name="decorator" property="stats" id="stat" type="net.java.dev.moskito.webui.bean.StatBean">
"";"<bean:write name="stat" property="name"/>";<%--
--%><logic:iterate name="stat" property="values" id="value" type="net.java.dev.moskito.webui.bean.StatValueBean"><%--
--%>;<bean:write name="value" property="value"/><%--
--%></logic:iterate></logic:iterate></logic:iterate>

"Generated at <bean:write name="timestampAsDate"/>, timestamp: <bean:write name="timestamp"/>"
