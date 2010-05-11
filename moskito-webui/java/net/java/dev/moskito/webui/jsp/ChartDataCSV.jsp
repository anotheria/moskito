<%@ page language="java" contentType="text/csv;charset=UTF-8" session="true" 
%><%@ taglib uri="http://www.anotheria.net/ano-tags" prefix="msk" 
%>
producerId;statName;statValueName;statValue
<msk:iterate name="data" id="entity" type="net.java.dev.moskito.webui.bean.ChartDataEntityBean"><%--
--%><msk:write name="entity" property="producerId"/>,<%--
--%><msk:write name="entity" property="statName"/>,<%--
--%><msk:write name="entity" property="statValueName"/>,<%--
--%><msk:write name="entity" property="statValue"/><%--
--%>
</msk:iterate>
