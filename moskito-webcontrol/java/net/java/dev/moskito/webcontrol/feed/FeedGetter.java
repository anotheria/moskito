package net.java.dev.moskito.webcontrol.feed;

import net.java.dev.moskito.webcontrol.configuration.StatsSource;

import org.w3c.dom.Document;

public interface FeedGetter {

	Document retreive(StatsSource source);
	
}
