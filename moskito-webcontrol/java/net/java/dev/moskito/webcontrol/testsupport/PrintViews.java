package net.java.dev.moskito.webcontrol.testsupport;

import java.util.List;

import net.java.dev.moskito.webcontrol.configuration.ConfigurationRepository;
import net.java.dev.moskito.webcontrol.configuration.SourceConfiguration;
import net.java.dev.moskito.webcontrol.configuration.ViewConfiguration;
import net.java.dev.moskito.webcontrol.configuration.ViewField;
import net.java.dev.moskito.webcontrol.repository.Attribute;
import net.java.dev.moskito.webcontrol.repository.Repository;
import net.java.dev.moskito.webcontrol.repository.Snapshot;
import net.java.dev.moskito.webcontrol.repository.SnapshotSource;

public class PrintViews {
	public static void main(String a[]){
		DummyFillRepository.fillRepository();
		DummyFillViewConfig.fillConfig();
		
		printViews();
	}
	
	public static void printViews(){
		List<String> views = ConfigurationRepository.INSTANCE.getViewNames();
		System.out.println("Views found: "+views);
		for (String v : views)
			printView(v);
	}
	
	private static void printView(String viewName){
		ViewConfiguration config = ConfigurationRepository.INSTANCE.getView(viewName);
		System.out.println("============= VIEW: "+config.getName()+"===============");
		List<SourceConfiguration> sources = ConfigurationRepository.INSTANCE.getSources();
		List<ViewField> fields = config.getFields();
		System.out.print("Source\t\t");
		for (ViewField f : fields){
			System.out.print(f.getFieldName()+"\t");
		}
		System.out.println();

		for (SourceConfiguration source : sources){
			System.out.print(source.getName()+"\t");
			try{
				Snapshot s = Repository.INSTANCE.getSnapshot(config.getContainerName(), SnapshotSource.valueOf(source));
				for (ViewField field : fields){
					Attribute att = s.getAttribute(field.getAttributeName());
					if (att==null)
						System.out.print("n.a.");
					else
						System.out.print(att.getValueString());
					System.out.print("\t");
				}
				
			}catch(Exception e){
				System.out.print("ERR: "+e);
			}
			System.out.println();
		}
		
		
		System.out.println("============= END VIEW: "+config.getName()+"===============");
	}
}
