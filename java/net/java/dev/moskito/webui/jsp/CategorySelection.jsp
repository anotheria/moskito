<%@ page language="java" contentType="text/html;charset=iso-8859-15" session="true"
%><%@ taglib uri="/tags/moskito" prefix="msk" 
%><%@ taglib uri="/tags/struts-logic" prefix="logic" 
%>Categories:&nbsp;
<logic:iterate name="categories" id="category" type="net.java.dev.moskito.webui.bean.UnitCountBean">
	<a href="mskShowProducersByCategory?pCategory=<msk:write name="category" property="unitName"/>"><msk:write name="category" property="unitName"/>(<msk:write name="category" property="unitCount"/>)</a>&nbsp;
</logic:iterate>
<br/><br/>