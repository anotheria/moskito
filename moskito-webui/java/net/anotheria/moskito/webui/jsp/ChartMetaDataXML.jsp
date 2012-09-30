<%@ page language="java" contentType="text/xml;charset=UTF-8" session="true" 
%><%@ taglib uri="http://www.anotheria.net/ano-tags" prefix="ano" 
%><?xml version="1.0" encoding="UTF-8"?>
<metadata>
	<intervals>
	<ano:iterate name="intervals" id="interval" type="net.java.dev.moskito.webui.bean.IntervalBean">
		<interval><ano:write name="interval" property="name"/></interval><%--
	--%></ano:iterate>
   	</intervals>
   	<units>
  	<ano:iterate name="units" id="unit" type="net.java.dev.moskito.webui.bean.UnitBean">
		<unit><ano:write name="unit" property="unitName"/></unit><%--
		--%></ano:iterate>
	</units>
	<producers>
    <ano:iterate name="producerAndTypes" id="producer" type="net.java.dev.moskito.webui.bean.ProducerAndTypeBean">
		<producer id="<ano:write name="producer" property="producerId"/>" type="<ano:write name="producer" property="type"/>">
			<statnames>
				<ano:iterate name="producer" property="statNames" type="java.lang.String" id="statName" indexId="statIndex">
					<statname><ano:write name="statName"/></statname><%--
				--%></ano:iterate>
			</statnames>
		</producer><%--
    --%></ano:iterate>
	</producers>
	<types>
    <ano:iterate name="typeAndValueNames" id="type" type="net.java.dev.moskito.webui.bean.TypeAndValueNamesBean">
		<type name="<ano:write name="type" property="type"/>">
			<valueNames>
			<ano:iterate name="type" property="valueNames" type="java.lang.String" id="valueName">
				<valueName><ano:write name="valueName"/></valueName><%--
			 --%></ano:iterate>
			</valueNames>
		</type><%--
    --%></ano:iterate>
	</types>
   
</metadata>
