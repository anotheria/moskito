package net.java.dev.moskito.webui.action;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.anotheria.maf.action.ActionForward;
import net.anotheria.maf.action.ActionMapping;
import net.anotheria.maf.bean.FormBean;
import net.anotheria.util.sorter.DummySortType;
import net.anotheria.util.sorter.SortType;
import net.anotheria.util.sorter.StaticQuickSorter;
import net.java.dev.moskito.core.accumulation.AccumulatedValue;
import net.java.dev.moskito.core.accumulation.Accumulator;
import net.java.dev.moskito.core.accumulation.AccumulatorRepository;
import net.java.dev.moskito.webui.bean.AccumulatedValueBean;
import net.java.dev.moskito.webui.bean.AccumulatedValuesBean;
import net.java.dev.moskito.webui.bean.AccumulatorInfoBean;
import net.java.dev.moskito.webui.bean.NaviItem;

public class ShowAccumulatorsAction extends BaseMoskitoUIAction{

	//save objects
	private static final SortType SORT_TYPE = new DummySortType();
	
	@Override
	public ActionForward execute(ActionMapping mapping, FormBean formBean,
			HttpServletRequest req, HttpServletResponse res) throws Exception {
		
		List<Accumulator> accumulators = AccumulatorRepository.getInstance().getAccumulators();
		List<AccumulatorInfoBean> beans = new ArrayList<AccumulatorInfoBean>();
		
		
		String normalize_p = req.getParameter("normalize");
		boolean normalize = normalize_p!=null && normalize_p.equals("true");
		if (normalize){
			req.setAttribute("normalize_set", Boolean.TRUE);
		}
		int normalizeBase = 100;
		try{
			normalizeBase = Integer.parseInt(req.getParameter("normalizeBase"));
		}catch(Exception ignored){}
		req.setAttribute("normalizeBase", normalizeBase);
		int maxValues = 200;
		try{
			maxValues = Integer.parseInt(req.getParameter("maxValues"));
		}catch(Exception ignored){;/*empty*/}
		req.setAttribute("maxValues", maxValues);
		
		for (Accumulator a : accumulators){
			AccumulatorInfoBean bean = new AccumulatorInfoBean();
			
			bean.setName(a.getName());
			bean.setPath(a.getDefinition().describe());
			bean.setId(a.getId());
			List<AccumulatedValue> values = a.getValues();
			if (values!=null && values.size()>0){
				bean.setNumberOfValues(values.size());
				bean.setLastValueTimestamp(values.get(values.size()-1).getISO8601Timestamp());
			}else{
				bean.setNumberOfValues(0);
				bean.setLastValueTimestamp("none");
			}
			
			beans.add(bean);
		}

		beans = StaticQuickSorter.sort(beans, SORT_TYPE);
		req.setAttribute("accumulators", beans);
		
		List<String> ids = new ArrayList<String>();
		
		Enumeration<?> names = req.getParameterNames();
		while(names.hasMoreElements()){
			String name = (String)names.nextElement();
			if (name.startsWith("id_")){
				ids.add(name.substring("id_".length()));
				//set flag to activate checkbox
				req.setAttribute(name+"_set", Boolean.TRUE);
			}
		}
		
//		System.out.println("ids to show: "+ids);
		if (ids.size()>0){
		List<AccumulatedValueBean> dataBeans = new ArrayList<AccumulatedValueBean>();
			
			//prepare values
			HashMap<Long, AccumulatedValuesBean> values = new HashMap<Long, AccumulatedValuesBean>();
			List<String> accNames = new ArrayList<String>();
			
			for (String id : ids){
				Accumulator acc = AccumulatorRepository.getInstance().getAccumulatorById(id);
				accNames.add(acc.getName());
				List<AccumulatedValue> accValues = acc.getValues();
				for (AccumulatedValue v : accValues){
					long timestamp = v.getTimestamp()/1000*1000;
					AccumulatedValuesBean bean = values.get(timestamp);
					if (bean==null){
						bean = new AccumulatedValuesBean(timestamp);
						values.put(timestamp, bean);
					}
					bean.setValue(acc.getName(), v.getValue());
				}
			}
			List<AccumulatedValuesBean> valuesList = StaticQuickSorter.sort(values.values(), SORT_TYPE);
			
			//now check if the data is complete
			//Stores last known values to allow filling in of missing values (combining 1m and 5m values)
			HashMap<String, String> lastValue = new HashMap<String, String>();

			//filling last (or first) values.
			for (String accName : accNames){
				//first put 'some' initial value.
				lastValue.put(accName, "0");
				//now search for first non-null value
				for(AccumulatedValuesBean accValueBean : valuesList){
					String aValue = accValueBean.getValue(accName);
					if (aValue!=null){
						lastValue.put(accName, aValue);
						break;
					}
				}
			}
			
			for(AccumulatedValuesBean accValueBean : valuesList){
				for (String accName : accNames){
					String value = accValueBean.getValue(accName);
					if (value==null){
						accValueBean.setValue(accName, lastValue.get(accName));
					}else{
						lastValue.put(accName, value);
					}
				}
			}
//			System.out.println("!2!");
//			for(AccumulatedValuesBean avbTemp : valuesList){
//				System.out.println("  "+avbTemp);
//			}

			if (normalize){
				normalize(valuesList, accNames, normalizeBase);
			}
			
			//now create final data
			for(AccumulatedValuesBean avb : valuesList){
				AccumulatedValueBean bean = new AccumulatedValueBean(avb.getTime());
				for (String accName : accNames){
					bean.addValue(avb.getValue(accName));
				}
				dataBeans.add(bean);
			}
			
			//now finally cut the data
//			if (dataBeans.size()>maxValues)
				//dataBeans.subList(fromIndex, toIndex)
			
			if (dataBeans.size()>maxValues)
				dataBeans = dataBeans.subList(dataBeans.size()-maxValues, dataBeans.size());

			req.setAttribute("data", dataBeans);
			req.setAttribute("accNames", accNames);
			
			
			
			
			
			
//			String id = req.getParameter("id");
//			if (id!=null && id.length()>0){
//				Accumulator acc = AccumulatorRepository.getInstance().getAccumulatorById(id);
//				AccumulatorDataBean dataBean = new AccumulatorDataBean();
//				dataBean.setDescription(acc.getDefinition().describe());
//				dataBean.setShortDescription(acc.getName());
//				List<AccumulatedValue> values = acc.getValues();
//				for (AccumulatedValue value : values){
//					dataBean.addValue(value.getValue(), NumberUtils.makeTimeString(value.getTimestamp()));
//				}
//				req.setAttribute("dataBean", dataBean);
//			}
		}
		
		return mapping.findForward("success");
	}
	
