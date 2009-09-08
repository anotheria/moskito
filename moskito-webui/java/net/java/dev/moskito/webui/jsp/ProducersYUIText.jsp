<%@ page language="java" contentType="text/plain;charset=iso-8859-15" session="true"
%><%@ taglib uri="/tags/struts-bean" prefix="bean" 
%><%@ taglib uri="/tags/struts-logic" prefix="logic" 
%><%@ taglib uri="/tags/json" prefix="json"
%><%@ page isELIgnored ="false" 
%><bean:define id="currentTimeUnit" name='moskito.CurrentUnit' property="unitName"
/>moskito
	interval=${currentInterval}
	timeUnit=${currentTimeUnit}
	category=${producer.category}
	subsystem=${producer.subsystem}
	timestamp=${timestamp}
	date=${timestampAsDate}
	decorators
		<logic:iterate type="net.java.dev.moskito.webui.bean.ProducerDecoratorBean" id="decorator" name="decorators">
		#############################################################################################################################
		name="${decorator.name}
		-----------------------------------------------------------------------------------------------------------------------------
		<logic:iterate name="decorator" property="producers" id="producer" type="net.java.dev.moskito.webui.bean.ProducerBean">
		producer=${producer.id}
		category=${producer.category}
		subsystem=${producer.subsystem}
		<logic:iterate name="producer" property="values" id="value" type="net.java.dev.moskito.webui.bean.StatValueBean" indexId="ind"
		>${value.name}=${value.value}
		</logic:iterate
		>class=${producer.className}
		</logic:iterate>
		</logic:iterate>						
