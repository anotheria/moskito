<%@ page language="java" contentType="text/html;charset=UTF-8" session="true"
%><%@ taglib uri="/tags/moskito" prefix="msk" 
%>Categories:&nbsp;
<msk:iterate name="categories" id="category" type="net.java.dev.moskito.webui.bean.UnitCountBean">
	<a href="mskShowProducersByCategory?pCategory=<msk:write name="category" property="unitName"/>"><msk:write name="category" property="unitName"/>(<msk:write name="category" property="unitCount"/>)</a>&nbsp;
</msk:iterate>
<br/><br/>