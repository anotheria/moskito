<%@ page language="java" contentType="text/xml;charset=iso-8859-15" session="true"
%><%@ taglib uri="/tags/struts-bean" prefix="bean" 
%><%@ taglib uri="/tags/struts-logic" prefix="logic" 
%><?xml version="1.0" encoding="UTF-8"?>
<producer id="<bean:write name="producer" property="id"/>">
	<category><bean:write name="producer" property="category"/></category>
	<subsystem><bean:write name="producer" property="subsystem"/></subsystem>
	<class><bean:write name="producer" property="className"/></class>
	<interval><bean:write name="currentInterval"/></interval>
	<timestamp><bean:write name="timestamp"/></timestamp>
	<date><bean:write name="timestampAsDate"/></date>
	<logic:iterate name="decorators" id="decorator" type="net.java.dev.moskito.webui.bean.StatDecoratorBean">
          <decorator name="<bean:write name="decorator" property="name"/>">
          	<logic:iterate name="decorator" property="stats" id="statBean" type="net.java.dev.moskito.webui.bean.StatBean">
          		<method name="<bean:write name="statBean" property="name"/>">
          		<bean:define name="decorator" property="captions" type="java.util.List" id="captions"/>
    	      	<logic:iterate name="statBean" property="values" id="value" type="net.java.dev.moskito.webui.bean.StatValueBean" indexId="ind"><%
    	      		String tagCaption = ((net.java.dev.moskito.webui.bean.StatCaptionBean)captions.get(ind.intValue())).getCaption(); 
				%><<%=tagCaption%>><bean:write name="value" property="value"/></<%=tagCaption%>>
	          	</logic:iterate>
	          	</method>
          	</logic:iterate>
          </decorator>
	</logic:iterate>      
</producer>