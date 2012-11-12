package net.anotheria.moskito.webcontrol.feed;

import net.anotheria.moskito.webcontrol.configuration.StatsSource;

import org.w3c.dom.Document;

public interface FeedGetter {

	Document retreive(StatsSource source);
	
}
