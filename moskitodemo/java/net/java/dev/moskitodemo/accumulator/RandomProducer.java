package net.java.dev.moskitodemo.accumulator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicInteger;

import net.java.dev.moskito.core.predefined.ServiceStats;
import net.java.dev.moskito.core.producers.IStats;
import net.java.dev.moskito.core.producers.IStatsProducer;
import net.java.dev.moskito.core.registry.ProducerRegistryFactory;

public class RandomProducer implements IStatsProducer{
	private int limitReq;
	private int limitTime;
	
	private static AtomicInteger instanceCounter = new AtomicInteger(0);
	
	private int instanceId = instanceCounter.incrementAndGet();
	
	private Random rnd;
	
	private ArrayList<IStats> stats;
	private ServiceStats serviceStats;
	
	private static final Timer timer = new Timer("RandomProducers", true);
	
	public RandomProducer(int aLimitReq, int aLimitTime){
		limitReq = aLimitReq;
		limitTime = aLimitTime;
		
		rnd = new Random(System.nanoTime());
		serviceStats = new ServiceStats("cumulated");
		stats = new ArrayList<IStats>();
		stats.add(serviceStats);
		
		ProducerRegistryFactory.getProducerRegistryInstance().registerProducer(this);
	
		timer.scheduleAtFixedRate(new TimerTask() {
			
			@Override
			public void run() {
				generateTraffic();
			}
		}, 0L, 1000L*60);
	}
	
	public void generateTraffic(){
		int numberOfReq = rnd.nextInt(limitReq);
		for (int i=0; i<numberOfReq; i++){
			int duration = rnd.nextInt(limitTime);
			serviceStats.addExecutionTime(1000L*1000*duration);
			serviceStats.addRequest();
			serviceStats.notifyRequestFinished();
		}
	}

	@Override
	public List<IStats> getStats() {
		return stats;
	}

	@Override
	public String getProducerId() {
		return "Random"+instanceId;
	}

	@Override
	public String getCategory() {
		return "service";
	}

	@Override
	public String getSubsystem() {
		return "accumulator-random";
	}
}
