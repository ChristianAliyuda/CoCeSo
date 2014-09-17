package at.wrk.coceso.dao.mapper;

import at.wrk.coceso.dao.IncidentDao;
import at.wrk.coceso.dao.UnitDao;
import at.wrk.coceso.entity.*;
import at.wrk.coceso.entity.enums.LogEntryType;
import at.wrk.coceso.entity.enums.TaskState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class LogMapper implements RowMapper<LogEntry> {

    @Autowired
    IncidentDao incidentDao;

    @Autowired
    UnitDao unitDao;

    @Override
    public LogEntry mapRow(ResultSet rs, int i) throws SQLException {
        LogEntry l = new LogEntry();

        l.setId(rs.getInt("id"));
        l.setAutoGenerated(rs.getBoolean("autoGenerated"));
        l.setText(rs.getString("text"));
        l.setTimestamp(rs.getTimestamp("timestamp"));
        l.setJson(rs.getString("json"));

        try{
            l.setState(TaskState.valueOf(rs.getString("taskstate")));
        }
        catch(NullPointerException e) {
            l.setState(null);
        }

        try{
            l.setType(LogEntryType.valueOf(rs.getString("type")));
        }
        catch(NullPointerException e) {
            l.setType(null);
        }


        l.setUser(new Operator());
        l.getUser().setId(rs.getInt("pid"));
        l.getUser().setSur_name(rs.getString("sur_name"));
        l.getUser().setGiven_name(rs.getString("given_name"));
        l.getUser().setdNr(rs.getInt("dNr"));
        l.getUser().setContact(rs.getString("contact"));
        l.getUser().setUsername(rs.getString("username"));

        // References NOT RESOLVED
        int incidentID = rs.getInt("incident_fk");
        if(incidentID > 0) {
            l.setIncident(new Incident());
            l.getIncident().setId(incidentID);
        }

        int unitID = rs.getInt("unit_fk");
        if(unitID > 0) {
            l.setUnit(new Unit());
            l.getUnit().setId(unitID);
            l.getUnit().setCall(rs.getString("call"));
        }

        //l.incident = incidentDao.getById(rs.getInt("incident"));
        //l.unit = unitDao.getById(rs.getInt("unit"));

        l.setConcern(null); // Entries are 'final', concern is only in DB relevant TODO Change if used internally

        return l;
    }
}