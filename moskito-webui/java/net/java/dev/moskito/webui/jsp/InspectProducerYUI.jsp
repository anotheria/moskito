<%@ page language="java" contentType="text/html;charset=iso-8859-15" session="true"
%><%@ taglib uri="/tags/struts-bean" prefix="bean" 
%><%@ taglib uri="/tags/struts-logic" prefix="logic" 
%>
<p><a href="<bean:write name="linkToCurrentPage"/>&pForward=xml">XML</a></p>
created at:&nbsp;<bean:write name="creationTime"/>&nbsp;(<bean:write name="creationTimestamp"/>)
<ul>
<logic:iterate name="creationTrace" type="java.lang.String" id="line">
	<li><bean:write name="line"/></li>
</logic:iterate>
</ul>