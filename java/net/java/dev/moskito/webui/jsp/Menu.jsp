<%@ page language="java" contentType="text/html;charset=iso-8859-15" session="true"
%><%@ taglib uri="/tags/struts-bean" prefix="bean" 
%><%@ taglib uri="/tags/struts-logic" prefix="logic" 
%><center>
<logic:iterate name="menu" id="item" type="net.java.dev.moskito.webui.bean.MenuItemBean">
	<logic:equal name="item" property="active" value="true">
		<b><bean:write name="item" property="caption"/></b>
	</logic:equal>
	<logic:notEqual name="item" property="active" value="true">
		<a href="<bean:write name="item" property="link"/>">&raquo;&nbsp;<bean:write name="item" property="caption"/></a>&nbsp;&nbsp;
	</logic:notEqual>
</logic:iterate>
</center>
<br/>
<hr size="1"/>

