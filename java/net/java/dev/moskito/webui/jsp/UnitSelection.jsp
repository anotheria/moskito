<%@ page language="java" contentType="text/html;charset=UTF-8" session="true"
%><%@ taglib uri="http://www.anotheria.net/ano-tags" prefix="msk" 
%>
<%--
Current unit: <b><msk:write scope="session" name="moskito.CurrentUnit" property="unitName" ignore="true"/></b>&nbsp;&nbsp;Available:&nbsp;
 --%>
 
<option selected>
	<msk:write scope="session" name="moskito.CurrentUnit" property="unitName" ignore="true"/>
</option>
<msk:iterate name="units" id="unit" type="net.java.dev.moskito.webui.bean.UnitBean">
	<option>
		<a href="<msk:write name="linkToCurrentPage"/>&amp;pUnit=<msk:write name="unit" property="unitName"/>"><msk:write name="unit" property="unitName"/></a>&nbsp;
	</option>
</msk:iterate>
