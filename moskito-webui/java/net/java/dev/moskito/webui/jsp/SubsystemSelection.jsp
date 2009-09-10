<%@ page language="java" contentType="text/html;charset=iso-8859-15" session="true"
%><%@ taglib uri="/tags/moskito" prefix="msk" 
%><%@ taglib uri="/tags/struts-logic" prefix="logic" 
%>Subsystems:&nbsp;
<logic:iterate name="subsystems" id="subsystem" type="net.java.dev.moskito.webui.bean.UnitCountBean">
	<a href="mskShowProducersBySubsystem?pSubsystem=<msk:write name="subsystem" property="unitName"/>"><msk:write name="subsystem" property="unitName"/>(<msk:write name="subsystem" property="unitCount"/>)</a>&nbsp;
</logic:iterate>
<br/><br/>
