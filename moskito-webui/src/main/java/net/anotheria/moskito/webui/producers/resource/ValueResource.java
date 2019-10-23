package net.anotheria.moskito.webui.producers.resource;

import io.swagger.v3.oas.annotations.servers.Server;
import net.anotheria.anoplass.api.APIException;
import net.anotheria.anoplass.api.APIFinder;
import net.anotheria.moskito.core.stats.TimeUnit;
import net.anotheria.moskito.webui.producers.api.ProducerAPI;
import net.anotheria.moskito.webui.producers.api.ValueRequestPO;
import net.anotheria.moskito.webui.producers.api.ValueResponseAO;
import net.anotheria.moskito.webui.shared.resource.AbstractResource;
import net.anotheria.moskito.webui.shared.resource.ReplyObject;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import java.util.Arrays;
import java.util.List;

/**
 * This resource serves requests for single and multiple values,
 * see https://jira.opensource.anotheria.net/browse/MSK-483 for details.
 *
 * @author lrosenberg
 * @since 01.06.18 15:20
 */
@Path("value")
@Server(url = "/moskito-inspect-rest/")
public class ValueResource extends AbstractResource {

	/**
	 * ProducerAPI.
	 */
	private ProducerAPI producerAPI = APIFinder.findAPI(ProducerAPI.class);


	@GET
	@Path("{producerName}/{statName}/{valueName}/{interval}/{timeunit}")
	public ReplyObject getValue(
			@PathParam("interval") String intervalName,
			@PathParam("timeunit") String timeUnitParam,
			@PathParam("producerName") String producerName,
			@PathParam("statName") String statName,
			@PathParam("valueName") String valueName
		) {
		TimeUnit timeUnit = null;
		try{
			timeUnit = TimeUnit.valueOf(timeUnitParam);
		}catch(IllegalArgumentException e){
			return ReplyObject.error("Not parseable timeunit value:"+timeUnitParam+", legal values are: "+ Arrays.asList(TimeUnit.values()));
		}

		try {
			String valueAsString = producerAPI.getSingleValue(producerName, statName, valueName, intervalName, timeUnit);
			return ReplyObject.success("value", valueAsString);

		}catch(APIException e) {
			return ReplyObject.error(e);
		}
	}

	@POST
	public ReplyObject getValues(ValueRequestPO[] values) {
		for (ValueRequestPO p : values){
			try{
				TimeUnit.fromString(p.getTimeUnit());
			}catch(IllegalArgumentException e){
				return ReplyObject.error("Wrong timeUnit: '"+p.getTimeUnit()+"' in "+p);
			}
		}

		try {
			List<ValueResponseAO> responses = producerAPI.getMultipleValues(Arrays.asList(values));
			return ReplyObject.success("values", responses);
		}catch(APIException e){
			return ReplyObject.error(e);
		}


	}

}
