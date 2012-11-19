package net.anotheria.moskito.annotation.callingAspect;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 19.11.12 00:00
 */
public class AbstractMoskitoAspect {
	public String getCategory(String proposal) {
		return proposal==null || proposal.length()==0 ? "annotated" : proposal;
	}

	public String getSubsystem(String proposal) {
		return proposal==null || proposal.length()==0 ? "default" : proposal;
	}
}
