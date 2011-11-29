package net.java.dev.moskito.sql.util;

import net.java.dev.moskito.core.producers.IStats;
import net.java.dev.moskito.core.registry.ProducerRegistryFactory;
import net.java.dev.moskito.sql.aspect.MatcherValue;
import net.java.dev.moskito.sql.aspect.MatcherValueBuilder;
import net.java.dev.moskito.sql.aspect.MatcherValueDAO;
import net.java.dev.moskito.sql.stats.QueryStats;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

/**
 * SQL intercept test.
 *
 * @author <a href="mailto:vzhovtiuk@anotheria.net">Vitaliy Zhovtiuk</a>
 *         Date: 11/29/11
 *         Time: 2:22 PM
 */
public class QueryProducerTest {

    private Connection connection;
    private QueryProducer queryProducer;

    @Before
    public void setUp() throws Exception {
        connection = TestDBUtil.getConnection();
        TestDBUtil.createTable(connection);
        queryProducer = (QueryProducer) ProducerRegistryFactory.getProducerRegistryInstance().getProducer(QueryProducer.PRODUCER_ID);
        queryProducer.reset();
    }

    @Test
    public void shouldGetQueriesNumber() throws Exception {
        MatcherValueDAO valueDAO = new MatcherValueDAO();
        System.out.println("List of matchers " + valueDAO.getMatcherValues(connection));

        MatcherValue matcherValue = valueDAO.createMatcherValue(connection, new MatcherValueBuilder().value("123").type(1).matcherId("123").build());
        System.out.println("Created " + valueDAO.getMatcherValue(connection, matcherValue.getId()));



        IStats iStats = queryProducer.getStats().get(0);
        assertNotNull("Query execution stat should be defined", iStats);
        assertTrue("Query execution stat should be defined", iStats instanceof QueryStats);

        QueryStats queryStats = (QueryStats) iStats;
        System.out.println("Query stats " + queryStats.toStatsString());
        assertEquals("Should have 3 queries", 3, queryStats.getQueriesExecuted(null));
    }

    @After
    public void tearDown() throws Exception {
        TestDBUtil.dropTable(connection);
        connection.close();
    }
}
