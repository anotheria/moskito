<%@ page language="java" contentType="text/html;charset=UTF-8" session="true"
%><%@ taglib uri="/tags/moskito" prefix="msk" 
%>
<p><a href="<msk:write name="linkToCurrentPage"/>&pForward=xml">XML</a></p>
created at:&nbsp;<msk:write name="creationTime"/>&nbsp;(<msk:write name="creationTimestamp"/>)
<ul>
<msk:iterate name="creationTrace" type="java.lang.String" id="line">
	<li><msk:write name="line"/></li>
</msk:iterate>
</ul>