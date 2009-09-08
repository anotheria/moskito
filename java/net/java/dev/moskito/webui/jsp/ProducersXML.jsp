<%@ page language="java" contentType="text/xml;charset=iso-8859-15" session="true"
%><%@ taglib uri="/tags/struts-bean" prefix="bean" 
%><%@ taglib uri="/tags/struts-logic" prefix="logic" 
%><?xml version="1.0" encoding="UTF-8"?>
<producers>
  <query>all producers</query>
  <interval><bean:write name="currentInterval"/></interval>
  <timestamp><bean:write name="timestamp"/></timestamp>
  <date><bean:write name="timestampAsDate"/></date>
  <logic:iterate type="net.java.dev.moskito.webui.bean.ProducerDecoratorBean" id="decorator" name="decorators">
    <decorator name="<bean:write name="decorator" property="name"/>"><%--
    --%><bean:define name="decorator" property="captions" type="java.util.List" id="captions"/><%--
	--%><logic:iterate name="decorator" property="producers" id="producer" type="net.java.dev.moskito.webui.bean.ProducerBean">
          <producer id="<bean:write name="producer" property="id"/>">
            <category><bean:write name="producer" property="category"/></category>
			<subsystem><bean:write name="producer" property="subsystem"/></subsystem>
			<class><bean:write name="producer" property="className"/></class>
            <values>
			<logic:iterate name="producer" property="values" id="value" type="net.java.dev.moskito.webui.bean.StatValueBean" indexId="ind"><%
				String tagCaption = ((net.java.dev.moskito.webui.bean.StatCaptionBean)captions.get(ind.intValue())).getCaption(); 
			%><value name="<%=tagCaption%>" type="<bean:write name="value" property="type"/>"><bean:write name="value" property="value"/></value>
			</logic:iterate>
            </values>
          </producer>
		</logic:iterate>      
    </decorator>
  </logic:iterate>
</producers>
