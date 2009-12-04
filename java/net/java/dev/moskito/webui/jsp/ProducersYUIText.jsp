<%@ page language="java" contentType="text/plain;charset=UTF-8" session="true"
%><%@ taglib uri="http://www.anotheria.net/ano-tags" prefix="msk" 
%><%@ taglib uri="/tags/json" prefix="json"
%><%@ page isELIgnored ="false" 
%><msk:define id="currentTimeUnit" name='moskito.CurrentUnit' property="unitName"
/>moskito
	interval=${currentInterval}
	timeUnit=${currentTimeUnit}
	category=${producer.category}
	subsystem=${producer.subsystem}
	timestamp=${timestamp}
	date=${timestampAsDate}
	decorators
		<msk:iterate type="net.java.dev.moskito.webui.bean.ProducerDecoratorBean" id="decorator" name="decorators">
		#############################################################################################################################
		name="${decorator.name}
		-----------------------------------------------------------------------------------------------------------------------------
		<msk:iterate name="decorator" property="producers" id="producer" type="net.java.dev.moskito.webui.bean.ProducerBean">
		producer=${producer.id}
		category=${producer.category}
		subsystem=${producer.subsystem}
		<msk:iterate name="producer" property="values" id="value" type="net.java.dev.moskito.webui.bean.StatValueBean" indexId="ind"
		>${value.name}=${value.value}
		</msk:iterate
		>class=${producer.className}
		</msk:iterate>
		</msk:iterate>						
