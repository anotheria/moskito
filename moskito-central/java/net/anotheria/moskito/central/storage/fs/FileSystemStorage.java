package net.anotheria.moskito.central.storage.fs;

import net.anotheria.moskito.central.Snapshot;
import net.anotheria.moskito.central.storage.SnapshotSerializer;
import net.anotheria.moskito.central.storage.Storage;
import net.anotheria.moskito.central.storage.serializer.GsonSerializer;
import net.anotheria.util.NumberUtils;
import net.anotheria.util.StringUtils;
import org.apache.log4j.Logger;
import org.configureme.ConfigurationManager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import static net.anotheria.moskito.central.CentralConstants.PATH_TAG_COMPONENT;
import static net.anotheria.moskito.central.CentralConstants.PATH_TAG_DATE;
import static net.anotheria.moskito.central.CentralConstants.PATH_TAG_HOST;
import static net.anotheria.moskito.central.CentralConstants.PATH_TAG_PRODUCER;
import static net.anotheria.moskito.central.CentralConstants.PATH_TAG_TIME;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 22.03.13 14:14
 */
public class FileSystemStorage implements Storage {

	private SnapshotSerializer serializer;

	private FileSystemStorageConfig config;

	private static Logger log = Logger.getLogger(FileSystemStorage.class);

	public FileSystemStorage(){
		//test
		serializer = new GsonSerializer();

	}

	@Override
	public void configure(String configurationName) {
		config = new FileSystemStorageConfig();
		if (configurationName==null)
			return;
		try{
			ConfigurationManager.INSTANCE.configureAs(config, configurationName);
		}catch(IllegalArgumentException e){
			log.warn("Couldn't configure FileSystemStorage with "+configurationName+" , working with default values");
		}
	}

	@Override
	public void processSnapshot(Snapshot target) {
		byte[] data = serializer.serialize(target);

		String path = config.getPattern();
		path = StringUtils.replace(path, PATH_TAG_HOST, target.getMetaData().getHostName());
		path = StringUtils.replace(path, PATH_TAG_COMPONENT, target.getMetaData().getComponentName());
		path = StringUtils.replace(path, PATH_TAG_PRODUCER, target.getMetaData().getProducerId());
		path = StringUtils.replace(path, PATH_TAG_DATE,StringUtils.replace(NumberUtils.makeDigitalDateString(target.getMetaData().getCreationTimestamp()), '.', '_'));
		path = StringUtils.replace(path, PATH_TAG_TIME,StringUtils.replace(NumberUtils.makeTimeString(target.getMetaData().getCreationTimestamp()), ':', '_'));

		FileOutputStream fOut = null;
		String dirName = path.substring(0, path.lastIndexOf('/'));
		File f = new File(dirName);
		f.mkdirs();
		try{
			fOut = new FileOutputStream(path);
			fOut.write(data);
			fOut.flush();
		}catch(IOException e){
			log.error("can't serialize snapshot "+target, e);
		}finally{
			if (fOut!=null){
				try{
					fOut.close();
				}catch(IOException ignored){}
			}
		}
	}
}
