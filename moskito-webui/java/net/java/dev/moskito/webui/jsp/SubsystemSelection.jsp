<%@ page language="java" contentType="text/html;charset=iso-8859-15" session="true"
%><%@ taglib uri="/tags/struts-bean" prefix="bean" 
%><%@ taglib uri="/tags/struts-logic" prefix="logic" 
%>Subsystems:&nbsp;
<logic:iterate name="subsystems" id="subsystem" type="net.java.dev.moskito.webui.bean.UnitCountBean">
	<a href="mskShowProducersBySubsystem?pSubsystem=<bean:write name="subsystem" property="unitName"/>"><bean:write name="subsystem" property="unitName"/>(<bean:write name="subsystem" property="unitCount"/>)</a>&nbsp;
</logic:iterate>
<br/><br/>
