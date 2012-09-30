<%@ page language="java" contentType="text/html;charset=UTF-8" session="true"
%><%@ taglib uri="http://www.anotheria.net/ano-tags" prefix="ano" 
%>
<ano:define name="currentInterval" id="currentInterval" toScope="page" type="java.lang.String"/>
<ano:iterate name="intervals" id="interval" type="net.java.dev.moskito.webui.bean.IntervalBean">
	<option value="<ano:write name="linkToCurrentPage"/>&amp;pInterval=<ano:write name="interval" property="name"/>" <ano:equal name="interval" property="name" value="<%=currentInterval%>">selected="selected"</ano:equal>>		
			<ano:write name="interval" property="name"/>		
	</option>
</ano:iterate>
