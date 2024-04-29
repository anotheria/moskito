package net.anotheria.moskito.web.filters.caseextractor;

import jakarta.servlet.http.HttpServletRequest;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 26.04.16 19:04
 */
public interface FilterCaseExtractor {
	String extractCaseName(HttpServletRequest req);

	String getProducerId();

	String getCategory();

	String getSubsystem();
}
