package net.anotheria.moskito.webui.shared.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import net.anotheria.anoplass.api.APIException;
import net.anotheria.moskito.core.config.MoskitoConfiguration;
import net.anotheria.moskito.core.config.MoskitoConfigurationHolder;
import net.anotheria.moskito.core.config.plugins.PluginConfig;
import net.anotheria.moskito.core.plugins.MoskitoPlugin;
import net.anotheria.moskito.core.plugins.PluginRepository;
import net.anotheria.moskito.core.stats.Interval;
import net.anotheria.moskito.core.stats.impl.IntervalRegistry;
import net.anotheria.moskito.core.timing.IUpdateable;
import net.anotheria.moskito.core.util.BuiltInErrorProducer;
import net.anotheria.moskito.core.util.CaughtError;
import net.anotheria.moskito.core.util.ErrorCatcher;
import net.anotheria.moskito.webui.plugins.VisualMoSKitoPlugin;
import net.anotheria.util.NumberUtils;
import net.anotheria.util.sorter.DummySortType;
import net.anotheria.util.sorter.SortType;
import net.anotheria.util.sorter.StaticQuickSorter;

import javax.management.*;
import java.util.*;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 24.03.14 22:53
 */
public class AdditionalFunctionalityAPIImpl extends AbstractMoskitoAPIImpl implements AdditionalFunctionalityAPI{

	/**
	 * Sort type.
	 */
	private SortType dummySortType;

	public AdditionalFunctionalityAPIImpl(){
		dummySortType = new DummySortType();
	}

	@Override
	public List<PluginAO> getPlugins() throws APIException {
		List<String> pluginNames = PluginRepository.getInstance().getPluginNames();
		List<PluginAO> ret = new ArrayList<>(pluginNames.size());
		for (String s : pluginNames){
			PluginAO ao = new PluginAO();

			ao.setName(s);
			MoskitoPlugin plugin = PluginRepository.getInstance().getPlugin(s);

			try{
				ao.setDescription(String.valueOf(plugin));
			}catch(Exception e){
				ao.setDescription("Error: "+e.getMessage());
			}

			PluginConfig config = PluginRepository.getInstance().getConfig(s);
			if (config==null){
				ao.setClassName("-not found-");
				ao.setConfigurationName("-not found-");
			}else{
				ao.setConfigurationName(config.getConfigurationName());
				ao.setClassName(config.getClassName());
			}

			if (plugin instanceof VisualMoSKitoPlugin){
				VisualMoSKitoPlugin vmp = (VisualMoSKitoPlugin)plugin;
				ao.setSubNaviItemIcon(vmp.getSubMenuIcon());
				ao.setSubNaviItemText(vmp.getSubMenuName());
				ao.setNavigationEntryAction(vmp.getNavigationEntryAction());
				ao.setWebEnabled(true);
			}

			ret.add(ao);
		}
		return ret;

	}

	@Override
	public void removePlugin(String pluginName) throws APIException {
		PluginRepository.getInstance().removePlugin(pluginName);
	}

	@Override
	public void forceIntervalUpdate(String intervalName) throws APIException {
		IntervalRegistry registry = IntervalRegistry.getInstance();
		Interval interval = registry.getInterval(intervalName);
		((IUpdateable)interval).update();
	}

	@Override public List<MBeanWrapperAO> getMBeans() throws APIException{
		try{
			List<MBeanServer> servers = MBeanServerFactory.findMBeanServer(null);
			List<MBeanWrapperAO> beans = new ArrayList<>(servers.size());

			for (MBeanServer s : servers){
				Set<ObjectInstance> instances = s.queryMBeans(null, null);
				for (ObjectInstance oi : instances){
					MBeanWrapperAO bean = new MBeanWrapperAO();
					bean.setClassName(oi.getClassName());
					ObjectName name = oi.getObjectName();
					bean.setDomain(name.getDomain());
					bean.setCanonicalName(name.getCanonicalName());
					String type = name.getKeyProperty("type");
					if (type!=null){
						bean.setType(type);
					}

					MBeanInfo info = s.getMBeanInfo(name);
					if (info!=null){
						bean.setDescription(info.getDescription());
						bean.setAttributes(convert(info.getAttributes(), s, name));
						bean.setOperations(Arrays.asList(info.getOperations()));
					}
					beans.add(bean);
				}
			}
			return beans;
		}catch(JMException e){
			throw new APIException("JMX Failure "+e.getMessage(), e);
		}
	}

