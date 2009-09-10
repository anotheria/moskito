<%@ page language="java" contentType="text/html;charset=iso-8859-15" session="true"
%><%@ taglib uri="/tags/struts-bean" prefix="bean" 
%><%@ taglib uri="/tags/struts-logic" prefix="logic" 
%>
<p><a href="<msk:write name="linkToCurrentPage"/>&pForward=xml">XML</a></p>
created at:&nbsp;<msk:write name="creationTime"/>&nbsp;(<msk:write name="creationTimestamp"/>)
<ul>
<logic:iterate name="creationTrace" type="java.lang.String" id="line">
	<li><msk:write name="line"/></li>
</logic:iterate>
</ul>