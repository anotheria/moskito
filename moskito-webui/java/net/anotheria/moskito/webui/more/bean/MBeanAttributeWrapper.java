package net.anotheria.moskito.webui.more.bean;

import javax.management.MBeanAttributeInfo;

/**
 * @author Michael König
 */
public class MBeanAttributeWrapper {

    private final MBeanAttributeInfo attribInfo;
    private final Object value;

    /**
     * Constructs an MBeanAttributeWrapper.
     */
    public MBeanAttributeWrapper(final MBeanAttributeInfo attribInfo, final Object value) {
        this.attribInfo = attribInfo;
        this.value = value;
    }

    /**
     * @return
     * @see javax.management.MBeanFeatureInfo#getDescription()
     */
    public String getDescription() {
        return attribInfo.getDescription();
    }

    /**
     * @return the {@link MBeanAttributeInfo}
     */
    public MBeanAttributeInfo getMBeanAttributeInfo() {
        return attribInfo;
    }

    /**
     * @return
     * @see javax.management.MBeanFeatureInfo#getName()
     */
    public String getName() {
        return attribInfo.getName();
    }

    /**
     * @return
     * @see javax.management.MBeanAttributeInfo#getType()
     */
    public String getType() {
        return attribInfo.getType();
    }

    /**
     * @return the current mbean attributes value
     */
    public Object getValue() {
        return value;
    }

    /**
     * @return
     * @see javax.management.MBeanAttributeInfo#isReadable()
     */
    public boolean isReadable() {
        return attribInfo.isReadable();
    }

    /**
     * @return
     * @see javax.management.MBeanAttributeInfo#isWritable()
     */
    public boolean isWritable() {
        return attribInfo.isWritable();
    }

}