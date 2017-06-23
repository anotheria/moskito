package net.anotheria.moskito.extensions.tomcat;

import net.anotheria.moskito.core.producers.IStats;
import net.anotheria.moskito.core.producers.IStatsProducer;
import net.anotheria.moskito.core.util.BuiltinUpdater;
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
public class GlobalRequestProcessorProducer implements IStatsProducer {

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
     * The monitored mbean.
     */
    private ObjectInstance mBean;

    /**
     * The monitored mbean server.
     */
    private MBeanServer mBeanServer;

    /**
     * Logger.
     */
    private static final Logger log = LoggerFactory.getLogger(GlobalRequestProcessorProducer.class);

    /**
     * Default Constructor, init mbean, mbeanServer, iStats.
     */
    public GlobalRequestProcessorProducer() {
        List<MBeanServer> servers = MBeanServerFactory.findMBeanServer(null);
        for (MBeanServer s : servers) {
            Set<ObjectInstance> instances = s.queryMBeans(null, new QueryExp() {
                @Override
                public boolean apply(ObjectName name) throws BadStringOperationException, BadBinaryOpValueExpException, BadAttributeValueExpException, InvalidApplicationException {
                    String type = name.getKeyProperty("type");
                    return name.getDomain().equals("Catalina") && type.equals("GlobalRequestProcessor");
                }

                @Override
                public void setMBeanServer(MBeanServer s) {
                }
            });
            if (instances.size() == 0) {
                continue;
            }
            mBean = instances.iterator().next();
            mBeanServer = s;
            iStats = new ArrayList<>(1);
            iStats.add(new GlobalRequestProcessorStats());
            break;
        }
        BuiltinUpdater.addTask(new TimerTask() {
            @Override
            public void run() {
                readAttributes();
            }
        });
    }

    @Override
    public String getCategory() {
        return "mBean";
    }

    @Override
    public String getSubsystem() {
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
            AttributeList attributeList = mBeanServer.getAttributes(mBean.getObjectName(), ATTRIBUTE_NAMES);
            if (attributeList.size() != ATTRIBUTE_NAMES.length) {
                log.error("Failed to read GlobalRequestProcessor mbean attributes: " + attributeList.asList().toString());
                return;
            }
            Map<String, Long> attributesMap = new HashMap<>();
            for (Attribute attribute : attributeList.asList()) {
                attributesMap.put(attribute.getName(), Long.valueOf(attribute.getValue().toString()));
            }
            ((GlobalRequestProcessorStats) iStats.get(0)).update(
                    attributesMap.get(ATTRIBUTE_NAMES[0]),
                    attributesMap.get(ATTRIBUTE_NAMES[1]),
                    attributesMap.get(ATTRIBUTE_NAMES[2]),
                    attributesMap.get(ATTRIBUTE_NAMES[3]),
                    attributesMap.get(ATTRIBUTE_NAMES[4]),
                    attributesMap.get(ATTRIBUTE_NAMES[5])
            );
        } catch (InstanceNotFoundException | ReflectionException e) {
            log.error("Failed to read GlobalRequestProcessor mbean attributes ", e);
        }
    }

}
