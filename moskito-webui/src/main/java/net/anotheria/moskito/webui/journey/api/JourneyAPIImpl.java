package net.anotheria.moskito.webui.journey.api;

import net.anotheria.anoplass.api.APIException;
import net.anotheria.anoplass.api.APIInitException;
import net.anotheria.moskito.core.calltrace.CurrentlyTracedCall;
import net.anotheria.moskito.core.calltrace.TraceStep;
import net.anotheria.moskito.core.journey.Journey;
import net.anotheria.moskito.core.journey.JourneyManager;
import net.anotheria.moskito.core.journey.JourneyManagerFactory;
import net.anotheria.moskito.core.journey.NoSuchJourneyException;
import net.anotheria.moskito.core.stats.TimeUnit;
import net.anotheria.moskito.webui.shared.api.AbstractMoskitoAPIImpl;
import net.anotheria.moskito.webui.util.TagsUtil;
import net.anotheria.util.NumberUtils;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 14.02.13 10:00
 */
public class JourneyAPIImpl extends AbstractMoskitoAPIImpl implements  JourneyAPI {

	private JourneyManager journeyManager;

	@Override
	public void init() throws APIInitException {
		super.init();
		journeyManager = JourneyManagerFactory.getJourneyManager();
	}

	@Override
	public List<JourneyListItemAO> getJourneys() throws APIException {
		List<Journey> journeys = journeyManager.getJourneys();
		List<JourneyListItemAO> beans = new ArrayList<JourneyListItemAO>(journeys.size());

		for (Journey j : journeys){
			JourneyListItemAO bean = new JourneyListItemAO();

			bean.setName(j.getName());
			bean.setActive(j.isActive());
			bean.setCreated(NumberUtils.makeISO8601TimestampString(j.getCreatedTimestamp()));
			bean.setLastActivity(NumberUtils.makeISO8601TimestampString(j.getLastActivityTimestamp()));
			bean.setNumberOfCalls(j.getTracedCalls().size());
			beans.add(bean);
		}

		return beans;
	}

	@Override
	public JourneyAO getJourney(String name) throws APIException {
		JourneyAO ret = new JourneyAO();
		Journey journey;
		try{
			journey = journeyManager.getJourney(name);
		}catch(NoSuchJourneyException e){
			throw new APIException("Journey not found.");
		}

		ret.setName(journey.getName());
		ret.setActive(journey.isActive());
		ret.setCreatedTimestamp(journey.getCreatedTimestamp());
		ret.setLastActivityTimestamp(journey.getLastActivityTimestamp());

		List<CurrentlyTracedCall> recorded = journey.getTracedCalls();
		List<JourneySingleTracedCallAO> calls = new ArrayList<JourneySingleTracedCallAO>(recorded.size());
		for (int i=0; i<recorded.size(); i++){
			CurrentlyTracedCall tracedCall = recorded.get(i);
			if(tracedCall == null){
				//this is a WTF, how could a null get added here in first place.
				log.warn("Unexpected null as tracedCall at position " + i);
				continue;
			}
			JourneySingleTracedCallAO b = new JourneySingleTracedCallAO();
			b.setName(tracedCall.getName());
			b.setDate(NumberUtils.makeISO8601TimestampString(tracedCall.getCreated()));
			b.setContainedSteps(tracedCall.getNumberOfSteps());
			b.setDuration(tracedCall.getRootStep().getDuration());
			calls.add(b);
		}

		ret.setCalls(calls);


		return ret;
	}


	private TracedCallAO mapTracedCall(CurrentlyTracedCall useCase, TimeUnit unit){
		if (useCase==null){
			throw new IllegalArgumentException("UseCase is null in call "+unit);
		}
		TraceStep root = useCase.getRootStep();
		TracedCallAO ret = new TracedCallAO();
		ret.setName(useCase.getName());
		ret.setCreated(useCase.getCreated());
		ret.setDate(NumberUtils.makeISO8601TimestampString(useCase.getCreated()));

		//set tags
		Map<String, String> tags = useCase.getTags();
		if (tags!=null && tags.size()>0){
			ret.setTags(TagsUtil.tagsMapToTagEntries(tags));
		}

		JourneyCallIntermediateContainer container = new JourneyCallIntermediateContainer();
		fillUseCasePathElementBeanList(container, root, unit);
		ret.setElements(container.getElements());

		//check for duplicates
		List<TracedCallDuplicateStepsAO> dupSteps = new ArrayList<>();
		Map<String,JourneyCallIntermediateContainer.ReversedCallHelper > stepsReversed = container.getReversedSteps();
		for (Map.Entry<String, JourneyCallIntermediateContainer.ReversedCallHelper> entry : stepsReversed.entrySet()){
			if (entry.getValue()!=null && entry.getValue().getPositions().size()>1){
				//duplicate found
				TracedCallDuplicateStepsAO dupStep = new TracedCallDuplicateStepsAO();
				dupStep.setCall(entry.getKey());
				dupStep.setPositions(entry.getValue().getPositions());
				dupStep.setTimespent(entry.getValue().getTimespent());
				dupStep.setDuration(entry.getValue().getDuration());
				dupSteps.add(dupStep);
			}
		}

		ret.setDuplicateSteps(dupSteps);

		return ret;
	}

