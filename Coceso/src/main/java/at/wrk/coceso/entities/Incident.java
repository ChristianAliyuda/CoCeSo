package at.wrk.coceso.entities;


import at.wrk.coceso.entities.*;

import java.util.*;

public class Incident {
    public int id;

    public Case aCase;

    public IncidentState state;

    public int priority;

    public boolean blue;

    public Map<Integer, TaskState> units;

    public CocesoPOI bo;

    public CocesoPOI ao;

    public String casusNr;

    public String info;

    public String caller;

    public IncidentType type;
}