package at.wrk.coceso.dao;

import at.wrk.coceso.entities.Incident;
import at.wrk.coceso.entities.IncidentState;
import at.wrk.coceso.entities.IncidentType;
import at.wrk.coceso.entities.TaskState;
import at.wrk.coceso.utils.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Repository
public class TaskDao {
    private JdbcTemplate jdbc;

    @Autowired
    public TaskDao(DataSource dataSource) {
        jdbc = new JdbcTemplate(dataSource);
    }

    public Map<Integer, TaskState> getAllByIncidentId(int id) {
        String q = "SELECT * FROM task WHERE incident_fk = ?";

        SqlRowSet rs = jdbc.queryForRowSet(q, id);

        Map<Integer, TaskState> ret = new HashMap<Integer, TaskState>();

        while(rs.next()) {
            ret.put(rs.getInt("unit_fk"), TaskState.valueOf(rs.getString("state")));
        }

        return ret;
    }

    public Map<Integer, TaskState> getAllByUnitId(int id) {
        String q = "SELECT * FROM task WHERE unit_fk = ?";

        SqlRowSet rs = jdbc.queryForRowSet(q, id);

        Map<Integer, TaskState> ret = new HashMap<Integer, TaskState>();

        while(rs.next()) {
            ret.put(rs.getInt("incident_fk"), TaskState.valueOf(rs.getString("state")));
        }

        return ret;
    }

    public List<Incident> getAllByUnitIdWithType(int id) {
        String q = "SELECT i.id, i.type, i.state FROM task t LEFT OUTER JOIN incident i ON t.incident_fk = i.id " +
                "WHERE t.unit_fk = ?";

        SqlRowSet rs = jdbc.queryForRowSet(q, id);

        List<Incident> ret = new LinkedList<Incident>();

        while(rs.next()) {

            Incident tmp = new Incident();
            tmp.id = rs.getInt("id");

            String x = rs.getString("type");
            tmp.type = (x == null ? null : IncidentType.valueOf(x));
            x = rs.getString("state");
            tmp.state = (x == null ? null : IncidentState.valueOf(x));

            ret.add(tmp);
        }

        return ret;
    }

    public boolean add(int incident_id, int unit_id, TaskState state) {
        String q = "INSERT INTO task (incident_fk, unit_fk, state) VALUES (?,?,?)";

        try {
            jdbc.update(q, incident_id, unit_id, state.name());
        } catch(DataAccessException e) {
            Logger.debug("TaskDao add: "+e);
            return false;
        }
        return true;
    }

    public boolean update(int incident_id, int unit_id, TaskState state) {
        String q = "UPDATE task SET state = ? WHERE incident_fk = ? AND unit_fk = ?";

        try {
            jdbc.update(q, state.name(), incident_id, unit_id);
        } catch(DataAccessException e) {
            Logger.debug("TaskDao update: "+e);
            return false;
        }
        return true;
    }

    public void remove(int incident_id, int unit_id) {
        String q = "DELETE FROM task WHERE incident_fk = ? AND unit_fk = ?";

        try {
            jdbc.update(q, incident_id, unit_id);
        } catch(DataAccessException e) {
            Logger.debug("TaskDao remove: "+e);
        }
    }

    public void removeAllByUnit(int unit_id) {
        String q = "DELETE FROM task WHERE unit_fk = ?";

        try {
            jdbc.update(q, unit_id);
        } catch(DataAccessException e) {
            Logger.debug("TaskDao removeAllByUnit: "+e);
        }
    }

    public void removeAllByIncident(int incident_id) {
        String q = "DELETE FROM task WHERE incident_fk = ?";

        try {
            jdbc.update(q, incident_id);
        } catch(DataAccessException e) {
            Logger.debug("TaskDao removeAllByIncident: "+e);
        }
    }
}
