<%@ page language="java" contentType="application/msexcel;charset=iso-8859-15" session="true"
%><%@ taglib uri="/tags/moskito" prefix="msk" 
%><%@ taglib uri="/tags/struts-logic" prefix="logic" 
%><%--
--%><logic:iterate type="net.java.dev.moskito.webui.bean.ProducerDecoratorBean" id="decorator" name="decorators"><%--
--%><msk:write name="decorator" property="name"/>;Producer Id;Category;Subsystem<%--
--%><logic:iterate name="decorator" property="captions" type="net.java.dev.moskito.webui.bean.StatCaptionBean" id="caption" indexId="ind"><%--
--%>;<msk:write name="caption" property="caption"/><%--
--%></logic:iterate><%--
--%>;class<%--
--%> 
<%--
--%><logic:iterate name="decorator" property="producers" id="producer" type="net.java.dev.moskito.webui.bean.ProducerBean"><%--
--%>"";<msk:write name="producer" property="id"/>;<msk:write name="producer" property="category"/>;<msk:write name="producer" property="subsystem"/><%--
--%><logic:iterate name="producer" property="values" id="value" type="net.java.dev.moskito.webui.bean.StatValueBean"><%--
--%>;<msk:write name="value" property="value"/><%--
--%></logic:iterate><%--
--%>;<msk:write name="producer" property="className"/><%--
--%> 
<%--
--%></logic:iterate><%--
--%> 
<%--
--%></logic:iterate><%--
--%>

"Generated at <msk:write name="timestampAsDate"/>, timestamp: <msk:write name="timestamp"/>"