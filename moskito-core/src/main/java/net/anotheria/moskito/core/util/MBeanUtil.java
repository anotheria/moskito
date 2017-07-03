package net.anotheria.moskito.core.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.management.*;
import java.lang.management.ManagementFactory;
import java.util.LinkedList;

/**
 * Utility class to register and unregister MBeans.
 * Methods that performs registration/unregistration is
 * synchronized on MBean server.
 */
public class MBeanUtil {

    private static Logger log = LoggerFactory.getLogger(MBeanUtil.class);

    /**
     * MBean server instance
     */
    private final MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();
    /**
     * List with all registered by this object MBeans
     * Required for cleanup beans
     */
    private LinkedList<ObjectName> beansNames = new LinkedList<>();

    /**
     * Singleton instance of util class
     */
    private static MBeanUtil instance = new MBeanUtil();

    /**
     * Prevents class instance creation except singleton
     */
    private MBeanUtil(){}

    public static MBeanUtil getInstance(){
        return instance;
    }

    /**
     * Used to resolve MBean name duplication
     * by adding prefix to name.
     *
     * Prefix format:
     *  copy{$number}.{$originalName}
     *  where:
     *      $number       - bean copy number (1 for first copy)
     *      $originalName - original bean name passed to {@link MBeanUtil#registerMBean(Object, String, boolean)} method
     *
     * @param name MBean name to change
     * @return change MBean name with prefix
     */
    /* for testing */ static String resolveDuplicateName(String name){

        if(name.matches("^copy\\d+\\..*$")){ // If true - means bean name already contain copy prefix

            // Parsing copy number from prefix to increase it

            // Copy number contains in the prefix end -
            // StringBuilder is required to reverse string
            StringBuilder copyPrefix =
                    new StringBuilder(
                            name.substring(0, name.indexOf('.'))
                    );

            // Collects copy number digits
            StringBuilder copyNumber = new StringBuilder();

            // Reverting copy prefix and collecting digits
            for(Character prefixChar : copyPrefix.reverse().toString().toCharArray())
                if(Character.isDigit(prefixChar))
                    copyNumber.append(prefixChar);
                else break; // loop reach end of 'copy' word. No copy number digits left

            return "copy" +
                    // Parsing copy number to integer and increase it
                    String.valueOf(
                            Integer.valueOf(
                                    // Digits are collected in reverse order, reverse it again
                                    copyNumber.reverse().toString()
                            ) + 1
                    ) + "." +
                    name.substring(name.indexOf('.') + 1); // Remove old prefix

        }
        else
            return "copy1." + name;

    }

    /**
     * Overloaded method with 'replace' parameter set to false.
     * See:
     *  {@link MBeanUtil#registerMBean(Object, String, boolean)}
     */
    public String registerMBean(Object bean, String name)
            throws MalformedObjectNameException, NotCompliantMBeanException, MBeanRegistrationException {
        return registerMBean(bean, name, false);
    }

    /**
     * Registers MBean in local MBean server.
     *
     * Method has mechanism to resolve bean name duplication
     * depends on `replace` parameter of ths method.
     *
     * If true - old bean be replaced by new one.
     * If false - prefix been added to bean name from method parameter
     *
     * Prefix format:
     * copy{$number}.{$originalName}
     * where:
     *     $number       - bean copy number (1 for first copy)
     *     $originalName - original bean name passed to this method
     *
     * @param bean MBean object
     * @param name MBean name
     * @param replace controls resolving of bean name duplication.
     *                 true  - old bean be replaced by new one.
     *                 false - prefix be added to bean name.
     *
     * @return name of MBean string
     *
     * @throws MalformedObjectNameException name from parameter is unacceptable as object name
     * @throws NotCompliantMBeanException thrown by MBeanServer
     * @throws MBeanRegistrationException thrown by MBeanServer
     */
    public String registerMBean(Object bean, String name, boolean replace)
            throws MalformedObjectNameException, NotCompliantMBeanException, MBeanRegistrationException{

        synchronized (mBeanServer) {

            ObjectName newBeanName = null;

            try {
                newBeanName = new ObjectName(name);
                beansNames.add(newBeanName); // Saving bean name for possible later cleanups
                mBeanServer.registerMBean(bean, newBeanName);
                return name;
            } catch (InstanceAlreadyExistsException e) {

                beansNames.remove(newBeanName); // Bean not registered, it name is not required for cleanup

                if (replace) {
                    unregisterMBean(name);
                    return registerMBean(bean, name, true);
                } else
                    return registerMBean(bean, resolveDuplicateName(name));

            }

        }

    }

    /**
     * Unregisters MBean from platform MBean server.
     * @param name name of MBean to unregister
     * @return true - bean unregistered
     *         false - bean with such name not found
     * @throws MBeanRegistrationException thrown by MBeanServer
     */
    public boolean unregisterMBean(String name) throws MBeanRegistrationException {

        synchronized (mBeanServer) {

            ObjectName toRemoveName;

            try {
                toRemoveName = new ObjectName(name);
            } catch (MalformedObjectNameException e) {
                return false; // bean with malformed can not be registered.
            }

            beansNames.remove(toRemoveName);

            try {
                mBeanServer.unregisterMBean(toRemoveName);
                return true;
            } catch (InstanceNotFoundException e) {
                return false;
            }

        }

    }

    /**
     * Unregisters all beans, that been registered by this utility object.
     */
    public void cleanup(){

        synchronized (mBeanServer) {

            for (ObjectName beanName : beansNames)
                try {
                    mBeanServer.unregisterMBean(beanName);
                } catch (InstanceNotFoundException ignored) {
                    // Bean previously been unregistered
                } catch (MBeanRegistrationException e) {
                    log.warn("Exception thrown while trying to clean up registered MBeans", e);
                }

        }

    }


}
