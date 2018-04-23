package net.anotheria.moskito.core.util;

import net.anotheria.moskito.core.accumulation.Accumulators;
import net.anotheria.moskito.core.config.MoskitoConfigurationHolder;
import net.anotheria.moskito.core.predefined.GlobalRequestProcessorStats;
import net.anotheria.moskito.core.producers.IStats;
import net.anotheria.moskito.core.producers.IStatsProducer;
import net.anotheria.moskito.core.registry.ProducerRegistryFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.management.Attribute;
import javax.management.AttributeList;
import javax.management.InstanceNotFoundException;
import javax.management.MBeanServer;
import javax.management.MBeanServerFactory;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectInstance;
import javax.management.ObjectName;
import javax.management.ReflectionException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TimerTask;

/**
 * Producer for values supplied by Global Request Processor.
 *
 */
public class BuiltinGlobalRequestProcessorProducer extends AbstractBuiltInProducer implements IStatsProducer, BuiltInProducer {

    /**
     * Mbean name.
     */
    private static final String MBEAN_NAME = "Catalina:name=\"%s\",type=GlobalRequestProcessor";

    /**
     * Attribute names array.
     */
    private static final String[] ATTRIBUTE_NAMES = new String[]{
            "requestCount",
            "maxTime",
            "bytesReceived",
            "bytesSent",
            "processingTime",
            "errorCount"
    };

    /**
     * Stats container.
     */
    private List<IStats> iStats;

    /**
     * The monitored mBeans.
     */
    private List<ObjectInstance> mBeans;

    /**
     * The monitored mbeans server.
     */
    private MBeanServer mBeanServer;

    /**
     * Logger.
     */
    private static final Logger log = LoggerFactory.getLogger(BuiltinGlobalRequestProcessorProducer.class);

    /**
     * Default Constructor, init mbean, mbeanServer, iStats.
     */
    public BuiltinGlobalRequestProcessorProducer() {
        List<MBeanServer> servers = MBeanServerFactory.findMBeanServer(null);

		boolean isAjp = MoskitoConfigurationHolder.getConfiguration().getTomcatRequestProcessorProducerConfig().isAjp();
		boolean isHttp = MoskitoConfigurationHolder.getConfiguration().getTomcatRequestProcessorProducerConfig().isHttp();
		String key = isAjp && isHttp ? "*" : isHttp ? "http*" : "ajp*";

		try {
			for (MBeanServer server : servers) {
				Set<ObjectInstance> instances = server.queryMBeans(new ObjectName(String.format(MBEAN_NAME, key)), null);
				if (instances.size() == 0) {
					continue;
				}
				List<String> beanNames = initFieldsAndCreateStats(server, instances);
				addUpdaterTask();
				ProducerRegistryFactory.getProducerRegistryInstance().registerProducer(this);
				Accumulators.createGlobalRequestProcessorAccumulators(beanNames);
				break;
			}
		} catch (MalformedObjectNameException e) {
			log.error("Failed to get GlobalRequestProcessor mbeans", e);
		}
    }

    @Override
    public String getCategory() {
        return "tomcat";
    }

    @Override
    public String getProducerId() {
        return "GlobalRequestProcessor";
    }

    @Override
    public List<IStats> getStats() {
        return iStats;
    }

	private List<String> initFieldsAndCreateStats(MBeanServer server, Set<ObjectInstance> instances) {
		iStats = new ArrayList<>();
		mBeans = new ArrayList<>();
		mBeanServer = server;
		List<String> beanNames = new ArrayList<>();
		for (ObjectInstance instance : instances) {
			mBeans.add(instance);
			String beanName = MBeanProducerFactory.normalize(instance.getObjectName().getKeyProperty("name"));
			GlobalRequestProcessorStats stats = new GlobalRequestProcessorStats(beanName);
			iStats.add(stats);
			beanNames.add(beanName);
		}
		return beanNames;
	}


	private void addUpdaterTask() {
		BuiltinUpdater.addTask(new TimerTask() {
			@Override
			public void run() {
				readAttributes();
			}
		});
	}

    private void readAttributes() {
        try {
            int i = 0;
            for (ObjectInstance mbean : mBeans) {
                AttributeList attributeList = mBeanServer.getAttributes(mbean.getObjectName(), ATTRIBUTE_NAMES);
                if (attributeList.size() != ATTRIBUTE_NAMES.length) {
                    log.error("Failed to read GlobalRequestProcessor mbean attributes: " + attributeList.asList().toString());
                    return;
                }
                Map<String, Long> attributesMap = new HashMap<>();
                for (Attribute attribute : attributeList.asList()) {
                    attributesMap.put(attribute.getName(), Long.valueOf(attribute.getValue().toString()));
                }
                ((GlobalRequestProcessorStats) iStats.get(i)).update(
                        attributesMap.get(ATTRIBUTE_NAMES[0]),
                        attributesMap.get(ATTRIBUTE_NAMES[1]),
                        attributesMap.get(ATTRIBUTE_NAMES[2]),
                        attributesMap.get(ATTRIBUTE_NAMES[3]),
                        attributesMap.get(ATTRIBUTE_NAMES[4]),
                        attributesMap.get(ATTRIBUTE_NAMES[5])
                );
                i++;
            }
        } catch (InstanceNotFoundException | ReflectionException e) {
            log.error("Failed to read GlobalRequestProcessor mbean attributes ", e);
        }
    }

}
