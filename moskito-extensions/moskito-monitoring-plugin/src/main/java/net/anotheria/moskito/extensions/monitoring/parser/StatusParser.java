package net.anotheria.moskito.extensions.monitoring.parser;

/**
 * Interface for Parsers of status data.
 * @param <S> type of source status(value to parse).
 * @param <D> type of parsing result.
 * @author dzhmud
 */
public interface StatusParser<S,D> {

    /**
     * Parse status of type S into the type D.
     * @param status status retrieved from application
     * @return parsed status.
     */
    D parse(S status);

}
