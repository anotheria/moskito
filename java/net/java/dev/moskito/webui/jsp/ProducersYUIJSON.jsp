<%@ page language="java" contentType="text/json;charset=UTF-8" session="true"
%><%@ taglib uri="/tags/moskito" prefix="msk" 
%><%@ taglib uri="/tags/json" prefix="json"
%><%@ page isELIgnored ="false" 
%><msk:define id="currentTimeUnit" name='moskito.CurrentUnit' property="unitName"
/><json:object>
	<json:object name="moskito" >
		<json:property name="query" value="all producers"/>
		<json:property name="interval" value="${currentInterval}"/>
		<json:property name="timeUnit" value="${currentTimeUnit}"/>
		<json:property name="category" value="${producer.category}"/>
		<json:property name="subsystem" value="${producer.subsystem}"/>
		<json:property name="timestamp" value="${timestamp}"/>
		<json:property name="date" value="${timestampAsDate}"/>
		<json:array name="decorators" var="decorator" items="${decorators}">
			<json:object>
				<json:property name="name" value="${decorator.name}"/>
				<json:array name="metaheader">
					<json:object>
						<json:property name="id" value="producer"/>
						<json:property name="caption" value="Producer"/>
						<json:property name="type" value="string"/>
					</json:object>
					<json:object>
						<json:property name="id" value="category"/>
						<json:property name="caption" value="Category"/>
						<json:property name="type" value="string"/>
					</json:object>
					<json:object>
						<json:property name="id" value="subsystem"/>
						<json:property name="caption" value="Subsystem"/>
						<json:property name="type" value="string"/>
					</json:object>
					<msk:iterate name="decorator" property="metaHeader" id="meta" type="net.java.dev.moskito.webui.bean.MetaHeaderBean">
					<json:object>
						<json:property name="id" value="${meta.id}"/>
						<json:property name="caption" value="${meta.caption}"/>
						<json:property name="type" value="${meta.type}"/>
					</json:object>
					</msk:iterate>
					<json:object>
						<json:property name="id" value="class"/>
						<json:property name="caption" value="Class"/>
						<json:property name="type" value="string"/>
					</json:object>
				</json:array>
				<json:array name="producers" var="producer" items="${decorator.producers}">
					<json:object>
						<json:property name="producer" value="${producer.id}"/>
						<json:property name="category" value="${producer.category}"/>
						<json:property name="subsystem" value="${producer.subsystem}"/>
						<msk:iterate name="producer" property="values" id="value" type="net.java.dev.moskito.webui.bean.StatValueBean" indexId="ind">
						<json:property name="${decorator.metaHeader[ind].id}" value="${value.value}"/>
						</msk:iterate>
						<json:property name="class" value="${producer.className}"/>						
					</json:object>
				</json:array>
			</json:object>
    	</json:array>
	</json:object>
</json:object>