<%@ page language="java" contentType="application/json;charset=UTF-8" session="true"
%><%@ taglib uri="http://www.anotheria.net/ano-tags" prefix="ano" 
%>{
	query: "AllProducers",
	interval: "<ano:write name="currentInterval"/>",
	timestamp: "<ano:write name="timestamp"/>",
	date: "<ano:write name="timestampAsDate"/>", 
    <ano:iterate type="net.anotheria.moskito.webui.shared.bean.ProducerDecoratorBean" id="decorator" name="decorators"><ano:define name="decorator" property="captions" type="java.util.List" id="captions"/>
    "<ano:write name="decorator" property="name"/>" : {
		<ano:iterate name="decorator" property="producers" id="producer" type="net.anotheria.moskito.webui.shared.bean.ProducerBean">
		          "<ano:write name="producer" property="id"/>": {
		            category: "<ano:write name="producer" property="category"/>",
					subsystem: "<ano:write name="producer" property="subsystem"/>",
					class: "<ano:write name="producer" property="className"/>",
		            values: [
					<ano:iterate name="producer" property="values" id="value" type="net.anotheria.moskito.webui.producers.api.StatValueAO" indexId="ind"><%
						String tagCaption = ((net.anotheria.moskito.webui.shared.bean.StatCaptionBean)captions.get(ind.intValue())).getCaption();
					%>
					{
						name: "<%=tagCaption%>",
						type: "<ano:write name="value" property="type"/>",
						value: "<ano:write name="value" property="value"/>" 
					}, 
					</ano:iterate>
		            ]}
	            }
				</ano:iterate>      
            }
		  </ano:iterate>

		chartdata: [
			<ano:iterate type="net.anotheria.moskito.webui.shared.bean.GraphDataBean" id="graph" name="graphDatas">
			{
				name: "<ano:write name="graph" property="caption"/>",
				id: "<ano:write name="graph" property="jsVariableName"/>",
				values: [
				<ano:iterate name="graph" property="values" id="value" type="net.anotheria.moskito.webui.shared.bean.GraphDataValueBean">
					name: "<ano:write name="value" property="name"/>",
					value: "<ano:write name="value" property="value"/>"
				</ano:iterate>	
				],
			}
			</ano:iterate> 
		]


    }
