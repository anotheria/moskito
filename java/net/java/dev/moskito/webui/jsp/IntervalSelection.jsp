<%@ page language="java" contentType="text/html;charset=iso-8859-15" session="true"
%><%@ taglib uri="/tags/struts-bean" prefix="bean" 
%><%@ taglib uri="/tags/struts-logic" prefix="logic" 
%>
Current interval: <b><bean:write name="currentInterval"/></b>&nbsp;&nbsp;Available:&nbsp;
<logic:iterate name="intervals" id="interval" type="net.java.dev.moskito.webui.bean.IntervalBean">
	<a href="<bean:write name="linkToCurrentPage"/>&pInterval=<bean:write name="interval" property="name"/>"><bean:write name="interval" property="name"/></a>&nbsp;
</logic:iterate>
<br><br>
