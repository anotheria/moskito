<%@ page language="java" contentType="text/html;charset=UTF-8" session="true"
%><%@ taglib uri="/tags/moskito" prefix="msk" 
%>Subsystems:&nbsp;
<msk:iterate name="subsystems" id="subsystem" type="net.java.dev.moskito.webui.bean.UnitCountBean">
	<a href="mskShowProducersBySubsystem?pSubsystem=<msk:write name="subsystem" property="unitName"/>"><msk:write name="subsystem" property="unitName"/>(<msk:write name="subsystem" property="unitCount"/>)</a>&nbsp;
</msk:iterate>
<br/><br/>
