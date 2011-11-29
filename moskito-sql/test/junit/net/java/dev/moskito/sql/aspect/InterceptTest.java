package net.java.dev.moskito.sql.aspect;

import net.anotheria.db.config.JDBCConfig;
import net.anotheria.db.config.JDBCConfigFactory;
import org.apache.commons.dbcp.BasicDataSource;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static junit.framework.Assert.assertEquals;

/**
 * SQL intercept test.
 *
 * @author <a href="mailto:vzhovtiuk@anotheria.net">Vitaliy Zhovtiuk</a>
 *         Date: 11/29/11
 *         Time: 2:22 PM
 */
public class InterceptTest {
    private static final String CREATE_TABLE_QUERY = "CREATE TABLE matchervalue(\n" +
            "\tid bigint PRIMARY KEY,\n" +
            "\ttype integer,\n" +
            "\tvalue varchar(255),\n" +
            "\tmatcherid varchar(255),\n" +
            "\tdao_created bigint,\n" +
            "\tdao_updated bigint\n" +
            ")";
    private static final String INTERCEPTED_OUTPUT = "SELECT id, type, value, matcherid, dao_created, dao_updated FROM matchervalue ORDER BY id\n" +
            "List of matchers []\n" +
            "INSERT INTO matchervalue (id, type, value, matcherid, dao_created) VALUES (?,?,?,?,?)\n" +
            "SELECT id, type, value, matcherid, dao_created, dao_updated FROM matchervalue WHERE matchervalue.id = ?\n" +
            "Created MatcherValue [1] type: 1, value: 123\n";

    private Connection connection;

    @Before
    public void setUp() throws Exception {
        connection = getConnection();
        createTable(connection);
    }

    @Test
    public void shouldInterceptQuery() throws Exception {
        MatcherValueDAO valueDAO = new MatcherValueDAO();
        OutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        System.setOut(printStream);
        System.out.println("List of matchers " + valueDAO.getMatcherValues(connection));

        MatcherValue matcherValue = valueDAO.createMatcherValue(connection, new MatcherValueBuilder().value("123").type(1).matcherId("123").build());
        System.out.println("Created " + valueDAO.getMatcherValue(connection, matcherValue.getId()));
        String sql = outputStream.toString();
        String expected = INTERCEPTED_OUTPUT;
        assertEquals("Should have logged query", expected, sql);
    }

    private void createTable(Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        statement.execute(CREATE_TABLE_QUERY);
    }

    private static Connection getConnection() throws SQLException {
        BasicDataSource newDataSource = new BasicDataSource();
        JDBCConfig config = JDBCConfigFactory.getJDBCConfig();
        newDataSource.setDriverClassName(config.getDriver());
        if (config.getPreconfiguredJdbcUrl() != null && config.getPreconfiguredJdbcUrl().length() > 0)
            newDataSource.setUrl(config.getPreconfiguredJdbcUrl());
        else
            newDataSource.setUrl("jdbc:" + config.getVendor() + "://" + config.getHost() + ":" + config.getPort() + "/" + config.getDb());
        newDataSource.setUsername(config.getUsername());
        newDataSource.setPassword(config.getPassword());

        if (config.getMaxConnections() != Integer.MAX_VALUE)
            newDataSource.setMaxActive(config.getMaxConnections());
        return newDataSource.getConnection();
    }
}
