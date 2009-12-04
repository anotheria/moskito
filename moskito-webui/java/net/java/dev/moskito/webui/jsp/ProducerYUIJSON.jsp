<%@ page language="java" contentType="text/json;charset=UTF-8" session="true"
%><%@ taglib uri="http://www.anotheria.net/ano-tags" prefix="msk" 
%><%@ taglib uri="/tags/json" prefix="json"
%><%@ page isELIgnored ="false" 
%><json:object>
	<json:object name="producer" >
		<json:property name="id" value="${producer.id}"/>
		<json:property name="category" value="${producer.category}"/>
		<json:property name="subsystem" value="${producer.subsystem}"/>
		<json:property name="class" value="${producer.class}"/>
		<json:property name="interval" value="${currentInterval}"/>
		<json:property name="timestamp" value="${timestamp}"/>
		<json:property name="date" value="${timestampAsDate}"/>
		<msk:iterate name="decorators" id="decorator" type="net.java.dev.moskito.webui.bean.StatDecoratorBean">
		<json:array name="stats" var="statBean" items="${decorator.stats}">
			<json:object>
				<json:property name="name" value="${statBean.name}"/>
				<msk:iterate name="statBean" property="values" id="value" type="net.java.dev.moskito.webui.bean.StatValueBean" indexId="ind">
				<json:property name="${value.name}" value="${value.value}"/>
				</msk:iterate>
			</json:object>
		</json:array>
		</msk:iterate>
	</json:object>
</json:object>