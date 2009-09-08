<%@ page language="java" contentType="application/msexcel;charset=iso-8859-15" session="true"
%><%@ taglib uri="/tags/struts-bean" prefix="bean" 
%><%@ taglib uri="/tags/struts-logic" prefix="logic" 
%><%--
--%><logic:iterate type="net.java.dev.moskito.webui.bean.ProducerDecoratorBean" id="decorator" name="decorators"><%--
--%><bean:write name="decorator" property="name"/>;Producer Id;Category;Subsystem<%--
--%><logic:iterate name="decorator" property="captions" type="net.java.dev.moskito.webui.bean.StatCaptionBean" id="caption" indexId="ind"><%--
--%>;<bean:write name="caption" property="caption"/><%--
--%></logic:iterate><%--
--%>;class<%--
--%> 
<%--
--%><logic:iterate name="decorator" property="producers" id="producer" type="net.java.dev.moskito.webui.bean.ProducerBean"><%--
--%>"";<bean:write name="producer" property="id"/>;<bean:write name="producer" property="category"/>;<bean:write name="producer" property="subsystem"/><%--
--%><logic:iterate name="producer" property="values" id="value" type="net.java.dev.moskito.webui.bean.StatValueBean"><%--
--%>;<bean:write name="value" property="value"/><%--
--%></logic:iterate><%--
--%>;<bean:write name="producer" property="className"/><%--
--%> 
<%--
--%></logic:iterate><%--
--%> 
<%--
--%></logic:iterate><%--
--%>

"Generated at <bean:write name="timestampAsDate"/>, timestamp: <bean:write name="timestamp"/>"