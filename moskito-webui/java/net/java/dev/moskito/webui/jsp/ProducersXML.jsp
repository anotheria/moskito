<%@ page language="java" contentType="text/xml;charset=iso-8859-15" session="true"
%><%@ taglib uri="/tags/moskito" prefix="msk" 
%><%@ taglib uri="/tags/struts-logic" prefix="logic" 
%><%@ taglib uri="/tags/struts-bean" prefix="bean" 
%><?xml version="1.0" encoding="UTF-8"?>
<producers>
  <query>all producers</query>
  <interval><msk:write name="currentInterval"/></interval>
  <timestamp><msk:write name="timestamp"/></timestamp>
  <date><msk:write name="timestampAsDate"/></date>
  <logic:iterate type="net.java.dev.moskito.webui.bean.ProducerDecoratorBean" id="decorator" name="decorators">
    <decorator name="<msk:write name="decorator" property="name"/>"><%--
    --%><bean:define name="decorator" property="captions" type="java.util.List" id="captions"/><%--
	--%><logic:iterate name="decorator" property="producers" id="producer" type="net.java.dev.moskito.webui.bean.ProducerBean">
          <producer id="<msk:write name="producer" property="id"/>">
            <category><msk:write name="producer" property="category"/></category>
			<subsystem><msk:write name="producer" property="subsystem"/></subsystem>
			<class><msk:write name="producer" property="className"/></class>
            <values>
			<logic:iterate name="producer" property="values" id="value" type="net.java.dev.moskito.webui.bean.StatValueBean" indexId="ind"><%
				String tagCaption = ((net.java.dev.moskito.webui.bean.StatCaptionBean)captions.get(ind.intValue())).getCaption(); 
			%><value name="<%=tagCaption%>" type="<msk:write name="value" property="type"/>"><msk:write name="value" property="value"/></value>
			</logic:iterate>
            </values>
          </producer>
		</logic:iterate>      
    </decorator>
  </logic:iterate>
</producers>
