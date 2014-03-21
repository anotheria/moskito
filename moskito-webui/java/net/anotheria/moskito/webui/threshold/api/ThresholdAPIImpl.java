package net.anotheria.moskito.webui.threshold.api;

import net.anotheria.anoplass.api.APIException;
import net.anotheria.moskito.core.stats.TimeUnit;
import net.anotheria.moskito.core.threshold.Threshold;
import net.anotheria.moskito.core.threshold.ThresholdConditionGuard;
import net.anotheria.moskito.core.threshold.ThresholdDefinition;
import net.anotheria.moskito.core.threshold.ThresholdRepository;
import net.anotheria.moskito.core.threshold.ThresholdStatus;
import net.anotheria.moskito.core.threshold.alerts.AlertHistory;
import net.anotheria.moskito.core.threshold.alerts.ThresholdAlert;
import net.anotheria.moskito.core.threshold.guard.DoubleBarrierPassGuard;
import net.anotheria.moskito.core.threshold.guard.GuardedDirection;
import net.anotheria.moskito.core.threshold.guard.LongBarrierPassGuard;
import net.anotheria.moskito.webui.shared.api.AbstractMoskitoAPIImpl;
import net.anotheria.util.NumberUtils;
import net.anotheria.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 11.02.13 18:45
 */
public class ThresholdAPIImpl extends AbstractMoskitoAPIImpl implements ThresholdAPI{
	@Override
	public List<ThresholdAlertAO> getAlerts() {
		ArrayList<ThresholdAlertAO> aBeans = new ArrayList<ThresholdAlertAO>();
		for (ThresholdAlert alert : AlertHistory.INSTANCE.getAlerts()){
			ThresholdAlertAO alertBean = new ThresholdAlertAO();
			alertBean.setId(alert.getThreshold().getId());
			alertBean.setName(alert.getThreshold().getName());
			alertBean.setOldColorCode(alert.getOldStatus().toString().toLowerCase());
			alertBean.setOldStatus(alert.getOldStatus().toString());
			alertBean.setOldValue(alert.getOldValue());
			alertBean.setNewColorCode(alert.getNewStatus().toString().toLowerCase());
			alertBean.setNewStatus(alert.getNewStatus().toString());
			alertBean.setNewValue(alert.getNewValue());
			alertBean.setTimestamp(NumberUtils.makeISO8601TimestampString(alert.getTimestamp()));
			aBeans.add(alertBean);
		}
		return aBeans;
	}

	protected boolean hasDots(String ... params){
		if (params==null)
			return false;
		for (String p : params){
			if (p!=null && p.indexOf('.')>0)
				return true;
		}
		return false;
	}

	protected GuardedDirection string2direction(String param){
		if (param==null)
			throw new IllegalArgumentException("Empty direction parameter!");
		if (param.equalsIgnoreCase("below"))
			return GuardedDirection.DOWN;
		if (param.equalsIgnoreCase("above"))
			return GuardedDirection.UP;
		throw new IllegalArgumentException("Unknown parameter value for direction "+param+", expected below or above.");
	}

