package net.anotheria.moskito.core.util;

import net.anotheria.moskito.core.config.MoskitoConfigurationHolder;
import net.anotheria.moskito.core.predefined.GlobalRequestProcessorStats;
import net.anotheria.moskito.core.producers.IStats;
import net.anotheria.moskito.core.producers.IStatsProducer;
import net.anotheria.moskito.core.registry.ProducerRegistryFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.management.Attribute;
import javax.management.AttributeList;
import javax.management.BadAttributeValueExpException;
import javax.management.BadBinaryOpValueExpException;
import javax.management.BadStringOperationException;
import javax.management.InstanceNotFoundException;
import javax.management.InvalidApplicationException;
import javax.management.MBeanServer;
import javax.management.MBeanServerFactory;
import javax.management.ObjectInstance;
import javax.management.ObjectName;
import javax.management.QueryExp;
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
 * @author esmakula
 */
public class BuiltinGlobalRequestProcessorProducer extends AbstractBuiltInProducer implements IStatsProducer, BuiltInProducer {

    /**
     * AJP Connector.
     */
    private static final String AJP = "ajp";

    /**
     * HTTP connector.
     */
    private static final String HTTP = "http";

    /**
     * Global Request Processor type.
     */
    private static final String  TYPE_PROCESSOR = "GlobalRequestProcessor";

    /**
     * Catalina domain.
     */
    private static final String DOMAIN_CATALINA = "Catalina";

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

        for (MBeanServer server : servers) {
            Set<ObjectInstance> instances = server.queryMBeans(null, new QueryExp() {
                @Override
                public boolean apply(ObjectName name) throws BadStringOperationException, BadBinaryOpValueExpException, BadAttributeValueExpException, InvalidApplicationException {
                    String type = name.getKeyProperty("type");
                    String nameProperty = name.getKeyProperty("name");
                    boolean isAjp = MoskitoConfigurationHolder.getConfiguration().getTomcatRequestProcessorProducerConfig().isAjp();
                    boolean isHttp = MoskitoConfigurationHolder.getConfiguration().getTomcatRequestProcessorProducerConfig().isHttp();
                    return name.getDomain().equals(DOMAIN_CATALINA) && type.equals(TYPE_PROCESSOR)
                            && (isAjp && nameProperty.toLowerCase().contains(AJP)
                                || isHttp && nameProperty.toLowerCase().contains(HTTP));
                }

                @Override
                public void setMBeanServer(MBeanServer s) {
                }
            });
            if (instances.size() > 0) {
                iStats = new ArrayList<>();
                mBeans = new ArrayList<>();
                mBeanServer = server;
                for (ObjectInstance instance : instances) {
                    mBeans.add(instance);
                    GlobalRequestProcessorStats stats = new GlobalRequestProcessorStats(instance.getObjectName().getKeyProperty("name"));
                    iStats.add(stats);
                }
                BuiltinUpdater.addTask(new TimerTask() {
                    @Override
                    public void run() {
                        readAttributes();
                    }
                });
                ProducerRegistryFactory.getProducerRegistryInstance().registerProducer(this);
                break;
            }
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

    /**
     * Reads the management bean attributes and extracts monitored data on regular base.
     */
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
