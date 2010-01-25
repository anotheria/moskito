package net.java.dev.moskito.webcontrol.feed;

import net.java.dev.moskito.webcontrol.configuration.SourceConfiguration;

import org.w3c.dom.Document;

public interface FeedGetter {

	Document retreive(SourceConfiguration source);
	
}
