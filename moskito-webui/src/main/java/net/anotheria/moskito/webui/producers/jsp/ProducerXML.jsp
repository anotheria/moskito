<%@ page language="java" contentType="text/xml;charset=UTF-8" session="true"
%><%@ taglib uri="http://www.anotheria.net/ano-tags" prefix="msk" 
%><?xml version="1.0" encoding="UTF-8"?>
<producer id="${producer.producerId}">
	<category><msk:write name="producer" property="category"/></category>
	<subsystem><msk:write name="producer" property="subsystem"/></subsystem>
	<class><msk:write name="producer" property="producerClassName"/></class>
	<interval><msk:write name="currentInterval"/></interval>
	<timestamp><msk:write name="timestamp"/></timestamp>
	<date><msk:write name="timestampAsDate"/></date>
	<msk:iterate name="decorators" id="decorator" type="net.anotheria.moskito.webui.shared.bean.StatDecoratorBean">
          <decorator name="<msk:write name="decorator" property="name"/>">
          	<msk:iterate name="decorator" property="stats" id="statBean" type="net.anotheria.moskito.webui.shared.bean.StatBean">
          		<method name="<msk:write name="statBean" property="name"/>">
          		<msk:define name="decorator" property="captions" type="java.util.List" id="captions"/>
    	      	<msk:iterate name="statBean" property="values" id="value" type="net.anotheria.moskito.webui.producers.api.StatValueAO" indexId="ind"><%
    	      		String tagCaption = ((net.anotheria.moskito.webui.shared.bean.StatCaptionBean)captions.get(ind.intValue())).getCaption();
				%><value name="<%=tagCaption%>" type="<msk:write name="value" property="type"/>"><msk:write name="value" property="value"/></value>
	          	</msk:iterate>
	          	</method>
          	</msk:iterate>
          </decorator>
	</msk:iterate>      
</producer>