	/**
	 * @param infos
	 *            the {@link javax.management.MBeanAttributeInfo} to wrap
	 * @param server
	 *            {@link MBeanServer} where to find the MBean values
	 * @param name
	 *            {@link ObjectName} where to find the MBean values
	 * @return the converted list of {@link MBeanAttributeWrapperAO}s
	 */
	private List<MBeanAttributeWrapperAO> convert(final MBeanAttributeInfo[] infos,
												final MBeanServer server, final ObjectName name) {
		final List<MBeanAttributeWrapperAO> res = new ArrayList<>(infos.length);

		for (final MBeanAttributeInfo info : infos) {
			Object value = "-";
			try {
				value = server.getAttribute(name, info.getName());
				if (value instanceof Object[]){
					value = Arrays.asList((Object[])value);
				}

				// CHECKSTYLE:OFF - we have to catch ALL exceptions
			} catch (final Exception e) {
				// CHECKSTYLE:ON
				log.debug("unable to read MBean: " + e.getLocalizedMessage());
			}

			res.add(new MBeanAttributeWrapperAO(info, value));

		}

		return res;
	}

	@Override
	public List<IntervalInfoAO> getIntervalInfos() throws APIException {
		List<Interval> intervals = IntervalRegistry.getInstance().getIntervals();
		List<IntervalInfoAO> ret = new ArrayList<IntervalInfoAO>(intervals.size());
		for (Interval interval : intervals){
		 	IntervalInfoAO info = new IntervalInfoAO();
			info.setName(interval.getName());
			info.setUpdateTimestamp(NumberUtils.makeISO8601TimestampString(interval.getLastUpdateTimestamp()));
			info.setLength(interval.getLength());
			ret.add(info);
		}
		return StaticQuickSorter.sort(ret, dummySortType);
	}

	@Override
	public String getConfigurationAsString() throws APIException {
		MoskitoConfiguration config = MoskitoConfigurationHolder.getConfiguration();
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String jsonOutput = gson.toJson(config);



		JsonParser jp = new JsonParser();
		JsonElement je = jp.parse(jsonOutput);
		String prettyJsonString = gson.toJson(je);
		return prettyJsonString;
	}

	@Override
	public Long getIntervalUpdateTimestamp(String intervalName) throws APIException {
		return IntervalRegistry.getInstance().getUpdateTimestamp(intervalName);
	}

	public MoskitoConfiguration getConfiguration(){
		return MoskitoConfigurationHolder.getConfiguration();
	}

	@Override
	public List<ErrorCatcherAO> getActiveErrorCatchers() throws APIException {
		List<ErrorCatcher> catchers = BuiltInErrorProducer.getInstance().getCatchers();
		List<ErrorCatcherAO> catcherAOs = new LinkedList<>();
		for (ErrorCatcher c : catchers){
			ErrorCatcherAO ao = new ErrorCatcherAO();
			ao.setName(c.getName());
			ao.setCount(c.getErrorList().size());
			catcherAOs.add(ao);
		}
		return catcherAOs;
	}

	@Override
	public List<CaughtErrorAO> getCaughtErrorsByExceptionName(String exceptionName) throws APIException {
		ErrorCatcher catcher = null;
		List<CaughtErrorAO> ret = new ArrayList<>();
		try {
			catcher = BuiltInErrorProducer.getInstance().getCatcher(Class.forName(exceptionName));
			List<CaughtError> errors = catcher.getErrorList();
			for (CaughtError error : errors){
			 	ret.add(makeCaughtErrorAO(error));
			}
		}catch(Exception any){
			throw new APIException("Couldn't retrieve class for exception "+exceptionName);
		}
		return ret;
	}

	private CaughtErrorAO makeCaughtErrorAO(CaughtError error){
		CaughtErrorAO ao = new CaughtErrorAO();
		ao.setMessage(error.getThrowable().getMessage());
		ao.setTimestamp(error.getTimestamp());
		ao.setDate(NumberUtils.makeISO8601TimestampString(error.getTimestamp()));
		ao.setElements(Arrays.asList(error.getThrowable().getStackTrace()));
		StringBuilder tags = new StringBuilder();
		for (Map.Entry<String, String> tag : error.getTags().entrySet()){
			tags.append(" ").append(tag.getKey()).append(": ").append(tag.getValue());
		}
		ao.setTagLine(tags.toString());

		return ao;
	}
}
