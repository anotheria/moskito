<%@ page language="java" contentType="text/html;charset=iso-8859-15" session="true"
%><%@ taglib uri="/tags/moskito" prefix="msk" 
%><%@ taglib uri="/tags/struts-logic" prefix="logic" 
%>
Current interval: <b><msk:write name="currentInterval"/></b>&nbsp;&nbsp;Available:&nbsp;
<logic:iterate name="intervals" id="interval" type="net.java.dev.moskito.webui.bean.IntervalBean">
	<a href="<msk:write name="linkToCurrentPage"/>&pInterval=<msk:write name="interval" property="name"/>"><msk:write name="interval" property="name"/></a>&nbsp;
</logic:iterate>
<br><br>