	@Override
	public void updateThreshold(String thresholdId, ThresholdPO po) throws APIException{
		Threshold oldThreshold = ThresholdRepository.getInstance().getById(thresholdId);
		ThresholdDefinition td = oldThreshold.getDefinition();
		td.setName(po.getName());

		//remove old
		ThresholdRepository.getInstance().removeById(thresholdId);
		GuardedDirection greenDir = string2direction(po.getGreenDir());
		GuardedDirection yellowDir = string2direction(po.getYellowDir());
		GuardedDirection orangeDir = string2direction(po.getOrangeDir());
		GuardedDirection redDir = string2direction(po.getRedDir());
		GuardedDirection purpleDir = string2direction(po.getPurpleDir());

		String greenValue  = po.getGreenValue();
		String yellowValue = po.getYellowValue();
		String orangeValue = po.getOrangeValue();
		String redValue    = po.getRedValue();
		String purpleValue = po.getPurpleValue();

		validateValues(greenValue, yellowValue, orangeValue, redValue, purpleValue);

		//determine if we have to use double
		boolean hasDots = hasDots(greenValue, yellowValue, orangeValue, redValue, purpleValue);

		Threshold newThreshold = ThresholdRepository.getInstance().createThreshold(td);
		newThreshold.addGuard(hasDots ?
				new DoubleBarrierPassGuard(ThresholdStatus.GREEN, Double.parseDouble(greenValue), greenDir):
				new LongBarrierPassGuard(ThresholdStatus.GREEN, Long.parseLong(greenValue), greenDir)
		);
		newThreshold.addGuard(hasDots ?
				new DoubleBarrierPassGuard(ThresholdStatus.YELLOW, Double.parseDouble(yellowValue), yellowDir):
				new LongBarrierPassGuard(ThresholdStatus.YELLOW, Long.parseLong(yellowValue), yellowDir)
		);
		newThreshold.addGuard(hasDots ?
				new DoubleBarrierPassGuard(ThresholdStatus.ORANGE, Double.parseDouble(orangeValue), orangeDir):
				new LongBarrierPassGuard(ThresholdStatus.ORANGE, Long.parseLong(orangeValue), orangeDir)
		);
		newThreshold.addGuard(hasDots ?
				new DoubleBarrierPassGuard(ThresholdStatus.RED, Double.parseDouble(redValue), redDir):
				new LongBarrierPassGuard(ThresholdStatus.RED, Long.parseLong(redValue), redDir)
		);
		newThreshold.addGuard(hasDots ?
				new DoubleBarrierPassGuard(ThresholdStatus.PURPLE, Double.parseDouble(purpleValue), purpleDir):
				new LongBarrierPassGuard(ThresholdStatus.PURPLE, Long.parseLong(purpleValue), purpleDir)
		);

	}


	private void validateValues(String greenValue, String yellowValue, String orangeValue, String redValue,String purpleValue) throws APIException{
        //fix for https://jira.opensource.anotheria.net/browse/MSK-82
        if (StringUtils.isEmpty(greenValue))
            throw new APIException("Cannot create new threshold. Please specify a boundary value for Green status.");
        if (StringUtils.isEmpty(yellowValue))
            throw new APIException("Cannot create new threshold. Please specify a boundary value for Yellow status.");
        if (StringUtils.isEmpty(orangeValue))
            throw new APIException("Cannot create new threshold. Please specify a boundary value for Orange status.");
        if (StringUtils.isEmpty(redValue))
            throw new APIException("Cannot create new threshold. Please specify a boundary value for Red status.");
        if (StringUtils.isEmpty(purpleValue))
            throw new APIException("Cannot create new threshold. Please specify a boundary value for Purple status.");
        
    }

	@Override
	public Threshold createThreshold(ThresholdPO po)  throws APIException{
		//now parse guards
		GuardedDirection greenDir = string2direction(po.getGreenDir());
		GuardedDirection yellowDir = string2direction(po.getYellowDir());
		GuardedDirection orangeDir = string2direction(po.getOrangeDir());
		GuardedDirection redDir = string2direction(po.getRedDir());
		GuardedDirection purpleDir = string2direction(po.getPurpleDir());

		String greenValue  = po.getGreenValue();
		String yellowValue = po.getYellowValue();
		String orangeValue = po.getOrangeValue();
		String redValue    = po.getRedValue();
		String purpleValue = po.getPurpleValue();

		validateValues(greenValue, yellowValue, orangeValue, redValue, purpleValue);

		//determine if we have to use double
		boolean hasDots = hasDots(greenValue, yellowValue, orangeValue, redValue, purpleValue);

		ThresholdDefinition td = new ThresholdDefinition();
		td.setProducerName(po.getProducerId());
		td.setStatName(po.getStatName());
		td.setValueName(po.getValueName());
		td.setIntervalName(po.getInterval());
		td.setTimeUnit(TimeUnit.fromString(po.getUnit()));
		td.setName(po.getName());

		Threshold newThreshold = ThresholdRepository.getInstance().createThreshold(td);
		newThreshold.addGuard(hasDots ?
				new DoubleBarrierPassGuard(ThresholdStatus.GREEN, Double.parseDouble(greenValue), greenDir):
				new LongBarrierPassGuard(ThresholdStatus.GREEN, Long.parseLong(greenValue), greenDir)
		);
		newThreshold.addGuard(hasDots ?
				new DoubleBarrierPassGuard(ThresholdStatus.YELLOW, Double.parseDouble(yellowValue), yellowDir):
				new LongBarrierPassGuard(ThresholdStatus.YELLOW, Long.parseLong(yellowValue), yellowDir)
		);
		newThreshold.addGuard(hasDots ?
				new DoubleBarrierPassGuard(ThresholdStatus.ORANGE, Double.parseDouble(orangeValue), orangeDir):
				new LongBarrierPassGuard(ThresholdStatus.ORANGE, Long.parseLong(orangeValue), orangeDir)
		);
		newThreshold.addGuard(hasDots ?
				new DoubleBarrierPassGuard(ThresholdStatus.RED, Double.parseDouble(redValue), redDir):
				new LongBarrierPassGuard(ThresholdStatus.RED, Long.parseLong(redValue), redDir)
		);
		newThreshold.addGuard(hasDots ?
				new DoubleBarrierPassGuard(ThresholdStatus.PURPLE, Double.parseDouble(purpleValue), purpleDir):
				new LongBarrierPassGuard(ThresholdStatus.PURPLE, Long.parseLong(purpleValue), purpleDir)
		);
		return newThreshold;
	}

