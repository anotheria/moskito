<%@ page language="java" contentType="text/xml;charset=UTF-8" session="true"
%><%@ taglib uri="http://www.anotheria.net/ano-tags" prefix="ano"
%><?xml version="1.0" encoding="UTF-8"?>
<thresholds>
  <system>
  	<color><ano:write name="systemStatusColor"/></color>
  	<state><ano:write name="systemStatus"/></state>
  </system>
  <setups>
	<ano:iterate name="infos" type="net.java.dev.moskito.webui.bean.ThresholdInfoBean" id="info">
		<setup>
			<id><ano:write name="info" property="id"/></id>
			<name><ano:write name="info" property="name"/></name>
			<producerName><ano:write name="info" property="producerName"/></producerName>
			<statName><ano:write name="info" property="statName"/></statName>
			<valueName><ano:write name="info" property="valueName"/></valueName>
			<intervalName><ano:write name="info" property="intervalName"/></intervalName>
			<descriptionString><ano:write name="info" property="descriptionString"/></descriptionString>
			<guards><ano:iterate name="info" property="guards" id="guard" type="java.lang.String"><guard><ano:write name="guard"/></guard></ano:iterate></guards>
		</setup>
		</ano:iterate>
	</setups>
	<thresholds>
	<ano:iterate name="thresholds" type="net.java.dev.moskito.webui.bean.ThresholdBean" id="threshold" indexId="index">
	<threshold>
		<setup>setup<ano:write name="threshold" property="id"/></setup>
		<color><ano:write name="threshold" property="colorCode"/></color>
		<status><ano:write name="threshold" property="status"/></status>
		<value><ano:write name="threshold" property="value"/></value>
		<change><ano:write name="threshold" property="change"/></change>
		<timestamp><ano:write name="threshold" property="timestamp"/></timestamp>
		<description><ano:write name="threshold" property="description"/></description>
	</threshold>
	</ano:iterate>
	</thresholds>
	<alerts>
	<ano:iterate name="alerts" type="net.java.dev.moskito.webui.bean.ThresholdAlertBean" id="alert" indexId="index">
		<alert>
			<timestamp><ano:write name="alert" property="timestamp"/></timestamp>
			<name><ano:write name="alert" property="name"/></name>
			<oldStatus><ano:write name="alert" property="oldStatus"/></oldStatus>
			<newStatus><ano:write name="alert" property="newStatus"/></newStatus>
			<oldValue><ano:write name="alert" property="oldValue"/></oldValue>
			<newValue><ano:write name="alert" property="newValue"/></newValue>
		</alert>
	</ano:iterate>
	</alerts>
</thresholds>
	