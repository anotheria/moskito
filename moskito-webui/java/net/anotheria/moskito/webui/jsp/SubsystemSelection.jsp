<%@ page language="java" contentType="text/html;charset=UTF-8" session="true"%>
<%@ taglib uri="http://www.anotheria.net/ano-tags" prefix="msk"%><% 
String currentSubsystem = (String)request.getAttribute("currentSubsystem");
%><msk:iterate name="subsystems" id="subsystem" type="net.java.dev.moskito.webui.bean.UnitCountBean">
	<option value="mskShowProducersBySubsystem?pSubsystem=<msk:write name="subsystem" property="unitName"/>" <msk:equal name="subsystem" property="unitName" value="<%=currentSubsystem%>">selected="selected"</msk:equal>>		
			<msk:write name="subsystem" property="unitName"/><msk:notEqual name="subsystem" property="unitCount" value="0">(<msk:write name="subsystem" property="unitCount"/>)</msk:notEqual>		
	</option>
</msk:iterate>