	@Override
	public void removeThreshold(String id) {
		ThresholdRepository.getInstance().removeById(id);
	}

	@Override
	public List<ThresholdStatusAO> getThresholdStatuses() throws APIException {
		List<Threshold> thresholds = ThresholdRepository.getInstance().getThresholds();
		ArrayList<ThresholdStatusAO> ret = new ArrayList<ThresholdStatusAO>();

		for (Threshold t : thresholds){
			ThresholdStatusAO statusAO = new ThresholdStatusAO();

			statusAO.setName(t.getName());
			statusAO.setColorCode(t.getStatus().toString().toLowerCase());
			statusAO.setStatus(t.getStatus().toString().toLowerCase());
			statusAO.setDescription(t.getDefinition().describe());
			statusAO.setTimestamp(t.getStatusChangeTimestamp() == 0 ? "Never" : NumberUtils.makeISO8601TimestampString(t.getStatusChangeTimestamp()));
			statusAO.setValue(t.getLastValue());
			statusAO.setChange(t.getStatusChange() == null ? "Never" : t.getStatusChange());

			statusAO.setTimestampForSorting(t.getStatusChangeTimestamp());
			statusAO.setStatusForSorting(t.getStatus());
			statusAO.setId(t.getId());
			statusAO.setFlipCount(t.getFlipCount());


			ret.add(statusAO);
		}
		return ret;
	}

	@Override
	public List<ThresholdDefinitionAO> getThresholdDefinitions() throws APIException {
		List<Threshold> thresholds = ThresholdRepository.getInstance().getThresholds();
		ArrayList<ThresholdDefinitionAO> ret = new ArrayList<ThresholdDefinitionAO>();
		for (Threshold t : thresholds){
			ThresholdDefinitionAO definitionAO = new ThresholdDefinitionAO();
			definitionAO.setId(t.getId());
			definitionAO.setName(t.getName());
			definitionAO.setProducerName(t.getDefinition().getProducerName());
			definitionAO.setStatName(t.getDefinition().getStatName());
			definitionAO.setIntervalName(t.getDefinition().getIntervalName());
			definitionAO.setValueName(t.getDefinition().getValueName());
			definitionAO.setDescriptionString(t.getDefinition().describe());
			for (ThresholdConditionGuard g : t.getGuards()){
				definitionAO.addGuard(g.toString());
			}
			ret.add(definitionAO);
		}
		return ret;

	}

	@Override
	public ThresholdStatus getWorstStatus() throws APIException {
		System.out.println("GET WORST STATUS CALLED: "+ThresholdRepository.getInstance().getWorstStatus());
		return ThresholdRepository.getInstance().getWorstStatus();
	}

	@Override
	public ThresholdStatus getWorstStatus(List<String> thresholdNames) throws APIException {
		return ThresholdRepository.getInstance().getWorstStatus(thresholdNames);
	}
}
