package ru.integration.com.client.model;

import ru.integration.com.common.model.Customer;
import ru.integration.com.common.model.Incident;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by eljah32 on 11/18/2018.
 */
public class IncidentModelHandler {
    List<Incident> openIncidents;
    Incident currentIncident;

    public IncidentModelHandler() {
        openIncidents = new ArrayList<>();
    }

    public void add(Incident incident) {
        if (currentIncident == null) {
            currentIncident = incident;
        }
        openIncidents.add(incident);
    }

    public void update(Incident incident) {
        if (currentIncident == null) {
            currentIncident = incident;
        }
        currentIncident = incident;
    }

    public void remove(Incident incident) {
        if (currentIncident == incident) {
            currentIncident = null;
        }
        openIncidents.remove(incident);
    }
}
