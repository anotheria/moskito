<%@ page language="java" contentType="text/html;charset=UTF-8" session="true"
%><%@ taglib uri="/tags/moskito" prefix="msk" 
%>
Current interval: <b><msk:write name="currentInterval"/></b>&nbsp;&nbsp;Available:&nbsp;
<msk:iterate name="intervals" id="interval" type="net.java.dev.moskito.webui.bean.IntervalBean">
	<a href="<msk:write name="linkToCurrentPage"/>&pInterval=<msk:write name="interval" property="name"/>"><msk:write name="interval" property="name"/></a>&nbsp;
</msk:iterate>
<br><br>