	/**
	 * Allows to fill {@link JourneyCallIntermediateContainer} with tracer steps.
	 *
	 * @param container {@link JourneyCallIntermediateContainer}
	 * @param root      {@link TraceStep}
	 * @param unit      {@link TimeUnit}
	 */
	private void fillUseCasePathElementBeanList(final JourneyCallIntermediateContainer container, final TraceStep root, final TimeUnit unit) {
		int currentLevel = 0,
				elementsToIncreaseLevel = 1,
				nextElementsToIncreaseLevel = 0;

		final Queue<TraceStep> queue = new LinkedList<>();
		queue.add(root);

		while (!queue.isEmpty()) {
			final TraceStep currentTraceStep = queue.poll();

			// process current trace step
			processTraceStep(container, currentTraceStep, unit, currentLevel);

			final List<TraceStep> children = currentTraceStep.getChildren();

			// calculate depth level
			nextElementsToIncreaseLevel += children.size();
			if (--elementsToIncreaseLevel == 0) {
				currentLevel++;
				elementsToIncreaseLevel = nextElementsToIncreaseLevel;
				nextElementsToIncreaseLevel = 0;
			}

			// add children for next processing
			if (!children.isEmpty()) {
				queue.addAll(children);
			}
		}
	}

	/**
	 * Allows to process given {@link TraceStep}, convert it to {@link TracedCallStepAO} and add to {@link JourneyCallIntermediateContainer}.
	 *
	 * @param container  {@link JourneyCallIntermediateContainer}
	 * @param traceStep  {@link TraceStep}
	 * @param unit       {@link TimeUnit}
	 * @param depthLevel the depth level of {@link TraceStep} tree
	 */
	private void processTraceStep(final JourneyCallIntermediateContainer container, final TraceStep traceStep, final TimeUnit unit, final int depthLevel) {
		final TracedCallStepAO b = new TracedCallStepAO();
		b.setCall(traceStep.getParent() == null ? "ROOT" : traceStep.getCall());
		b.setRoot(traceStep.getParent() == null);
		b.setLayer(depthLevel);
		b.setDuration(unit.transformNanos(traceStep.getDuration()));
		b.setLevel(depthLevel);
		b.setAborted(traceStep.isAborted());

		long timeSpentInChildren = 0;
		for (TraceStep p : traceStep.getChildren()) {
			timeSpentInChildren += p.getDuration();
		}

		b.setTimespent(unit.transformNanos((traceStep.getDuration() - timeSpentInChildren)));

		container.add(b);
	}

	@Override
	public TracedCallAO getTracedCallByName(String journeyName, String traceName, TimeUnit unit)  throws APIException{
		Journey journey = getJourneyByName(journeyName);
		CurrentlyTracedCall call = journey.getStepByName(traceName);
		try {
			return mapTracedCall(call, unit);
		}catch(IllegalArgumentException e){
			throw new IllegalStateException("Can' lookup call by name: "+journeyName+", "+traceName+", "+unit);
		}
	}


	@Override
	public TracedCallAO getTracedCall(String journeyName, int callPosition, TimeUnit unit) throws APIException {
		Journey journey = getJourneyByName(journeyName);
		CurrentlyTracedCall call = journey.getTracedCalls().get(callPosition);
		return mapTracedCall(call, unit);
	}

	@Override
	public AnalyzedJourneyAO analyzeJourney(String journeyName) throws APIException {
		Journey journey = getJourneyByName(journeyName);
		List<CurrentlyTracedCall> tracedCalls = journey.getTracedCalls();
		List<AnalyzedProducerCallsMapAO> callsList = new ArrayList<>(tracedCalls.size() + 1);

		AnalyzedProducerCallsMapAO overallCallsMap = new AnalyzedProducerCallsMapAO("Total by Producer");
		AnalyzedProducerCallsMapAO categoryCallsMap = new AnalyzedProducerCallsMapAO("Total by Category");
		AnalyzedProducerCallsMapAO subsystemCallsMap = new AnalyzedProducerCallsMapAO("Total by Subsystem");

		callsList.add(overallCallsMap);
		callsList.add(categoryCallsMap);
		callsList.add(subsystemCallsMap);

		for (CurrentlyTracedCall tc : tracedCalls){
			if (tc==null){
				log.warn("TracedCall is null!");
				continue;
			}
			AnalyzedProducerCallsMapAO singleCallMap = new AnalyzedProducerCallsMapAO(tc.getName());
			for (TraceStep step : tc.getRootStep().getChildren()){
				addStep(step, singleCallMap, overallCallsMap);

				if (step.getProducer() != null) {
					addStep(step.getProducer().getCategory(), step, categoryCallsMap);
					addStep(step.getProducer().getSubsystem(), step, subsystemCallsMap);
				}
			}
			callsList.add(singleCallMap);
		}


		AnalyzedJourneyAO result = new AnalyzedJourneyAO();
		result.setName(journeyName);
		result.setCalls(callsList);

		return result;
	}

	private Journey getJourneyByName(String name) throws APIException {
		try {
			journeyManager.getJourney(name);
			return journeyManager.getJourney(name);
		}catch(NoSuchJourneyException e){
			throw new APIException("Journey " + name+ " not found.");
		}
	}

	private void addStep(TraceStep step, AnalyzedProducerCallsMapAO... maps) {
		String producerName = step.getProducer() == null ? "UNKNOWN" : step.getProducer().getProducerId();
		addStep(producerName, step, maps);
	}

	private void addStep(String callId, TraceStep step, AnalyzedProducerCallsMapAO... maps) {
		for (AnalyzedProducerCallsMapAO map : maps) {
			map.addProducerCall(callId, step.getNetDuration());
		}
		for (TraceStep childStep : step.getChildren()){
			addStep(callId, childStep, maps);
		}
	}


	@Override
	public void deleteJourney(String journeyName) throws APIException {
		journeyManager.removeJourney(journeyName);
	}
}
