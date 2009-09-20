<%@ page language="java" contentType="text/html;charset=UTF-8" session="true"
%><%@ taglib uri="/tags/moskito" prefix="msk" 
%>
Current unit: <b><msk:write scope="session" name="moskito.CurrentUnit" property="unitName" ignore="true"/></b>&nbsp;&nbsp;Available:&nbsp;
<msk:iterate name="units" id="unit" type="net.java.dev.moskito.webui.bean.UnitBean">
	<a href="<msk:write name="linkToCurrentPage"/>&pUnit=<msk:write name="unit" property="unitName"/>"><msk:write name="unit" property="unitName"/></a>&nbsp;
</msk:iterate>
<br><br>
