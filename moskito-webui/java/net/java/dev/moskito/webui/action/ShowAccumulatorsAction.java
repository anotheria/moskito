package net.java.dev.moskito.webui.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.anotheria.maf.action.ActionForward;
import net.anotheria.maf.action.ActionMapping;
import net.anotheria.maf.bean.FormBean;
import net.anotheria.util.NumberUtils;
import net.java.dev.moskito.core.accumulation.AccumulatedValue;
import net.java.dev.moskito.core.accumulation.Accumulator;
import net.java.dev.moskito.core.accumulation.AccumulatorRepository;
import net.java.dev.moskito.webui.bean.AccumulatorDataBean;
import net.java.dev.moskito.webui.bean.AccumulatorInfoBean;
import net.java.dev.moskito.webui.bean.NaviItem;

public class ShowAccumulatorsAction extends BaseMoskitoUIAction{

	@Override
	public ActionForward execute(ActionMapping mapping, FormBean formBean,
			HttpServletRequest req, HttpServletResponse res) throws Exception {
		
		List<Accumulator> accumulators = AccumulatorRepository.getInstance().getAccumulators();
		ArrayList<AccumulatorInfoBean> beans = new ArrayList<AccumulatorInfoBean>();
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

		req.setAttribute("accumulators", beans);
		
		
		String id = req.getParameter("id");
		if (id!=null && id.length()>0){
			Accumulator acc = AccumulatorRepository.getInstance().getAccumulatorById(id);
			AccumulatorDataBean dataBean = new AccumulatorDataBean();
			dataBean.setDescription(acc.getDefinition().describe());
			List<AccumulatedValue> values = acc.getValues();
			for (AccumulatedValue value : values){
				dataBean.addValue(value.getValue(), NumberUtils.makeTimeString(value.getTimestamp()));
			}
			req.setAttribute("dataBean", dataBean);
		}
		
		
		return mapping.findForward("success");
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
