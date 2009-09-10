<%@ page language="java" contentType="text/xml;charset=iso-8859-15" session="true"
%><%@ taglib uri="/tags/moskito" prefix="msk" 
%><%@ taglib uri="/tags/struts-logic" prefix="logic" 
%><%@ taglib uri="/tags/struts-bean" prefix="bean" 
%><?xml version="1.0" encoding="UTF-8"?>
<producer id="<msk:write name="producer" property="id"/>">
	<category><msk:write name="producer" property="category"/></category>
	<subsystem><msk:write name="producer" property="subsystem"/></subsystem>
	<class><msk:write name="producer" property="className"/></class>
	<interval><msk:write name="currentInterval"/></interval>
	<timestamp><msk:write name="timestamp"/></timestamp>
	<date><msk:write name="timestampAsDate"/></date>
	<logic:iterate name="decorators" id="decorator" type="net.java.dev.moskito.webui.bean.StatDecoratorBean">
          <decorator name="<msk:write name="decorator" property="name"/>">
          	<logic:iterate name="decorator" property="stats" id="statBean" type="net.java.dev.moskito.webui.bean.StatBean">
          		<method name="<msk:write name="statBean" property="name"/>">
          		<bean:define name="decorator" property="captions" type="java.util.List" id="captions"/>
    	      	<logic:iterate name="statBean" property="values" id="value" type="net.java.dev.moskito.webui.bean.StatValueBean" indexId="ind"><%
    	      		String tagCaption = ((net.java.dev.moskito.webui.bean.StatCaptionBean)captions.get(ind.intValue())).getCaption(); 
				%><value name="<%=tagCaption%>" type="<msk:write name="value" property="type"/>"><msk:write name="value" property="value"/></value>
	          	</logic:iterate>
	          	</method>
          	</logic:iterate>
          </decorator>
	</logic:iterate>      
</producer>