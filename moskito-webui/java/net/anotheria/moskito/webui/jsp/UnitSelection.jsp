<%@ page language="java" contentType="text/html;charset=UTF-8" session="true"
%><%@ taglib uri="http://www.anotheria.net/ano-tags" prefix="msk" 
%><msk:define name="moskito.CurrentUnit" property="unitName" id="currentUnit" toScope="page" type="java.lang.String"/>
<msk:iterate name="units" id="unit" type="net.java.dev.moskito.webui.bean.UnitBean">
	<option value="<msk:write name="linkToCurrentPage"/>&amp;pUnit=<msk:write name="unit" property="unitName"/>" <msk:equal name="unit" property="unitName" value="<%=currentUnit%>">selected="selected"</msk:equal>>		
		<msk:write name="unit" property="unitName"/>		
	</option>
</msk:iterate>
