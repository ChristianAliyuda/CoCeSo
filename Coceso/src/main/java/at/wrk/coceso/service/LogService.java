package at.wrk.coceso.service;


import at.wrk.coceso.dao.LogDao;
import at.wrk.coceso.entities.*;
import at.wrk.coceso.utils.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class LogService {
    @Autowired
    LogDao logDao;

    public void logFull(Person user, String text, int activeCase, Unit unit, Incident incident, boolean autoGenerated) {
        if(user == null) {
            Logger.error("LogService called without 'user'!");
            return;
        }
        LogEntry logEntry = new LogEntry();

        logEntry.user = user;
        logEntry.text = text;
        logEntry.unit = unit;
        logEntry.incident = incident;
        logEntry.autoGenerated = autoGenerated;

        logEntry.aCase = new Case();
        logEntry.aCase.id = activeCase;
        logEntry.state = incident != null && incident.units != null && unit != null ?
                incident.units.get(unit.id) : null;

        ObjectMapper mapper = new ObjectMapper();

        try {
            logEntry.json = mapper.writeValueAsString(new Object[] { unit, incident });
        } catch (IOException e) {
            e.printStackTrace();
        }

        logDao.add(logEntry);

    }

    public void logWithIDs(int user_id, String text, int activeCase, int unit_id, int incident_id, boolean auto) {
        if(user_id < 1)
            return;
        logDao.add(activeCase, unit_id, incident_id, auto, user_id, text);
    }

    public void logByUser(Person user, String text, int activeCase) {
        logFull(user, text, activeCase, null, null, false);
    }
}
