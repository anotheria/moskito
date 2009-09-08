<%@ page language="java" contentType="text/html;charset=iso-8859-15" session="true"
%><%@ taglib uri="/tags/struts-bean" prefix="bean" 
%><%@ taglib uri="/tags/struts-logic" prefix="logic" 
%>
<% int currentDepth = (Integer)request.getAttribute("currentDepth"); %>
var tmpNode<%=currentDepth+1%>;

<logic:iterate name="subnodes" type="net.java.dev.moskito.webui.bean.UseCaseElementNodeBean" id="node" indexId="index">
	tmpNode<%=currentDepth+1%> = new YAHOO.widget.TextNode("<bean:write name="node" property="callShort"/>", tmpNode<%=currentDepth%>, false);
	<% request.setAttribute("subnodes", node.getChildren()); %>
	<% request.setAttribute("currentDepth", currentDepth+1); %>
	<jsp:include page="TreeNode.jsp" flush="false" />
</logic:iterate> 