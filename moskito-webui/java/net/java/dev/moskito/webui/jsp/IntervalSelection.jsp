<%@ page language="java" contentType="text/html;charset=UTF-8" session="true"
%><%@ taglib uri="http://www.anotheria.net/ano-tags" prefix="msk" 
%>
<msk:define name="currentInterval" id="currentInterval" toScope="page" type="java.lang.String"/>
<msk:iterate name="intervals" id="interval" type="net.java.dev.moskito.webui.bean.IntervalBean">
	<option value="<msk:write name="linkToCurrentPage"/>&amp;pInterval=<msk:write name="interval" property="name"/>" <msk:equal name="interval" property="name" value="<%=currentInterval%>">selected="selected"</msk:equal>>		
			<msk:write name="interval" property="name"/>		
	</option>
</msk:iterate>
