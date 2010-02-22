<%@ page language="java" contentType="text/html;charset=UTF-8" session="true"
%><%@ taglib uri="http://www.anotheria.net/ano-tags" prefix="msk" 
%>
Current interval: <b><msk:write name="currentInterval"/></b>&nbsp;&nbsp;Available:&nbsp;
<msk:iterate name="intervals" id="interval" type="net.java.dev.moskito.webui.bean.IntervalBean">
	<a href="<msk:write name="linkToCurrentPage"/>&amp;pInterval=<msk:write name="interval" property="name"/>"><msk:write name="interval" property="name"/></a>&nbsp;
</msk:iterate>
<br><br>
