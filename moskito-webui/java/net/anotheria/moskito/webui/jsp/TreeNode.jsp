<%@ page language="java" contentType="text/html;charset=UTF-8" session="true"
%><%@ taglib uri="http://www.anotheria.net/ano-tags" prefix="msk" 
%>
<% int currentDepth = (Integer)request.getAttribute("currentDepth"); %>
var tmpNode<%=currentDepth+1%>;

<msk:iterate name="subnodes" type="net.java.dev.moskito.webui.bean.UseCaseElementNodeBean" id="node" indexId="index">
	tmpNode<%=currentDepth+1%> = new YAHOO.widget.TextNode("<msk:write name="node" property="callShort"/>", tmpNode<%=currentDepth%>, false);
	<% request.setAttribute("subnodes", node.getChildren()); %>
	<% request.setAttribute("currentDepth", currentDepth+1); %>
	<jsp:include page="TreeNode.jsp" flush="false" />
</msk:iterate> 