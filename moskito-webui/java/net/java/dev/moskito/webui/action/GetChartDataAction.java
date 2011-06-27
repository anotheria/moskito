package net.java.dev.moskito.webui.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.anotheria.maf.action.ActionCommand;
import net.anotheria.maf.action.ActionMapping;
import net.anotheria.maf.bean.FormBean;
import net.anotheria.util.StringUtils;
import net.java.dev.moskito.core.producers.IStats;
import net.java.dev.moskito.core.producers.IStatsProducer;
import net.java.dev.moskito.core.registry.IProducerRegistry;
import net.java.dev.moskito.core.registry.ProducerRegistryFactory;
import net.java.dev.moskito.core.stats.TimeUnit;
import net.java.dev.moskito.webui.bean.ChartDataEntityBean;
import net.java.dev.moskito.webui.bean.NaviItem;

/**
 * Delivers data for charts.
 * @author lrosenberg.
 *
 */
public class GetChartDataAction extends BaseMoskitoUIAction{

	@Override
	protected NaviItem getCurrentNaviItem() {
		return null;
	}

	@Override
	protected String getLinkToCurrentPage(HttpServletRequest req) {
		return null;
	}
	
	/**
	 * Link to the producer registry.
	 */
	private IProducerRegistry registry;
	/**
	 * Default constructor.
	 */
	public GetChartDataAction(){
		registry = ProducerRegistryFactory.getProducerRegistryInstance();
	}

	@Override
	public ActionCommand execute(ActionMapping mapping, FormBean form,
			HttpServletRequest req, HttpServletResponse res) {

		String intervalName = getCurrentInterval(req, false);
		TimeUnit unit = getCurrentUnit(req, false).getUnit();
		
		req.setAttribute("interval", intervalName);
		req.setAttribute("unit", unit.toString());
		
		String producersParameter = req.getParameter("producers");
		
		String[] producers = StringUtils.tokenize(producersParameter, ',');
		List<RequestedValue> params = new ArrayList<RequestedValue>();
		for (String p : producers){
			String[] tokens = StringUtils.tokenize(p, '.');
			params.add(new RequestedValue(tokens[0], tokens[1], tokens[2]));
		}
		
		ArrayList<ChartDataEntityBean> ret = new ArrayList<ChartDataEntityBean>();
		
		
		//processing parameters
		for (RequestedValue v : params){
			IStatsProducer producer = registry.getProducer(v.getProducerId());
			if (producer==null){
				continue;
			}
			List<IStats> stats = producer.getStats();
			for (IStats s : stats){
				if (s.getName().equals(v.getStatId())){
					ChartDataEntityBean bean = new ChartDataEntityBean();
					bean.setProducerId(producer.getProducerId());
					bean.setStatName(s.getName());
					bean.setStatValueName(v.getValue());
					String value = s.getValueByNameAsString(v.getValue(), intervalName, unit);
					if (value!=null)
						bean.setStatValue(value);
					else
						bean.setStatValue("0");
					ret.add(bean);
				}
			}
		}
		
		req.setAttribute("data", ret);
		return mapping.findCommand( getForward(req) );
	}
	
	/**
	 * Parameter wrapper class.
	 * @author lrosenberg.
	 *
	 */
	public static class RequestedValue{
		/**
		 * ProducerId parameter.
		 */
		private String producerId;
		/**
		 * Stat id parameter.
		 */
		private String statId;
		/**
		 * Stat value name parameter.
		 */
		private String value;
		
		/**
		 * Default constructor.
		 * @param aProducerId
		 * @param aStatId
		 * @param aValue
		 */
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
