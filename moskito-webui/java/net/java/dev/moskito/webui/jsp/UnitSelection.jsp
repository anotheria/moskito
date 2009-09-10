<%@ page language="java" contentType="text/html;charset=iso-8859-15" session="true"
%><%@ taglib uri="/tags/moskito" prefix="msk" 
%><%@ taglib uri="/tags/struts-logic" prefix="logic" 
%>
Current unit: <b><msk:write scope="session" name="moskito.CurrentUnit" property="unitName" ignore="true"/></b>&nbsp;&nbsp;Available:&nbsp;
<logic:iterate name="units" id="unit" type="net.java.dev.moskito.webui.bean.UnitBean">
	<a href="<msk:write name="linkToCurrentPage"/>&pUnit=<msk:write name="unit" property="unitName"/>"><msk:write name="unit" property="unitName"/></a>&nbsp;
</logic:iterate>
<br><br>
