package net.anotheria.moskito.core.snapshot;

import net.anotheria.moskito.core.producers.IStatsProducer;
import net.anotheria.moskito.core.registry.IProducerRegistryAPI;
import net.anotheria.moskito.core.registry.ProducerRegistryAPIFactory;
import net.anotheria.moskito.core.stats.IIntervalListener;
import net.anotheria.moskito.core.stats.Interval;
import net.anotheria.moskito.core.stats.IntervalRegistryListener;
import net.anotheria.moskito.core.stats.impl.IntervalRegistry;
import net.anotheria.util.queue.IQueueWorker;
import net.anotheria.util.queue.QueuedProcessor;
import net.anotheria.util.queue.UnrecoverableQueueOverflowException;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 20.03.13 14:32
 */
public class SnapshotRepository {

	private final List<SnapshotConsumer> consumers = new CopyOnWriteArrayList<SnapshotConsumer>();

	private IProducerRegistryAPI producerRegistryAPI;

	private QueuedProcessor<ProducerSnapshot> snapshotQueuedProcessor;

	private static Logger log = Logger.getLogger(SnapshotRepository.class);

	private boolean inTestMode = false;

	private SnapshotRepository(){
		snapshotQueuedProcessor = new QueuedProcessor<ProducerSnapshot>("SnapshotConsumers", new IQueueWorker<ProducerSnapshot>() {
			@Override
			public void doWork(ProducerSnapshot producerSnapshot) throws Exception {
				for (SnapshotConsumer consumer : consumers){
					try{
						consumer.consumeSnapshot(producerSnapshot);
					}catch(Exception e){
						log.warn("consumer "+consumer+" failed to process snapshot "+producerSnapshot);
					}

				}
			}
		}, 1000, 50,  log);
		snapshotQueuedProcessor.start();
		inTestMode = System.getProperty("JUNITTEST", "false").equals("true");
		producerRegistryAPI = new ProducerRegistryAPIFactory().createProducerRegistryAPI();
		List<Interval> intervals =  IntervalRegistry.getInstance().getIntervals();
		for (Interval i : intervals)
			i.addSecondaryIntervalListener(new SRIntervalListener());
		IntervalRegistry.getInstance().addIntervalRegistryListener(new SRIntervalRegistryListener());

	}

	public static final SnapshotRepository getInstance(){
		return SnapshotRepositoryInstanceHolder.instance;
	}

	private void intervalUpdated(Interval aCaller){
		if (consumers.size()==0)
			return;
		List<IStatsProducer> producers = producerRegistryAPI.getAllProducers();
		if (producers.size()==0)
			return;
		String intervalName = aCaller.getName();
		for (IStatsProducer<?> producer : producers){
			ProducerSnapshot snapshot = SnapshotCreator.createSnapshot(producer, intervalName);
			try{
				snapshotQueuedProcessor.addToQueue(snapshot);
			} catch (UnrecoverableQueueOverflowException e) {
				log.warn("Queue overflow - throw away snapshot: "+snapshot,e );
			}
		}
		if (inTestMode){
			//if we are in a unittest, we have to wait for producer to empty its queue, because
			//this producer doesn't support sync mode like queuedeventsender.
			//Yes, having Thread.sleep() in test is a bad thing to do, but what is the alternative?
			while(snapshotQueuedProcessor.getQueueSize()!=0){
				try{
					Thread.sleep(50);
				}catch(InterruptedException ignored){}
			}
		}

	}

	private static class SRIntervalListener implements IIntervalListener{
		@Override
		public void intervalUpdated(Interval aCaller) {
			SnapshotRepository.getInstance().intervalUpdated(aCaller);
		}
	}

	private static class SRIntervalRegistryListener implements IntervalRegistryListener{
		@Override
		public void intervalCreated(Interval aInterval) {
			aInterval.addSecondaryIntervalListener(new SRIntervalListener());
		}
	}

	private static class SnapshotRepositoryInstanceHolder{
		private static final SnapshotRepository instance = new SnapshotRepository();
	}

	public void addConsumer(SnapshotConsumer consumer){
		if (consumers.contains(consumer))
			consumers.remove(consumer);
		consumers.add(consumer);
	}

	public void removeConsumer(SnapshotConsumer consumer){
		consumers.remove(consumer);
	}
}
