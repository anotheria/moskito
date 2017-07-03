package net.anotheria.moskito.core.util;

import org.junit.Test;

import javax.management.*;

import java.lang.management.ManagementFactory;

import static org.junit.Assert.*;

/**
 * Tests {@link MBeanUtil} class
 */
public class MBeanUtilTest {

    /**
     * Test modifying mbean name to prevent name duplication
     * mechanism of testing class
     */
    @Test
    public void testResolveDuplicateName(){

        final String originalName = "SampleMBeanName:test=test";
        String resolvedBeanName;

        resolvedBeanName = MBeanUtil.resolveDuplicateName(originalName);
        assertEquals("copy1." + originalName, resolvedBeanName);

        resolvedBeanName = MBeanUtil.resolveDuplicateName(resolvedBeanName);
        assertEquals("copy2." + originalName, resolvedBeanName);

        assertEquals("copy100." + originalName, MBeanUtil.resolveDuplicateName("copy99." + originalName));

    }

    @Test
    public void testRegisterAndUnregister()
            throws MBeanRegistrationException, MalformedObjectNameException, NotCompliantMBeanException {

        final String sampleBeanName = "SampleBean:test=test";

        MBeanUtil.getInstance().registerMBean(sampleBean, sampleBeanName);

        assertTrue(
                MBeanUtil.getInstance().unregisterMBean(sampleBeanName) // Must return true on success remove
        );

        assertFalse(
                // Unregistration should not be success due that bean is already removed
                MBeanUtil.getInstance().unregisterMBean(sampleBeanName)
        );

        // Checking that MBean with test name is not registered
        assertFalse(ManagementFactory.getPlatformMBeanServer().isRegistered(new ObjectName(sampleBeanName)));

    }

    @Test
    public void testCleanup()
            throws MBeanRegistrationException, MalformedObjectNameException, NotCompliantMBeanException {

        final String sampleBeanName1 = "SampleBean:test=test,order=1";
        final String sampleBeanName2 = "SampleBean:test=test,order=2";
        final String sampleBeanName3 = "SampleBean:test=test,order=3";

        final MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();

        MBeanUtil.getInstance().registerMBean(sampleBean, sampleBeanName1);
        MBeanUtil.getInstance().registerMBean(sampleBean, sampleBeanName2);
        MBeanUtil.getInstance().registerMBean(sampleBean, sampleBeanName3);

        MBeanUtil.getInstance().cleanup(); // All registered beans has to be removed after cleanup() call

        // Checking that MBeans with test names is not registered
        assertFalse(mbs.isRegistered(new ObjectName(sampleBeanName1)));
        assertFalse(mbs.isRegistered(new ObjectName(sampleBeanName2)));
        assertFalse(mbs.isRegistered(new ObjectName(sampleBeanName3)));

    }

    /**
     * Object for testing that can be used as MBean (at least in this test scope)
     */
    private static Object sampleBean = new DynamicMBean(){
        @Override
        public Object getAttribute(String attribute) throws AttributeNotFoundException, MBeanException, ReflectionException {
            return null;
        }

        @Override
        public void setAttribute(Attribute attribute) throws AttributeNotFoundException, InvalidAttributeValueException, MBeanException, ReflectionException {

        }

        @Override
        public AttributeList getAttributes(String[] attributes) {
            return null;
        }

        @Override
        public AttributeList setAttributes(AttributeList attributes) {
            return null;
        }

        @Override
        public Object invoke(String actionName, Object[] params, String[] signature) throws MBeanException, ReflectionException {
            return null;
        }

        @Override
        public MBeanInfo getMBeanInfo() {
            return new MBeanInfo("Anonymous", null, null, null, null, null);
        }
    };

}
