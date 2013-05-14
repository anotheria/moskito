package net.anotheria.moskito.web.filters;

import net.anotheria.moskito.core.dynamic.OnDemandStatsProducer;
import net.anotheria.moskito.core.dynamic.OnDemandStatsProducerException;
import net.anotheria.moskito.core.predefined.FilterStats;
import net.anotheria.moskito.web.MoskitoFilter;
import net.anotheria.util.queue.IQueueWorker;
import net.anotheria.util.queue.QueuedProcessor;
import net.anotheria.util.queue.UnrecoverableQueueOverflowException;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;
import java.net.InetAddress;

/**
 * This is a safe alternative to the SourceTldFilter. It processes incoming ips asynchronously therefore
 * not slowing down the processing of the request.
 *
 * @author lrosenberg
 * @since 25.04.13 18:48
 */
public class AsyncSourceTldFilter extends MoskitoFilter{
	/**
	 * Limit for the ip length. This limit is simple precaution in case someone is sending constructed source ip header
	 * or X_FORWARDED_FOR header.
	 */
	public static final int TLD_LENGTH_LIMIT = 20;

	/**
	 * Processor for asynchronous processing of incoming ip adresses.
	 */
	private QueuedProcessor<TemporarlyStatsStorage> asyncProcessor;

	@Override
	public void init(FilterConfig config) throws ServletException {
		super.init(config);
		asyncProcessor = new QueuedProcessor<TemporarlyStatsStorage>("async-tld-resolver", new QueueWorker(this), 10000, log);
		asyncProcessor.start();
	}

	private void writeStatsToProducer(String caseName, TemporarlyStatsStorage tss){
		OnDemandStatsProducer<FilterStats> onDemandProducer = getProducer();

		FilterStats defaultStats = onDemandProducer.getDefaultStats();
		FilterStats caseStats = null;
		try{
			if (caseName!=null)
				caseStats = onDemandProducer.getStats(caseName);
		}catch(OnDemandStatsProducerException e){
			log.info("Couldn't get stats for case : "+caseName+", probably limit reached");
			caseStats = getOtherStats();
		}

		defaultStats.addRequest();
		if (caseStats!=null)
			caseStats.addRequest();

		defaultStats.addExecutionTime(tss.exTime);
		if (caseStats!=null)
			caseStats.addExecutionTime(tss.exTime);

		if (tss.error){
			defaultStats.notifyError();
			if (caseStats!=null)
				caseStats.notifyError();
		}

		if (tss.servletException){
			defaultStats.notifyServletException();
			if (caseStats!=null)
				caseStats.notifyServletException();
		}

		if (tss.runtimeException){
			defaultStats.notifyRuntimeException();
			if (caseStats!=null)
				caseStats.notifyRuntimeException();
		}

		if (tss.ioException){
			defaultStats.notifyIOException();
			if (caseStats!=null)
				caseStats.notifyIOException();
		}

		if (tss.finished){
			defaultStats.notifyRequestFinished();
			if (caseStats!=null)
				caseStats.notifyRequestFinished();

		}
	}

	/**
	 * Temporary object to save request data between the request and asynchronous processing.
	 */
	private static class TemporarlyStatsStorage{
		/**
		 * Clients ip.
		 */
		String ip;
		/**
		 * Execution time.
		 */
		long exTime;
		/**
		 * Was the request finished? Finished means finally{} block has been reached without exceptions.
		 */
		boolean finished;
		/**
		 * Did a servlet exception occurred?
		 */
		boolean servletException;
		/**
		 * Did an io exception occurred?
		 */
		boolean ioException;
		/**
		 * Did a runtime exception occurred?
		 */
		boolean runtimeException;
		/**
		 * Did an Error occurred?
		 */
		boolean error;
	}

	/**
	 * Internal class the processes the queue elements.
	 */
	private static class QueueWorker implements IQueueWorker<TemporarlyStatsStorage> {

		/**
		 * Link to the filter.
		 */
		private AsyncSourceTldFilter parent;

		QueueWorker(AsyncSourceTldFilter parent){
			this.parent = parent;
		}

		@Override
		public void doWork(TemporarlyStatsStorage workingElement) throws Exception {
			//resolve ip
			String ip = workingElement.ip;
			String hostName = InetAddress.getByName(ip).getHostName();
			int indexOfTld = hostName.lastIndexOf('.');
			if (indexOfTld!=-1){
				hostName = hostName.substring(indexOfTld+1);
			}
			if (hostName.length()>SourceTldFilter.TLD_LENGTH_LIMIT){
				hostName = hostName.substring(0, SourceTldFilter.TLD_LENGTH_LIMIT);
			}

			if (Character.isDigit(hostName.charAt(hostName.length()-1))){
				hostName = "-unresolved-";
			}



			//add to stats
			parent.writeStatsToProducer(hostName, workingElement);


		}
	}

	@Override public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {

		OnDemandStatsProducer<FilterStats> onDemandProducer = getProducer();
		if (onDemandProducer==null){
			log.error("Access to filter before it's inited!");
			chain.doFilter(req, res);
			return;
		}

		TemporarlyStatsStorage tss = new TemporarlyStatsStorage();

		try{
			long startTime = System.nanoTime();
			chain.doFilter(req, res);
			tss.exTime = System.nanoTime() - startTime;
		}catch(ServletException e){
			tss.servletException = true;
			throw e;
		}catch(IOException e){
			tss.ioException = true;
			throw e;
		}catch(RuntimeException e){
			tss.runtimeException = true;
			throw e;
		}catch(Error e){
			tss.error = true;
			throw e;
		}finally{
			tss.finished = true;
			tss.ip = req.getRemoteAddr();
			try {
				asyncProcessor.addToQueueDontWait(tss);
			} catch (UnrecoverableQueueOverflowException e) {
				e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
			}
		}
	}


		/**
		 * Overwrite this to provide a name allocation mechanism to make request -> name mapping.
		 * @param req ServletRequest.
		 * @param res ServletResponse.
		 * @return name of the use case for stat storage.
		 */
	protected String extractCaseName(ServletRequest req, ServletResponse res ){
		throw new AssertionError("Shouldn't be ever called!");
	}


}
