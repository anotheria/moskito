package net.anotheria.moskito.core.config.filter;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import org.configureme.annotations.Configure;
import org.configureme.annotations.ConfigureMe;

import java.io.Serializable;

/**
 * Configuration object vor generic filters. Lists enabled case extractors.
 *
 * @author lrosenberg
 * @since 26.04.16 19:19
 */
@SuppressFBWarnings({"SE_BAD_FIELD", "EI_EXPOSE_REP", "EI_EXPOSE_REP2"})
@ConfigureMe
public class FilterConfig implements Serializable{
	/**
	 * SerialVersionUID.
	 */
	private static final long serialVersionUID = -3292200778852233918L;

	@Configure
	private String[] caseExtractors = new String[]{
		"net.anotheria.moskito.web.filters.caseextractor.RequestURICaseExtractor",
		"net.anotheria.moskito.web.filters.caseextractor.RefererCaseExtractor",
		"net.anotheria.moskito.web.filters.caseextractor.MethodCaseExtractor",
		"net.anotheria.moskito.web.filters.caseextractor.UserAgentCaseExtractor",
		"net.anotheria.moskito.web.filters.caseextractor.DomainCaseExtractor"
	};

	public String[] getCaseExtractors() {
		return caseExtractors;
	}

	public void setCaseExtractors(String[] caseExtractors) {
		this.caseExtractors = caseExtractors;
	}
}
