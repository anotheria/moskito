<%@ page trimDirectiveWhitespaces="true" %>
<%@ page language="java" contentType="text/xml;charset=UTF-8" session="true" %>
<%@ taglib uri="http://www.anotheria.net/ano-tags" prefix="ano" %>
<?xml version="1.0" encoding="UTF-8"?>
<result>
	<query>CumulatedProducers</query>
	<interval><ano:write name="currentInterval"/></interval>
	<timestamp><ano:write name="timestamp"/></timestamp>
	<date><ano:write name="timestampAsDate"/></date>

	<decorator name="<ano:write name="decorator" property="name"/>">
		<stats>
			<ano:iterate name="decorator" property="producers" id="producer" type="net.anotheria.moskito.webui.producers.api.ProducerAO">
				<ano:iterate name="producer" property="lines" id="statLine" type="net.anotheria.moskito.webui.producers.api.StatLineAO">
					<stat name="<ano:write name="statLine" property="statName"/>" producerId="<ano:write name="producer" property="producerId"/>">
						<values>
							<ano:iterate name="statLine" property="values" id="value" type="net.anotheria.moskito.core.decorators.value.StatValueAO">
								<value name="<ano:write name="value" property="name" />" type="<ano:write name="value" property="type" />">
									<ano:write name="value" property="value" />
								</value>
							</ano:iterate>
						</values>
					</stat>
				</ano:iterate>
			</ano:iterate>

			<ano:present name="decorator" property="cumulatedStat">
				<ano:define id="statLine" name="decorator" property="cumulatedStat" type="net.anotheria.moskito.webui.producers.api.StatLineAO"/>
				<stat name="<ano:write name="statLine" property="statName" />">
					<values>
						<ano:iterate name="statLine" property="values" id="value" type="net.anotheria.moskito.core.decorators.value.StatValueAO">
							<value name="<ano:write name="value" property="name" />" type="<ano:write name="value" property="type" />">
								<ano:write name="value" property="value" />
							</value>
						</ano:iterate>
					</values>
				</stat>
			</ano:present>
		</stats>
	</decorator>
</result>