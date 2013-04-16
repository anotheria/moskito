package net.anotheria.moskito.central.storage.fs;

import net.anotheria.moskito.central.Snapshot;
import net.anotheria.moskito.central.storage.Storage;
import net.anotheria.moskito.central.storage.StorageUtils;
import net.anotheria.moskito.central.storage.serializer.CSVSerializer;
import org.apache.log4j.Logger;
import org.configureme.ConfigurationManager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Set;

/**
 * Storage that stores a producer/stat combination in csv format.
 *
 * @author lrosenberg
 * @since 24.03.13 22:41
 */
public class CSVFileStorage implements Storage{

	/**
	 * Configuration.
	 */
	private CSVFileStorageConfig config;

	/**
	 * Logger.
	 */
	private static Logger log = Logger.getLogger(CSVFileStorage.class);

	/**
	 * Serializer instance.
	 */
	private CSVSerializer serializer = new CSVSerializer();

	@Override
	public void configure(String configurationName) {
		config = new CSVFileStorageConfig();
		if (configurationName==null)
			return;
		try{
			ConfigurationManager.INSTANCE.configureAs(config, configurationName);
		}catch(IllegalArgumentException e){
			log.warn("Couldn't configure CSVFileStorage with " + configurationName + " , working with default values");
		}
		log.info("Configured CSVFIleStorage "+config+" from configuration file "+configurationName);
	}

	@Override
	public void processSnapshot(Snapshot target) {
		String producerId = target.getMetaData().getProducerId();
		String interval = target.getMetaData().getIntervalName();
		Set<String> stats = target.getKeySet();
		if (stats==null || stats.size()==0)
			return;
		for (String stat : stats){
			if (!config.include(producerId, stat, interval))
				continue;


			byte[] data = serializer.serialize(target, stat);
			if (data==null)
				continue;

			String path = StorageUtils.convertPathPattern(config.getPattern(), target, stat);

			FileOutputStream fOut = null;
			String dirName = path.substring(0, path.lastIndexOf('/'));
			File f = new File(dirName);
			f.mkdirs();

			File targetFile = new File(path);
			boolean writeHeader = !targetFile.exists();
			try{
				fOut = new FileOutputStream(targetFile, true);
				if (writeHeader){
					fOut.write(serializer.getHeader(target));
					fOut.write("\n".getBytes());
				}
				fOut.write(data);
				fOut.write("\n".getBytes());
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
}
