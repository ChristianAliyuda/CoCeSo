package at.wrk.coceso.dao.mapper;

import at.wrk.coceso.entities.*;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class LogMapper implements RowMapper<LogEntry> {
    @Override
    public LogEntry mapRow(ResultSet rs, int i) throws SQLException {
        LogEntry l = new LogEntry();

        l.id = rs.getInt("l.id");
        l.autoGenerated = rs.getBoolean("l.autoGenerated");
        l.text = rs.getString("l.text");
        l.timestamp = rs.getTimestamp("l.timestamp");
        l.json = rs.getString("l.json");

        try{
            l.state = TaskState.valueOf(rs.getString("l.state"));
        }
        catch(IllegalArgumentException e) {
            l.state = null;
        }


        l.user = new Person();
        l.user.id = rs.getInt("p.id");
        l.user.sur_name = rs.getString("p.sur_name");
        l.user.given_name = rs.getString("p.given_name");

        // References NOT RESOLVED
        l.incident = new Incident();
        l.incident.id = rs.getInt("l.incident");

        l.unit = new Unit();
        l.unit.id = rs.getInt("l.unit");

        l.aCase = null; // Entries are 'final', aCase is only in DB relevant TODO Change if used internally

        return l;
    }
}
