package net.anotheria.moskito.webui.gauges.action;

import net.anotheria.maf.action.ActionCommand;
import net.anotheria.maf.action.ActionMapping;
import net.anotheria.maf.bean.FormBean;
import net.anotheria.moskito.core.config.MoskitoConfigurationHolder;
import net.anotheria.moskito.core.config.gauges.GaugeConfig;
import net.anotheria.moskito.core.config.gauges.GaugeValueConfig;
import net.anotheria.moskito.core.config.gauges.GaugesConfig;
import net.anotheria.moskito.core.producers.IStats;
import net.anotheria.moskito.core.producers.IStatsProducer;
import net.anotheria.moskito.core.registry.IProducerRegistryAPI;
import net.anotheria.moskito.core.registry.ProducerRegistryAPIFactory;
import net.anotheria.moskito.core.stats.TimeUnit;
import net.anotheria.moskito.webui.gauges.api.GaugeAO;
import net.anotheria.moskito.webui.producers.api.LongValueAO;
import net.anotheria.moskito.webui.producers.api.StatValueAO;
import net.anotheria.moskito.webui.producers.api.StringValueAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.LinkedList;
import java.util.List;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 23.03.15 21:38
 */
public class ShowGaugesAction extends BaseGaugesAction{

	private static Logger log = LoggerFactory.getLogger(ShowGaugesAction.class);

	@Override
	public ActionCommand execute(ActionMapping actionMapping, FormBean formBean, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {

		GaugesConfig gg = MoskitoConfigurationHolder.getConfiguration().getGaugesConfig();
		System.out.println("SHOW GAUGES");
		List<GaugeAO> gaugeAOList = new LinkedList<GaugeAO>();
		for (GaugeConfig g : gg.getGauges()){
			System.out.println("Gauge config: "+g);
			GaugeAO ao = createGaugeAO(g);
			System.out.println(" ==> "+ao);
			gaugeAOList.add(ao);
		}

		httpServletRequest.setAttribute("gauges", gaugeAOList);
		System.out.println("set gauges: "+gaugeAOList);
		return actionMapping.success();
	}

	private GaugeAO createGaugeAO(GaugeConfig fromConfig){

		GaugeAO ao = new GaugeAO();
		ao.setName(fromConfig.getName());
		boolean complete = true;
		StatValueAO current = gaugeValue2statValue(fromConfig.getCurrentValue());
		ao.setCurrent(current);
		StatValueAO min = gaugeValue2statValue(fromConfig.getMinValue());
		ao.setMin(min);
		StatValueAO max = gaugeValue2statValue(fromConfig.getMaxValue());
		ao.setMax(max);
		if (current instanceof StringValueAO ||
				min instanceof StringValueAO ||
				max instanceof StringValueAO
				) {
			complete = false;
		}
		ao.setComplete(true);
		return ao;
	}

	private StatValueAO gaugeValue2statValue(GaugeValueConfig config){
		if (config.getConstant()!=null){
			return new LongValueAO(null, (long)config.getConstant());
		}

		//ok, its not a constant
		IProducerRegistryAPI regApi = new ProducerRegistryAPIFactory().createProducerRegistryAPI(); //really new?
		IStatsProducer producer = regApi.getProducer(config.getProducerName());
		if (producer == null )
			return new StringValueAO(null, "no producer");
		for (IStats s : (List<IStats>)producer.getStats()){
			if (s.getName().equals(config.getStatName())){
				System.out.println("Found stats: "+s);
				String value = s.getValueByNameAsString(config.getValueName(), config.getIntervalName(), TimeUnit.valueOf(config.getTimeUnit()));
				try {
					return new LongValueAO(null, Long.parseLong(value));
				}catch(NumberFormatException e){
					log.error("Can't parse long value, probably invalid value for gauge " + config);
					return new StringValueAO(null, "Error");
				}
			}

		}


		return new StringValueAO(null, "n.A.");
	}



}
