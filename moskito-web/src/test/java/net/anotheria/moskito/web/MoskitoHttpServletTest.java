package net.anotheria.moskito.web;

import net.anotheria.moskito.core.predefined.ServletStats;
import net.anotheria.moskito.core.producers.IStats;
import net.anotheria.moskito.core.producers.IStatsProducer;
import net.anotheria.moskito.core.registry.IProducerRegistryAPI;
import net.anotheria.moskito.core.registry.ProducerRegistryAPIFactory;
import net.anotheria.moskito.core.registry.ProducerRegistryFactory;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

@RunWith(MockitoJUnitRunner.class)
public class MoskitoHttpServletTest {
	
	private static final long GETS = 5, POSTS = 10, HEADS = 2, PUTS = 6, LM = 4, TRACES = 13, OPTIONS = 2, DELETES = 20;

	@BeforeClass public static void setProperty(){
		System.setProperty("JUNITTEST", "true");
	}
	
	@Before public void reset(){
		ProducerRegistryFactory.reset();
	}
	
	@Test public void testMethods() throws Exception{
		TestServlet servlet = new TestServlet();
		HttpServletRequest req = mock(HttpServletRequest.class);
		HttpServletResponse res = mock(HttpServletResponse.class);
		ServletConfig config = mock(ServletConfig.class);
		
	
		servlet.init(config);
		int i = 0;
		
		Map<String, Long> controlMap = new HashMap<String, Long>();
		controlMap.put("get", GETS);
		controlMap.put("post", POSTS);
		controlMap.put("head", HEADS);
		controlMap.put("put", PUTS);
		controlMap.put("trace", TRACES);
		controlMap.put("options", OPTIONS);
		controlMap.put("delete", DELETES);
		controlMap.put("lastModified", LM);
		
		for (i=0; i<GETS; i++)
			servlet.doGet(req, res);
		for (i=0; i<POSTS; i++)
			servlet.doPost(req, res);
		for (i=0; i<HEADS; i++)
			servlet.doHead(req, res);
		for (i=0; i<PUTS; i++)
			servlet.doPut(req, res);
		for (i=0; i<DELETES; i++)
			servlet.doDelete(req, res);
		for (i=0; i<TRACES; i++)
			servlet.doTrace(req, res);
		for (i=0; i<OPTIONS; i++)
			servlet.doOptions(req, res);
		for (i=0; i<LM; i++)
			servlet.getLastModified(req);

		IProducerRegistryAPI api = new ProducerRegistryAPIFactory().createProducerRegistryAPIForUnitTest();
		IStatsProducer producer = api.getProducer("TestServlet");
		List<IStats> stats = producer.getStats();
		for (IStats s : stats){
			//System.out.println(s);
			assertEquals("Mismatch in "+s.getName(), ((ServletStats)s).getTotalRequests(), controlMap.get(s.getName()).longValue());
		}
	}

	@Test public void testErrors() throws Exception {
		TestServlet servlet = new TestServlet(true);
		HttpServletRequest req = mock(HttpServletRequest.class);
		HttpServletResponse res = mock(HttpServletResponse.class);
		ServletConfig config = mock(ServletConfig.class);
	
		servlet.init(config);
		int i = 0;
		
		Map<String, Long> controlMap = new HashMap<String, Long>();
		controlMap.put("get", GETS);
		controlMap.put("post", POSTS);
		controlMap.put("head", HEADS);
		controlMap.put("put", PUTS);
		controlMap.put("trace", TRACES);
		controlMap.put("options", OPTIONS);
		controlMap.put("delete", DELETES);
		controlMap.put("lastModified", 0L);
		

		int errors = 0;
		
		for (i=0; i<GETS; i++){
			try{
				servlet.doGet(req, res);
			}catch(ServletException ignored){
				errors++;
			}
		}
		for (i=0; i<OPTIONS; i++){
			try{
				servlet.doOptions(req, res);
			}catch(ServletException ignored){
				errors++;
			}
		}
		for (i=0; i<TRACES; i++){
			try{
				servlet.doTrace(req, res);
			}catch(ServletException ignored){
				errors++;
			}
		}
		for (i=0; i<DELETES; i++){
			try{
				servlet.doDelete(req, res);
			}catch(ServletException ignored){
				errors++;
			}
		}
		for (i=0; i<PUTS; i++){
			try{
				servlet.doPut(req, res);
			}catch(ServletException ignored){
				errors++;
			}
		}
		for (i=0; i<HEADS; i++){
			try{
				servlet.doHead(req, res);
			}catch(ServletException ignored){
				errors++;
			}
		}
		for (i=0; i<POSTS; i++){
			try{
				servlet.doPost(req, res);
			}catch(ServletException ignored){
				errors++;
			}
		}
		
		assertEquals(HEADS+ PUTS+GETS+OPTIONS+TRACES+DELETES+POSTS, errors);

		IProducerRegistryAPI api = new ProducerRegistryAPIFactory().createProducerRegistryAPIForUnitTest();
		IStatsProducer producer = api.getProducer("TestServlet");
		List<IStats> stats = producer.getStats();
		for (IStats s : stats){
			//System.out.println(s);
			assertEquals("Mismatch in "+s.getName(), ((ServletStats)s).getServletExceptions(), controlMap.get(s.getName()).longValue());
		}
	}
}
