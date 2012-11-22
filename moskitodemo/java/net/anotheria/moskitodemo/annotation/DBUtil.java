package net.anotheria.moskitodemo.annotation;

import net.anotheria.db.config.JDBCConfig;
import net.anotheria.db.config.JDBCConfigFactory;
import net.anotheria.moskito.aop.annotation.MonitorMethod;
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
public class DBUtil {
    private static final String CREATE_TABLE_QUERY = "CREATE TABLE comment(\n" +
            "\tid bigint PRIMARY KEY,\n" +
            "\tfirstName varchar(255),\n" +
            "\tlastName varchar(255),\n" +
            "\temail varchar(255),\n" +
            "\ttext varchar(255),\n" +
            "\ttimestamp bigint,\n" +
            "\twishesUpdates boolean,\n" +
            "\tdao_created bigint,\n" +
            "\tdao_updated bigint\n" +
            ")";

    private static final String DROP_TABLE_QUERY = "DROP TABLE comment";

    public static void createTable() throws SQLException {
        Statement statement = getConnection().createStatement();
        statement.execute(CREATE_TABLE_QUERY);
    }

    public static void dropTable() throws SQLException {
        Statement statement = getConnection().createStatement();
        statement.execute(DROP_TABLE_QUERY);
    }

    @MonitorMethod
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
