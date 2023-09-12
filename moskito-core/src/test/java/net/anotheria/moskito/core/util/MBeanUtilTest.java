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
            throws MBeanRegistrationException, MalformedObjectNameException, NotCompliantMBeanException,
            IntrospectionException, InstanceNotFoundException, ReflectionException {

        final String sampleBeanName = "SampleBean:test=test";
        final SampleBean sampleBean = new SampleBean("test_id");
        final SampleBean anotherSampleBean = new SampleBean("other_test_id");
        final MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();

        // Register method should return bean name
        assertEquals(sampleBeanName,
                MBeanUtil.getInstance().registerMBean(sampleBean, sampleBeanName)
        );

        // Trying to register bean with duplicate name with replace set to false
        // Method should return name with copy prefix
        assertEquals("copy1." + sampleBeanName,
                MBeanUtil.getInstance().registerMBean(sampleBean, sampleBeanName, false)
        );

        // Trying to register mbean with duplicate name with replace set to true
        // Method should replace old mbean
        assertEquals(sampleBeanName,
                MBeanUtil.getInstance().registerMBean(anotherSampleBean, sampleBeanName, true)
        );

        // Getting mbean info to determinate that mbean was replaced
        MBeanInfo info = mbs.getMBeanInfo(new ObjectName(sampleBeanName));
        assertEquals("other_test_id", info.getDescription());

        // Must return true on success remove
        assertTrue(
                MBeanUtil.getInstance().unregisterMBean(sampleBeanName)
        );

        // Unregistration should not be success due that bean is already removed
        assertFalse(
                MBeanUtil.getInstance().unregisterMBean(sampleBeanName)
        );

        // Trying to unregister bean with non existing name.
        // Method should return false
        assertFalse(
                MBeanUtil.getInstance().unregisterMBean("non_existing_bean")
        );

        // Checking that MBean with test name is not registered
        assertFalse(mbs.isRegistered(new ObjectName(sampleBeanName)));

    }

    @Test
    public void testCleanup()
            throws MBeanRegistrationException, MalformedObjectNameException, NotCompliantMBeanException {

        final SampleBean sampleBean = new SampleBean("test");

        final String sampleBeanName1 = "SampleBean:test=test,order=1";
        final String sampleBeanName2 = "SampleBean:test=test,order=2";
        final String sampleBeanName3 = "SampleBean:test=test,order=3";

        final MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();

        MBeanUtil.getInstance().registerMBean(sampleBean, sampleBeanName1);
        MBeanUtil.getInstance().registerMBean(sampleBean, sampleBeanName2);
        MBeanUtil.getInstance().registerMBean(sampleBean, sampleBeanName3);

        MBeanUtil.getInstance().cleanup(); // All registered beans have to be removed after cleanup() call

        // Checking that MBeans with test names is not registered
        assertFalse(mbs.isRegistered(new ObjectName(sampleBeanName1)));
        assertFalse(mbs.isRegistered(new ObjectName(sampleBeanName2)));
        assertFalse(mbs.isRegistered(new ObjectName(sampleBeanName3)));

    }

    /**
     * MBean mock for testing
     */
    private static class SampleBean implements DynamicMBean{

        private String id;

        private SampleBean(String id){
            this.id = id;
        }

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
            return new MBeanInfo("Anonymous", id, null, null, null, null);
        }
    };

}
