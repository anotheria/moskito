package net.anotheria.moskito.core.config.filter;

import org.configureme.annotations.Configure;
import org.configureme.annotations.ConfigureMe;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 26.04.16 19:19
 */
@ConfigureMe
public class FilterConfig {
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
