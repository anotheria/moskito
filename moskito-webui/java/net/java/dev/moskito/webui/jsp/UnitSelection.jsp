<%@ page language="java" contentType="text/html;charset=UTF-8" session="true"
%><%@ taglib uri="http://www.anotheria.net/ano-tags" prefix="msk" 
%>
 
<option selected>
	<msk:write scope="session" name="moskito.CurrentUnit" property="unitName" ignore="true"/>
</option>

<msk:iterate name="units" id="unit" type="net.java.dev.moskito.webui.bean.UnitBean">
	<option value="<msk:write name="linkToCurrentPage"/>&amp;pUnit=<msk:write name="unit" property="unitName"/>">		
		<msk:write name="unit" property="unitName"/>		
	</option>
</msk:iterate>
