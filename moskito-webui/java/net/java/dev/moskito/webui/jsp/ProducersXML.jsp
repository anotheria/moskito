<%@ page language="java" contentType="text/xml;charset=UTF-8" session="true"
%><%@ taglib uri="http://www.anotheria.net/ano-tags" prefix="msk" 
%><?xml version="1.0" encoding="UTF-8"?>
<producers>
  <query>all producers</query>
  <interval><msk:write name="currentInterval"/></interval>
  <timestamp><msk:write name="timestamp"/></timestamp>
  <date><msk:write name="timestampAsDate"/></date>
  <msk:iterate type="net.java.dev.moskito.webui.bean.ProducerDecoratorBean" id="decorator" name="decorators">
    <decorator name="<msk:write name="decorator" property="name" filter="true"/>"><%--
    --%><msk:define name="decorator" property="captions" type="java.util.List" id="captions"/><%--
	--%><msk:iterate name="decorator" property="producers" id="producer" type="net.java.dev.moskito.webui.bean.ProducerBean">
          <producer id="<msk:write name="producer" property="id" filter="true"/>">
            <category><msk:write name="producer" property="category" filter="true"/></category>
			<subsystem><msk:write name="producer" property="subsystem" filter="true"/></subsystem>
			<class><msk:write name="producer" property="className" filter="true"/></class>
            <values>
			<msk:iterate name="producer" property="values" id="value" type="net.java.dev.moskito.webui.bean.StatValueBean" indexId="ind"><%
				String tagCaption = ((net.java.dev.moskito.webui.bean.StatCaptionBean)captions.get(ind.intValue())).getCaption(); 
			%><value name="<%=tagCaption%>" type="<msk:write name="value" property="type"/>"><msk:write name="value" property="value"/></value>
			</msk:iterate>
            </values>
          </producer>
		</msk:iterate>      
    </decorator>
  </msk:iterate>
  <chartdata>
	<msk:iterate type="net.java.dev.moskito.webui.bean.GraphDataBean" id="graph" name="graphDatas">
	<chart name="<msk:write name="graph" property="caption" filter="true"/>" id="<msk:write name="graph" property="jsVariableName" filter="true"/>">
		<msk:iterate name="graph" property="values" id="value" type="net.java.dev.moskito.webui.bean.GraphDataValueBean">
			<value name="<msk:write name="value" property="name" filter="true"/>"><msk:write name="value" property="value" filter="true"/></value>
		</msk:iterate>	
	</chart>	
	</msk:iterate> 
  </chartdata>
</producers>
