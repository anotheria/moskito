<%@ page language="java" contentType="text/json;charset=iso-8859-15" session="true"
%><%@ taglib uri="/tags/struts-bean" prefix="bean" 
%><%@ taglib uri="/tags/struts-logic" prefix="logic" 
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
		<logic:iterate name="decorators" id="decorator" type="net.java.dev.moskito.webui.bean.StatDecoratorBean">
		<json:array name="stats" var="statBean" items="${decorator.stats}">
			<json:object>
				<json:property name="name" value="${statBean.name}"/>
				<logic:iterate name="statBean" property="values" id="value" type="net.java.dev.moskito.webui.bean.StatValueBean" indexId="ind">
				<json:property name="${value.name}" value="${value.value}"/>
				</logic:iterate>
			</json:object>
		</json:array>
		</logic:iterate>
	</json:object>
</json:object>