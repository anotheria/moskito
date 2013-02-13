<%@ page language="java" contentType="text/csv;charset=UTF-8"  session="true" 
%><%@ taglib uri="http://www.anotheria.net/ano-tags" prefix="ano" 
%>intervals,<ano:iterate name="intervals" id="interval" type="net.anotheria.moskito.webui.shared.bean.IntervalBean">"<ano:write name="interval" property="name"/>",</ano:iterate>
units,<ano:iterate name="units" id="unit" type="net.anotheria.moskito.webui.shared.bean.UnitBean">"<ano:write name="unit" property="unitName"/>",</ano:iterate>
producers,<ano:iterate name="producerAndTypes" id="producer" type="net.anotheria.moskito.webui.shared.bean.ProducerAndTypeBean">"<ano:write name="producer" property="producerId"/>;<ano:write name="producer" property="type"/>",</ano:iterate>
types,<ano:iterate name="typeAndValueNames" id="type" type="net.anotheria.moskito.webui.shared.bean.TypeAndValueNamesBean"><%--
--%>"<ano:write name="type" property="type"/>;<%--
--%><ano:iterate name="type" property="valueNames" type="java.lang.String" id="valueName"><%--
--%><ano:write name="valueName"/>;<%--
--%></ano:iterate>",<%--
--%></ano:iterate>
