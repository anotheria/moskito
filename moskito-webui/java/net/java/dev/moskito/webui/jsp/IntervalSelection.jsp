<%@ page language="java" contentType="text/html;charset=UTF-8" session="true"
%><%@ taglib uri="http://www.anotheria.net/ano-tags" prefix="msk" 
%>


<option selected>
	<msk:write name="currentInterval"/>
</option>
 
 
<msk:iterate name="intervals" id="interval" type="net.java.dev.moskito.webui.bean.IntervalBean">
	<option value="<msk:write name="linkToCurrentPage"/>&amp;pInterval=<msk:write name="interval" property="name"/>">		
			<msk:write name="interval" property="name"/>		
	</option>
</msk:iterate>
