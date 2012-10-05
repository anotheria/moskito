package net.anotheria.moskito.sql.util;

import net.anotheria.db.config.JDBCConfig;
import net.anotheria.db.config.JDBCConfigFactory;
import org.apache.commons.dbcp.BasicDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by IntelliJ IDEA.
 *
 * @author <a href="mailto:vzhovtiuk@anotheria.net">Vitaliy Zhovtiuk</a>
 *         Date: 11/29/11
 *         Time: 6:11 PM
 *         To change this template use File | Settings | File Templates.
 */
public class TestDBUtil {
    private static final String CREATE_TABLE_QUERY = "CREATE TABLE matchervalue(\n" +
            "\tid bigint PRIMARY KEY,\n" +
            "\ttype integer,\n" +
            "\tvalue varchar(255),\n" +
            "\tmatcherid varchar(255),\n" +
            "\tdao_created bigint,\n" +
            "\tdao_updated bigint\n" +
            ")";

    private static final String DROP_TABLE_QUERY = "DROP TABLE matchervalue";

    public static void createTable(Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        statement.execute(CREATE_TABLE_QUERY);
    }

    public static void dropTable(Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        statement.execute(DROP_TABLE_QUERY);
    }

    public static Connection getConnection() throws SQLException {
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
