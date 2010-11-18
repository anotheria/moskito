<%@ page language="java" contentType="text/html;charset=UTF-8" session="true"
%><%@ taglib uri="http://www.anotheria.net/ano-tags" prefix="msk"
%><% String currentCategory = (String)request.getAttribute("currentCategory");%>
<msk:iterate name="categories" id="category" type="net.java.dev.moskito.webui.bean.UnitCountBean">
	<option value="mskShowProducersByCategory?pCategory=<msk:write name="category" property="unitName"/>" <msk:equal name="category" property="unitName" value="<%=currentCategory%>">selected="selected"</msk:equal>>		
		<msk:write name="category" property="unitName"/><msk:notEqual name="category" property="unitCount" value="0">(<msk:write name="category" property="unitCount"/>)</msk:notEqual>		
	</option>
</msk:iterate>
