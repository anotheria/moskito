package net.anotheria.moskito.sql.callingAspect;

import net.anotheria.db.dao.RowMapper;
import net.anotheria.db.dao.RowMapperException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by IntelliJ IDEA.
 *
 * @author <a href="mailto:vzhovtiuk@anotheria.net">Vitaliy Zhovtiuk</a>
 *         Date: 11/29/11
 *         Time: 10:53 AM
 *         To change this template use File | Settings | File Templates.
 */
public class MatcherValueRowMapper extends RowMapper<MatcherValue> {

    private static Logger log = LoggerFactory.getLogger(MatcherValueRowMapper.class);

    @Override
    public MatcherValue map(ResultSet row) throws RowMapperException {
        try {
            long id = row.getLong(1);
            MatcherValue ret = new MatcherValueVO(String.valueOf(id));
            ret.setType(row.getInt(2));
            ret.setValue(row.getString(3));
            ret.setMatcherId(row.getString(4));
            ((MatcherValueVO) ret).setDaoCreated(row.getLong(5));
            ((MatcherValueVO) ret).setDaoUpdated(row.getLong(6));
            return ret;
        } catch (SQLException e) {
            log.error("map", e);
            throw new RowMapperException(e);
        }
    }
}
