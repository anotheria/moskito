package net.java.dev.moskito.webui.action.accumulators;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.java.dev.moskito.webui.action.accumulators.ShowAccumulatorsAction;
import net.java.dev.moskito.webui.bean.AccumulatedValuesBean;

import org.junit.Test;

public class TestAccumulatorNormalization {
	//this test ensures that values that 
	@Test public void testWithSimilarValues(){
		String[] names = new String[]{"smalline","mediumline", "bigline"};
		List<AccumulatedValuesBean> sourceData = 		createData(
				names,
				new String[]{"1","2","3","4"},
				new String[]{"10","20","30", "40"},
				new String[]{"100","200","300", "400"}
				);
		ShowAccumulatorsAction.normalize(sourceData, Arrays.asList(names), 100);
		for (AccumulatedValuesBean toCheck : sourceData){
			checkForFloatSimilarity(toCheck.getValue("smalline"), toCheck.getValue("mediumline"), toCheck.getValue("bigline"));
		}
		
	}
	
	private void checkForFloatSimilarity(String f1, String f2, String ... ff){
		float pattern = Float.parseFloat(f1);
		assertTrue("expected similarity in "+pattern+", "+f2, Math.abs(pattern-Float.parseFloat(f2))<0.1);
		if (ff!=null){
			for (String s : ff){
				assertTrue("expected similarity in "+pattern+", "+Float.parseFloat(s), Math.abs(pattern-Float.parseFloat(s))<0.1);
			}
		}
	}
	
	@Test public void testReverseOrder(){
		String[] names = new String[]{"line","reverseline"};
		List<AccumulatedValuesBean> sourceData = 		createData(
				names,
				new String[]{"1","2","3","4","5"},
				new String[]{"5","4","3","2","1"}
				);
		ShowAccumulatorsAction.normalize(sourceData, Arrays.asList(names), 100);
		for (int i=0; i<sourceData.size(); i++){
			checkForFloatSimilarity(sourceData.get(i).getValue("line"), sourceData.get(sourceData.size()-i-1).getValue("reverseline"));
		}
	}
	
	private static List<AccumulatedValuesBean> createData(String[] names, String [] ... valuesArray ){
		int vLength = valuesArray[0].length;
		for (String[] vv : valuesArray){
			if (vv.length!=vLength)
				throw new AssertionError("Arrays must have same length");
		}
		
		if (valuesArray.length!=names.length)
			throw new AssertionError("Expect similar count of names and values "+names.length+" vs "+valuesArray.length);
		
		List<AccumulatedValuesBean> valuesBeans = new ArrayList<AccumulatedValuesBean>();
		for (int i=0; i<vLength; i++){
			long timestamp = 0 + 1000L*60*i;
			AccumulatedValuesBean vBean = new AccumulatedValuesBean(timestamp);
			for (int n=0; n<names.length; n++){
				vBean.setValue(names[n], valuesArray[n][i]);
			}
			valuesBeans.add(vBean);
		}
		//DebugUtilities.printList(valuesBeans);
		return valuesBeans;
		
	}
	
	@Test public void testSingleValue(){
		//this test is based on real-life examples
		String[] names = new String[]{"threads"};
		List<AccumulatedValuesBean> sourceData = 		createData(
				names,
				new String[]{"25","25","25","26"}
				);
		ShowAccumulatorsAction.normalize(sourceData, Arrays.asList(names), 100);
		//DebugUtilities.printList(sourceData);
		assertEquals(Float.parseFloat(sourceData.get(0).getValue("threads")), 0.0, 0.0001);
		assertEquals(Float.parseFloat(sourceData.get(sourceData.size()-1).getValue("threads")), 100.0, 0.0001);
	}
	
}
