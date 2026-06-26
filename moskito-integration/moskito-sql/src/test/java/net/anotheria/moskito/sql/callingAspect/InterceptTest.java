package net.anotheria.moskito.sql.callingAspect;

import net.anotheria.moskito.sql.util.TestDBUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * SQL intercept test.
 *
 * @author <a href="mailto:vzhovtiuk@anotheria.net">Vitaliy Zhovtiuk</a>
 *         Date: 11/29/11
 *         Time: 2:22 PM
 */
@Disabled
public class InterceptTest {

    private static final String LINESEP = System.getProperty("line.separator");
	
    private static final String INTERCEPTED_OUTPUT = "SELECT id, type, value, matcherid, dao_created, dao_updated FROM matchervalue ORDER BY id" + LINESEP +
            "List of matchers []" + LINESEP +
            "INSERT INTO matchervalue (id, type, value, matcherid, dao_created) VALUES (?,?,?,?,?)" + LINESEP+
            "SELECT id, type, value, matcherid, dao_created, dao_updated FROM matchervalue WHERE matchervalue.id = ?" + LINESEP +
            "Created MatcherValue [1] type: 1, value: 123" + LINESEP;

    private Connection connection;

    @BeforeEach
    public void setUp() throws Exception {
        connection = TestDBUtil.getConnection();
        TestDBUtil.createTable(connection);
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
        assertEquals(expected, sql, "Should have logged query");
    }



    @AfterEach
    public void tearDown() throws Exception {
        TestDBUtil.dropTable(connection);
        connection.close();
    }
}
