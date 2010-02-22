<%@ page language="java" contentType="text/html;charset=UTF-8" session="true"%>
<%@ taglib uri="http://www.anotheria.net/ano-tags" prefix="msk"%>

<msk:iterate name="subsystems" id="subsystem" type="net.java.dev.moskito.webui.bean.UnitCountBean">
	<li>
		<a href="mskShowProducersBySubsystem?pSubsystem=<msk:write name="subsystem" property="unitName"/>">
			<msk:write name="subsystem" property="unitName"/>(<msk:write name="subsystem" property="unitCount"/>)
		</a>&nbsp;
	</li>
</msk:iterate>

