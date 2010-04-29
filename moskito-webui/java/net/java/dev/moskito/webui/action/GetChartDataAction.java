package net.java.dev.moskito.webui.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.anotheria.maf.action.ActionForward;
import net.anotheria.maf.action.ActionMapping;
import net.anotheria.maf.bean.FormBean;
import net.anotheria.util.StringUtils;
import net.java.dev.moskito.core.producers.IStats;
import net.java.dev.moskito.core.producers.IStatsProducer;
import net.java.dev.moskito.core.registry.IProducerRegistry;
import net.java.dev.moskito.core.registry.ProducerRegistryFactory;
import net.java.dev.moskito.core.stats.TimeUnit;
import net.java.dev.moskito.webui.bean.NaviItem;

public class GetChartDataAction extends BaseMoskitoUIAction{

	@Override
	protected NaviItem getCurrentNaviItem() {
		return null;
	}

	@Override
	protected String getLinkToCurrentPage(HttpServletRequest req) {
		return null;
	}
	
	
	private IProducerRegistry registry;
	
	public GetChartDataAction(){
		registry = ProducerRegistryFactory.getProducerRegistryInstance();
	}

	@Override
	public ActionForward execute(ActionMapping mapping, FormBean bean,
			HttpServletRequest req, HttpServletResponse res) throws Exception {

		String intervalName = null;
		TimeUnit unit = TimeUnit.MICROSECONDS;
		
		String producersParameter = req.getParameter("producers");
		System.out.println("Producers parameter: "+producersParameter);
		
		String[] producers = StringUtils.tokenize(producersParameter, ',');
		System.out.println(""+producers.length+" producers");
		List<RequestedValue> params = new ArrayList<RequestedValue>();
		for (String p : producers){
			String[] tokens = StringUtils.tokenize(p, '.');
			params.add(new RequestedValue(tokens[0], tokens[1], tokens[2]));
		}
		
		System.out.println("Parsed parameters "+params);
		
		//processing parameters
		for (RequestedValue v : params){
			IStatsProducer producer = registry.getProducer(v.getProducerId());
			System.out.println("Producer: "+producer);
			if (producer==null){
				System.out.println("not found...");
				continue;
			}
			List<IStats> stats = producer.getStats();
			for (IStats s : stats){
				System.out.println("Checking "+s);
				if (s.getName().equals(v.getStatId())){
					System.out.println("Found: "+s.getName());
					System.out.println("---- "+s.getValueByNameAsString(v.getValue(), intervalName, unit));
				}
			}
		}
		
		
		return null;
	}
	
	public static class RequestedValue{
		private String producerId;
		private String statId;
		private String value;
		
		public RequestedValue(String aProducerId, String aStatId, String aValue){
			producerId = aProducerId;
			statId = aStatId;
			value = aValue;
		}
		
		public String getProducerId() {
			return producerId;
		}
		public void setProducerId(String producerId) {
			this.producerId = producerId;
		}
		public String getStatId() {
			return statId;
		}
		public void setStatId(String statId) {
			this.statId = statId;
		}
		public String getValue() {
			return value;
		}
		public void setValue(String value) {
			this.value = value;
		}
		
		@Override public String toString(){
			return getProducerId()+"."+getStatId()+"."+getValue();
		}
	}

}
