package net.anotheria.moskito.core.entrypoint;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

/**
 * Test for PastMeasurementChainNode.
 * The cases are defined in PastMeasurementChainNode.addToChainIfLongerDuration().
 *
 * @author lrosenberg
 * @since 18.09.20 00:18
 */
public class PastMeasurementChainNodeTest {
	@Test
	public void testEmptyNodeLength(){
		assertEquals(1, new PastMeasurementChainNode().getLength());
	}

	@Test public void testNodeLength(){
		PastMeasurementChainNode first = new PastMeasurementChainNode();
		first.add(new PastMeasurementChainNode());
		first.add(new PastMeasurementChainNode());
		assertEquals(3, first.getLength());
	}

	@Test public void testInsertCase1(){
		PastMeasurementChainNode root = null;
		PastMeasurementChainNode newNode = new PastMeasurementChainNode();

		PastMeasurementChainNode chain = PastMeasurementChainNode.addToChainIfLongerDuration(root, newNode);
		assertSame(newNode, chain);
	}

	@Test public void testInsertCase2(){

		ActiveMeasurement am = new ActiveMeasurement("foo");
		//we create 3 nodes with duration of 100, and insert one node with duration of 200. It should be added at the start.
		PastMeasurementChainNode root = null;

		//build chain.
		PastMeasurementChainNode first = new PastMeasurementChainNode(am);first.unitTestSetEndTime(System.currentTimeMillis()+100);
		PastMeasurementChainNode second = new PastMeasurementChainNode(am);second.unitTestSetEndTime(System.currentTimeMillis()+100);
		PastMeasurementChainNode third = new PastMeasurementChainNode(am);third.unitTestSetEndTime(System.currentTimeMillis()+100);
		first.add(second).add(third);
		root = first;

		PastMeasurementChainNode newNode = new PastMeasurementChainNode(am);
		newNode.unitTestSetEndTime(am.getStartTime()+400);

		PastMeasurementChainNode chain = PastMeasurementChainNode.addToChainIfLongerDuration(root, newNode);
		assertSame(newNode, chain);
		assertEquals(4, chain.getLength());
		assertEquals(400, chain.getNthElement(0).getDuration());
	}

	@Test public void testInsertCase3(){
		PastMeasurementChainNode root = null;
		PastMeasurementChainNode newNode = new PastMeasurementChainNode();

		PastMeasurementChainNode chain = PastMeasurementChainNode.addToChainIfLongerDuration(root, newNode);
		assertSame(newNode, chain);
	}

	@Test public void testInsertCase4(){

		//very similar to case 2, but the element goes at the end, not at the beginning.
		ActiveMeasurement am = new ActiveMeasurement("foo");
		//we create 3 nodes with duration of 100, and insert one node with duration of 50. It should be added at the end.
		PastMeasurementChainNode root = null;

		//build chain.
		PastMeasurementChainNode first = new PastMeasurementChainNode(am);first.unitTestSetEndTime(System.currentTimeMillis()+100);
		PastMeasurementChainNode second = new PastMeasurementChainNode(am);second.unitTestSetEndTime(System.currentTimeMillis()+100);
		PastMeasurementChainNode third = new PastMeasurementChainNode(am);third.unitTestSetEndTime(System.currentTimeMillis()+100);
		first.add(second).add(third);
		root = first;

		PastMeasurementChainNode newNode = new PastMeasurementChainNode(am);
		newNode.unitTestSetEndTime(am.getStartTime()+50);

		PastMeasurementChainNode chain = PastMeasurementChainNode.addToChainIfLongerDuration(root, newNode);
		assertSame(root, chain);

		assertEquals(4, chain.getLength());
		assertEquals(50, chain.getNthElement(3).getDuration());
	}

	@Test public void testInsertCase5(){

		//very similar to case 2, but the element goes at the end, not at the beginning.
		ActiveMeasurement am = new ActiveMeasurement("foo");
		//we create 20 nodes with duration of 100.
		//the chain should only contain 10 (net.anotheria.moskito.core.entrypoint.PastMeasurementChainNode.MAX_LENGTH)
		PastMeasurementChainNode root = null;

		//build chain.
		for (int i=0; i<30; i++){
			PastMeasurementChainNode node = new PastMeasurementChainNode(am);node.unitTestSetEndTime(System.currentTimeMillis()+100);
			root = PastMeasurementChainNode.addToChainIfLongerDuration(root, node);
		}


		assertEquals(10, root.getLength());
	}



}
