package net.anotheria.moskito.central.storage;

import net.anotheria.moskito.central.Snapshot;
import net.anotheria.util.NumberUtils;
import net.anotheria.util.StringUtils;

import static net.anotheria.moskito.central.CentralConstants.PATH_TAG_CATEGORY;
import static net.anotheria.moskito.central.CentralConstants.PATH_TAG_COMPONENT;
import static net.anotheria.moskito.central.CentralConstants.PATH_TAG_DATE;
import static net.anotheria.moskito.central.CentralConstants.PATH_TAG_HOST;
import static net.anotheria.moskito.central.CentralConstants.PATH_TAG_INTERVAL;
import static net.anotheria.moskito.central.CentralConstants.PATH_TAG_PRODUCER;
import static net.anotheria.moskito.central.CentralConstants.PATH_TAG_STAT;
import static net.anotheria.moskito.central.CentralConstants.PATH_TAG_SUBSYSTEM;
import static net.anotheria.moskito.central.CentralConstants.PATH_TAG_TIME;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 24.03.13 22:44
 */
public class StorageUtils {

	public static final String convertPathPattern(String path, Snapshot target, String statName){
		path = convertPathPattern(path, target);
		return StringUtils.replace(path, PATH_TAG_STAT, statName);
	}

	public static final String convertPathPattern(String path, Snapshot target){
		path = StringUtils.replace(path, PATH_TAG_HOST, target.getMetaData().getHostName());
		path = StringUtils.replace(path, PATH_TAG_COMPONENT, target.getMetaData().getComponentName());
		path = StringUtils.replace(path, PATH_TAG_PRODUCER, target.getMetaData().getProducerId());
		path = StringUtils.replace(path, PATH_TAG_DATE,StringUtils.replace(NumberUtils.makeDigitalDateString(target.getMetaData().getCreationTimestamp()), '.', '_'));
		path = StringUtils.replace(path, PATH_TAG_TIME,StringUtils.replace(NumberUtils.makeTimeString(target.getMetaData().getCreationTimestamp()), ':', '_'));
		path = StringUtils.replace(path, PATH_TAG_CATEGORY, target.getMetaData().getCategory());
		path = StringUtils.replace(path, PATH_TAG_SUBSYSTEM, target.getMetaData().getSubsystem());
		path = StringUtils.replace(path, PATH_TAG_INTERVAL, target.getMetaData().getIntervalName());
		return path;
	}
}
