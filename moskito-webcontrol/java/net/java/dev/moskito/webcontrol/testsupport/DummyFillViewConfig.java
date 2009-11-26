package net.java.dev.moskito.webcontrol.testsupport;

import net.java.dev.moskito.webcontrol.configuration.ConfigurationRepository;
import net.java.dev.moskito.webcontrol.configuration.SourceConfiguration;
import net.java.dev.moskito.webcontrol.configuration.ViewConfiguration;
import net.java.dev.moskito.webcontrol.configuration.ViewField;

public class DummyFillViewConfig {
	
	public static void fillConfig(){
		fillViewConfig();
		fillSourceConfig();
	}
	
	public static void fillViewConfig(){
		ViewConfiguration memoryAll = new ViewConfiguration("MemoryAll");
		memoryAll.addField(new ViewField("Eden - FreeMB", "Eden.FreeMB"));
		memoryAll.addField(new ViewField("Eden - UsedMB", "Eden.UsedMB"));
		memoryAll.addField(new ViewField("Eden - MaxUsedMB", "Eden.MaxUsedMB"));
		memoryAll.addField(new ViewField("Eden - CommitedMB", "Eden.CommitedMB"));

		memoryAll.addField(new ViewField("Survivor - FreeMB", "Survivor.FreeMB"));
		memoryAll.addField(new ViewField("Survivor - UsedMB", "Survivor.UsedMB"));
		memoryAll.addField(new ViewField("Survivor - MaxUsedMB", "Survivor.MaxUsedMB"));
		memoryAll.addField(new ViewField("Survivor - CommitedMB", "Survivor.CommitedMB"));

		memoryAll.addField(new ViewField("OldGen - FreeMB", "OldGen.FreeMB"));
		memoryAll.addField(new ViewField("OldGen - UsedMB", "OldGen.UsedMB"));
		memoryAll.addField(new ViewField("OldGen - MaxUsedMB", "OldGen.MaxUsedMB"));
		memoryAll.addField(new ViewField("OldGen - CommitedMB", "OldGen.CommitedMB"));

		memoryAll.addField(new ViewField("PermGen - FreeMB", "PermGen.FreeMB"));
		memoryAll.addField(new ViewField("PermGen - UsedMB", "PermGen.UsedMB"));
		memoryAll.addField(new ViewField("PermGen - MaxUsedMB", "PermGen.MaxUsedMB"));
		memoryAll.addField(new ViewField("PermGen - CommitedMB", "PermGen.CommitedMB"));
		ConfigurationRepository.INSTANCE.addView(memoryAll);

		
		ViewConfiguration memoryFree = new ViewConfiguration("FreeMemory");
		memoryFree.addField(new ViewField("Eden", "Eden.FreeMB"));
		memoryFree.addField(new ViewField("Survivor", "Survivor.FreeMB"));
		memoryFree.addField(new ViewField("OldGen", "OldGen.FreeMB"));
		memoryFree.addField(new ViewField("PermGen", "PermGen.FreeMB"));
		ConfigurationRepository.INSTANCE.addView(memoryFree);

	
	}
	
	public static void fillSourceConfig(){
		ConfigurationRepository.INSTANCE.addSource(new SourceConfiguration("Server 1", "http://bla1.blub.de"));
		ConfigurationRepository.INSTANCE.addSource(new SourceConfiguration("Server 2", "http://bla2.blub.de"));
		ConfigurationRepository.INSTANCE.addSource(new SourceConfiguration("Server 3", "http://bla3.blub.de"));
		ConfigurationRepository.INSTANCE.addSource(new SourceConfiguration("Server 4", "http://bla4.blub.de"));
		
	}
}
