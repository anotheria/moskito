package net.anotheria.moskito.webui.gauges.api;

import net.anotheria.anoplass.api.APIException;
import net.anotheria.anoplass.api.APIInitException;
import net.anotheria.moskito.core.config.MoskitoConfiguration;
import net.anotheria.moskito.core.config.MoskitoConfigurationHolder;
import net.anotheria.moskito.core.config.gauges.GaugeConfig;
import net.anotheria.moskito.core.config.gauges.GaugeValueConfig;
import net.anotheria.moskito.core.config.gauges.GaugeZoneConfig;
import net.anotheria.moskito.core.config.gauges.GaugesConfig;
import net.anotheria.moskito.core.producers.IStats;
import net.anotheria.moskito.core.producers.IStatsProducer;
import net.anotheria.moskito.core.registry.IProducerRegistryAPI;
import net.anotheria.moskito.core.registry.NoSuchProducerException;
import net.anotheria.moskito.core.registry.ProducerRegistryAPIFactory;
import net.anotheria.moskito.core.stats.TimeUnit;
import net.anotheria.moskito.core.decorators.value.LongValueAO;
import net.anotheria.moskito.core.decorators.value.StatValueAO;
import net.anotheria.moskito.core.decorators.value.StringValueAO;
import net.anotheria.moskito.webui.shared.api.AbstractMoskitoAPIImpl;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Gauge api implementation.
 *
 * @author lrosenberg
 * @since 25.03.15 11:41
 */
public class GaugeAPIImpl extends AbstractMoskitoAPIImpl implements GaugeAPI {

	/**
	 * RegistryAPI.
	 */
	private IProducerRegistryAPI producerRegistryAPI;

	private List<GaugeZoneAO> defaultZones;

	@Override
	public void init() throws APIInitException {
		super.init();
		producerRegistryAPI = new ProducerRegistryAPIFactory().createProducerRegistryAPI();

		MoskitoConfiguration configuration = getConfiguration();
		GaugeZoneConfig[] defaultZonesConfig = configuration.getGaugesConfig().getDefaultZones();
		if (defaultZonesConfig == null || defaultZonesConfig.length == 0){
			defaultZones = createDefaultZones();
		}else{
			defaultZones = new ArrayList<>(defaultZonesConfig.length);
			for (GaugeZoneConfig zoneConfig : defaultZonesConfig){
				defaultZones.add(zoneAOFromZoneConfig(zoneConfig));
			}
		}
	}

	/**
	 * This method creates some default zones if nothing is configured.
	 * @return
	 */
	private List<GaugeZoneAO> createDefaultZones(){
		ArrayList<GaugeZoneAO> ret = new ArrayList<>(1);
		GaugeZoneAO redZone = new GaugeZoneAO();
		redZone.setColor("red");
		redZone.setLeft(0.9f);
		redZone.setRight(1.0f);
		ret.add(redZone);
		return ret;
	}

	@Override
	public List<GaugeAO> getGauges(){
		GaugesConfig gg = MoskitoConfigurationHolder.getConfiguration().getGaugesConfig();
		List<GaugeAO> gaugeAOList = new LinkedList<GaugeAO>();
		if (gg == null || gg.getGauges() == null || gg.getGauges().length == 0)
			return gaugeAOList;
		for (GaugeConfig g : gg.getGauges()){
			GaugeAO ao = createGaugeAO(g);
			gaugeAOList.add(ao);
		}
		return gaugeAOList;
	}

	@Override
	public List<GaugeAO> getGauges(String... names) throws APIException {
		if (names==null)
			throw new APIException("Unexpected parameter null for gauge names");

		LinkedList<GaugeAO> ret = new LinkedList<GaugeAO>();
		for (String name : names){
			GaugeConfig config = getGaugeConfigByName(name);
			ret.add(createGaugeAO(config));
		}
		return ret;
	}

	private GaugeConfig getGaugeConfigByName(String name) throws APIException{
		GaugesConfig gg = MoskitoConfigurationHolder.getConfiguration().getGaugesConfig();
		for (GaugeConfig g : gg.getGauges()){
			if (g.getName().equals(name))
				return g;
		}
		throw new APIException("Can't find gauge configuration for '"+name+ '\'');

	}

	private GaugeAO createGaugeAO(GaugeConfig fromConfig){

		GaugeAO ao = new GaugeAO();
		ao.setName(fromConfig.getName());
		ao.setCaption(fromConfig.getCaption());
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
		ao.setComplete(complete);


		//prepare zones
		if (fromConfig.getZones()!=null && fromConfig.getZones().length>0){
			for (GaugeZoneConfig zoneConfig : fromConfig.getZones()){
				ao.addZone(zoneAOFromZoneConfig(zoneConfig));
			}
		}else{
			ao.setZones(defaultZones);
		}

		return ao;
	}

	private GaugeZoneAO zoneAOFromZoneConfig(GaugeZoneConfig zoneConfig){
		GaugeZoneAO zoneAO = new GaugeZoneAO();
		zoneAO.setColor(zoneConfig.getColor());
		zoneAO.setLeft(zoneConfig.getLeft());
		zoneAO.setRight(zoneConfig.getRight());
		return zoneAO;
	}

	private StatValueAO gaugeValue2statValue(GaugeValueConfig config){
		if (config.getConstant()!=null){
			return new LongValueAO(null, (long)config.getConstant());
		}

		//ok, its not a constant
		IStatsProducer producer = null;
		try{
			producer = producerRegistryAPI.getProducer(config.getProducerName());
		}catch(NoSuchProducerException e){
			//this is alternative to producer == null and is handled in next if.
		}
		if (producer == null )
			return new StringValueAO(null, "no producer");
		for (IStats s : (List<IStats>)producer.getStats()){
			if (s.getName().equals(config.getStatName())){
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
