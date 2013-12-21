package at.wrk.coceso.dao;


import at.wrk.coceso.dao.mapper.UnitMapper;
import at.wrk.coceso.entity.Person;
import at.wrk.coceso.entity.Point;
import at.wrk.coceso.entity.Unit;
import at.wrk.coceso.entity.enums.UnitState;
import at.wrk.coceso.utils.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

@Repository
public class UnitDao extends CocesoDao<Unit> {

    @Autowired
    UnitMapper unitMapper;

    @Autowired
    CrewDao crewDao;

    @Autowired
    PointDao pointDao;

    @Autowired
    public UnitDao(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public Unit getById(int id) {
        if(id < 1) {
            Logger.error("UnitDao.getById(int): Invalid ID: " + id);
            return null;
        }

        String q = "select * from unit where id = ?";
        Unit unit;

        try {
            unit = jdbc.queryForObject(q, new Integer[] {id}, unitMapper);
        }
        catch(DataAccessException dae) {
            Logger.error("UnitDao.getById(int): requested id: "+id+"; DataAccessException: "+dae.getMessage());
            return null;
        }

        return unit;
    }

    @Override
    public List<Unit> getAll(int concern_id) {
        if(concern_id < 1) {
            Logger.warning("UnitDao.getAll: invalid concern_id: "+concern_id);
            return null;
        }
        String q = "SELECT * FROM unit WHERE concern_fk = ? ORDER BY id ASC";

        try {
            return jdbc.query(q, new Object[] {concern_id}, unitMapper);
        }
        catch(DataAccessException dae) {
            Logger.error("UnitDao.getAll: DataAccessException: "+dae.getMessage());
            return null;
        }
    }

    //TODO move to PointService
    Point createPointIfNotExist(Point dummy) {
        if(dummy == null)
            return null;

        if(dummy.id > 0) {
            Point p = pointDao.getById(dummy.id);
            if(p != null)
                return p;
        }


        Point point = pointDao.getByInfo(dummy.info);
        if(point == null && dummy.info != null && !dummy.info.isEmpty()) {
            dummy.id = pointDao.add(dummy);
            return dummy;
        }
        else return point;
    }

    /**
     * Update Unit. Only Values state, info, position, home are changeable. All others are LOCKED!
     * To change these, use updateFull(Unit).
     *
     * @param unit Unit to write to DB
     * @return Success of Operation
     */
    @Override
    public boolean update(Unit unit) {
        if(unit == null) {
            Logger.error("UnitDao.update(Unit): unit is NULL");
            return false;
        }
        if(unit.id <= 0) {
            Logger.error("UnitDao.update(Unit): Invalid id: " + unit.id + ", call: "+unit.call);
            return false;
        }

        unit.home = createPointIfNotExist(unit.home);
        unit.position = createPointIfNotExist(unit.position);

        final String pre_q = "update unit set";
        final String suf_q = " where id = " + unit.id;

        boolean first = true;
        boolean info_given = false;

        String q = pre_q;
        if(unit.state != null) {
            q += " state = '" + unit.state.name() + "'";
            first = false;
        }
        if(unit.info != null) {
            if(!first) {
                q += ",";
            }
            q += " info = '?'";
            info_given = true;
            first = false;
        }
        if(unit.position != null && unit.position.id > 0) {
            if(!first) {
                q += ",";
            }
            q += " position_point_fk = " + unit.position.id;
            first = false;
        }
        if(unit.home != null && unit.home.id  > 0) {
            if(!first) {
                q += ",";
            }
            q += " home_point_fk = " + unit.home.id;
            // first = false;
        }
        q += suf_q;
        try {
            if(info_given) {
                jdbc.update(q, unit.info);
            }
            else {
                jdbc.update(q);
            }
        }
        catch(DataAccessException dae) {
            Logger.error("UnitDao.update(Unit): DataAccessException: " + dae.getMessage());
            return false;
        }
        return true;
    }

    public boolean updateFull(Unit unit) {
        if(unit == null) {
            Logger.error("UnitDao.updateFull(Unit): unit is NULL");
            return false;
        }
        if(unit.id <= 0) {
            Logger.error("UnitDao.updateFull(Unit): Invalid id: " + unit.id + ", call: "+unit.call);
            return false;
        }

        unit.home = createPointIfNotExist(unit.home);
        unit.position = createPointIfNotExist(unit.position);


        String q = "UPDATE unit SET state = ?, call = ?, ani = ?, withdoc = ?, " +
                "portable = ?, transportvehicle = ?, info = ?, position_point_fk = ?, home_point_fk = ? WHERE id = ?";

        try {
            jdbc.update(q, unit.state == null ? UnitState.AD.name() : unit.state.name(), unit.call, unit.ani, unit.withDoc, unit.portable, unit.transportVehicle,
                    unit.info, unit.position == null ? null : unit.position.id,
                    unit.home == null ? null : unit.home.id, unit.id);
        }
        catch(DataAccessException dae) {
            Logger.error("UnitDao.updateFull(Unit): DataAccessException: " + dae.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public int add(Unit uunit) {
        if(uunit == null) {
            Logger.error("UnitDao.add(Unit): unit is NULL");
            return -1;
        }
        if(uunit.concern == null || uunit.concern <= 0) {
            Logger.error("UnitDao.add(Unit): No concern given. call: " + uunit.call);
            return -1;
        }

        uunit.prepareNotNull();

        uunit.home = createPointIfNotExist(uunit.home);
        uunit.position = createPointIfNotExist(uunit.position);


        final Unit unit = uunit;

        try {
            final String q = "INSERT INTO unit (concern_fk, state, call, ani, withDoc," +
                    " portable, transportVehicle, info, position_point_fk, home_point_fk) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            KeyHolder holder = new GeneratedKeyHolder();

            jdbc.update(new PreparedStatementCreator() {

                @Override
                public PreparedStatement createPreparedStatement(Connection connection)
                        throws SQLException {
                    PreparedStatement ps = connection.prepareStatement(q, Statement.RETURN_GENERATED_KEYS);

                    ps.setInt(1, unit.concern);
                    ps.setString(2, unit.state == null ? UnitState.AD.name() : unit.state.name());
                    ps.setString(3, unit.call);
                    ps.setString(4, unit.ani);
                    ps.setBoolean(5, unit.withDoc);
                    ps.setBoolean(6, unit.portable);
                    ps.setBoolean(7, unit.transportVehicle);
                    ps.setString(8, unit.info);
                    if(unit.position == null)
                        ps.setObject(9,  null);
                    else
                        ps.setInt(9, unit.position.id);

                    if(unit.home == null)
                        ps.setObject(10,  null);
                    else
                        ps.setInt(10, unit.home.id);
                    return ps;
                }
            }, holder);

            if(unit.crew != null) {
                for(Person p : unit.crew) {
                    crewDao.add(unit, p);
                }
            }
            return (Integer) holder.getKeys().get("id");
        }
        catch (DataAccessException dae) {
            Logger.error("UnitDao.add(Unit): call: "+unit.call+"; DataAccessException: "+dae.getMessage());
            return -1;
        }

    }

    @Override
    public boolean remove(Unit unit) {
        if(unit == null) {
            Logger.error("UnitDao.remove(Unit): unit is NULL");
            return false;
        }
        if(unit.id <= 0) {
            Logger.error("UnitDao.remove(Unit): invalid id: " + unit.id + ", call: " + unit.call);
            return false;
        }
        String q = "delete from unit where id = ?";
        try {
            jdbc.update(q, unit.id);
        }
        catch (DataAccessException dae) {
            Logger.error("UnitDao.remove(Unit): id: "+unit.id+"; DataAccessException: "+dae.getMessage());
            return false;
        }

        return true;
    }
}
