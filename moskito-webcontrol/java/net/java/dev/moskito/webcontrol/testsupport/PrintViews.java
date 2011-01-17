package net.java.dev.moskito.webcontrol.testsupport;

import java.util.List;

import net.java.dev.moskito.webcontrol.configuration.ConfigurationRepository;
import net.java.dev.moskito.webcontrol.configuration.StatsSource;
import net.java.dev.moskito.webcontrol.configuration.ViewConfiguration;
import net.java.dev.moskito.webcontrol.configuration.ViewField;
import net.java.dev.moskito.webcontrol.repository.*;
import net.java.dev.moskito.webcontrol.ui.MoskitoWebcontrolUIFilter;

public class PrintViews {

	public static void main(String a[]) throws Exception {
		ConfigurationRepository.INSTANCE.loadViewsConfiguration();
		RepositoryUpdater.getInstance().update();
		printViews();
	}

	public static void printViews() {
		List<String> views = ConfigurationRepository.INSTANCE.getViewNames();
		System.out.println("Views found: " + views);
		for (String v : views)
			printView(v);
	}

	private static void printView(String viewName) {

		for (String name : ConfigurationRepository.INSTANCE.getIntervalsNames()) {

			ViewConfiguration config = ConfigurationRepository.INSTANCE.getView(viewName);
			System.out.println("============= VIEW: " + config.getName() + ", interval: " + name + "===============");
			List<StatsSource> sources = ConfigurationRepository.INSTANCE.getSources();
			List<ViewField> fields = config.getFields();
			System.out.print("Source\t\t");
			for (ViewField f : fields) {
				System.out.print(f.getFieldName() + "\t");
			}
			System.out.println();

			for (StatsSource source : sources) {
				System.out.print(source.getName() + "\t\t");
				try {
					Snapshot s = Repository.INSTANCE.getSnapshot(ConfigurationRepository.INSTANCE.getContainerName(name), SnapshotSource
							.valueOf(source));
					for (ViewField field : fields) {
						Attribute att = s.getAttribute(field.getAttributeName());
						if (att == null) {
							System.out.print("n.a.");
						} else {
							System.out.print(att.getValueString()+"("+att.getCondition().name()+")");
						}
						System.out.print("\t");
					}
				} catch (Exception e) {
					e.printStackTrace();
					System.out.print("ERR: " + e);
				}
				System.out.println();
			}
			System.out.println("============= END VIEW: " + config.getName() + "===============");
		}
	}

}