	/*test visibility */ static void normalize(List<AccumulatedValuesBean> values, List<String> names, int limit){
		for (String name : names){
			//System.out.println("normalizing "+name);
			ArrayList<Float> valueCopy = new ArrayList<Float>(values.size());
			//step1 transform everything to float
			float min = Float.MAX_VALUE, max = Float.MIN_VALUE;
			for (AccumulatedValuesBean v : values){
				float val = Float.parseFloat(v.getValue(name));
				if (val>max)
					max = val;
				if (val<min)
					min = val;
				valueCopy.add(val);
			}
			//System.out.println("1: "+valueCopy);
			float range = max - min;
			float multiplier = limit / range;
			//System.out.println("range "+range+", multiplier "+multiplier);
			
			//step2 recalculate
			for (int i=0; i<values.size(); i++){
				float newValue = (valueCopy.get(i)-min)*multiplier;
				//System.out.println(values.get(i).getValue(name)+" --> "+newValue);
				values.get(i).setValue(name, ""+newValue);
			}
		}
	}
	

	@Override
	protected String getLinkToCurrentPage(HttpServletRequest req) {
		return "mskAccumulators?ts="+System.currentTimeMillis();
	}

	@Override
	protected NaviItem getCurrentNaviItem() {
		return NaviItem.ACCUMULATORS;
	}
}
