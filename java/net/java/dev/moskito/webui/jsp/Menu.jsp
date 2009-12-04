<%@ page language="java" contentType="text/html;charset=UTF-8" session="true"
%><%@ taglib uri="http://www.anotheria.net/ano-tags" prefix="msk" 
%><center>
<msk:iterate name="menu" id="item" type="net.java.dev.moskito.webui.bean.MenuItemBean">
	<msk:equal name="item" property="active" value="true">
		<b><msk:write name="item" property="caption"/></b>
	</msk:equal>
	<msk:notEqual name="item" property="active" value="true">
		<a href="<msk:write name="item" property="link"/>">&raquo;&nbsp;<msk:write name="item" property="caption"/></a>&nbsp;&nbsp;
	</msk:notEqual>
</msk:iterate>
</center>
<br/>
<hr size="1"/>

