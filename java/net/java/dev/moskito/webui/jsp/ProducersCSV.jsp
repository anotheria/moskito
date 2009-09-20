<%@ page language="java" contentType="application/msexcel;charset=UTF-8" session="true"
%><%@ taglib uri="/tags/moskito" prefix="msk" 
%><%--
--%><msk:iterate type="net.java.dev.moskito.webui.bean.ProducerDecoratorBean" id="decorator" name="decorators"><%--
--%><msk:write name="decorator" property="name"/>;Producer Id;Category;Subsystem<%--
--%><msk:iterate name="decorator" property="captions" type="net.java.dev.moskito.webui.bean.StatCaptionBean" id="caption" indexId="ind"><%--
--%>;<msk:write name="caption" property="caption"/><%--
--%></msk:iterate><%--
--%>;class<%--
--%> 
<%--
--%><msk:iterate name="decorator" property="producers" id="producer" type="net.java.dev.moskito.webui.bean.ProducerBean"><%--
--%>"";<msk:write name="producer" property="id"/>;<msk:write name="producer" property="category"/>;<msk:write name="producer" property="subsystem"/><%--
--%><msk:iterate name="producer" property="values" id="value" type="net.java.dev.moskito.webui.bean.StatValueBean"><%--
--%>;<msk:write name="value" property="value"/><%--
--%></msk:iterate><%--
--%>;<msk:write name="producer" property="className"/><%--
--%> 
<%--
--%></msk:iterate><%--
--%> 
<%--
--%></msk:iterate><%--
--%>

"Generated at <msk:write name="timestampAsDate"/>, timestamp: <msk:write name="timestamp"/>"