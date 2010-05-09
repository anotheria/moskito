<%@ page language="java" contentType="text/xml;charset=UTF-8" session="true" 
%><%@ taglib uri="http://www.anotheria.net/ano-tags" prefix="msk" 
%><?xml version="1.0" encoding="UTF-8"?>
<data interval="<msk:write name="interval"/>" unit="<msk:write name="unit"/>">
<msk:iterate name="data" id="entity" type="net.java.dev.moskito.webui.bean.ChartDataEntityBean"
 > <element>
	<producerId><msk:write name="entity" property="producerId"/></producerId>
	<statName><msk:write name="entity" property="statName"/></statName>
	<statValueName><msk:write name="entity" property="statValueName"/></statValueName>
	<statValue><msk:write name="entity" property="statValue"/></statValue>
  </element>
</msk:iterate>
</data>