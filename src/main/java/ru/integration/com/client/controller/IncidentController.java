package ru.integration.com.client.controller;

import com.github.nmorel.gwtjackson.client.ObjectMapper;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.inject.Inject;
import ru.integration.com.client.event.AddTodoEvent;
import ru.integration.com.client.event.AddTodoEventHandler;
import ru.integration.com.client.event.incident.NewIncidentEvent;
import ru.integration.com.client.event.incident.NewIncidentEventHandler;
import ru.integration.com.client.model.IncidentModelHandler;
import ru.integration.com.client.model.ModelHandler;
import ru.integration.com.client.ui.IncidentWidget;
import ru.integration.com.client.ui.MainPanel;
import ru.integration.com.common.model.Customer;
import ru.integration.com.common.model.Incident;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by eljah32 on 11/18/2018.
 */
public class IncidentController {
    private SimpleEventBus _eventBus;

    /**
     * Model Handler
     */
    private IncidentModelHandler _incidentModelHandler;

    /**
     * main panel UI
     */
    private IncidentWidget _incidentWidget;
    private MainPanel _mainPanel;

    public static interface IncidentMapper extends ObjectMapper<Incident> {
    }

    IncidentMapper mapper = GWT.create(IncidentMapper.class);

    @Inject
    public IncidentController(SimpleEventBus eventBus, IncidentModelHandler modelHandler, MainPanel mainPanel) {

        _eventBus = eventBus;
        _incidentModelHandler = modelHandler;
        _mainPanel = mainPanel;
    }

    /**
     * Bind all events handler
     */
    public void bindHandlers() {

        _eventBus.addHandler(NewIncidentEvent.TYPE, new NewIncidentEventHandler() {

            @Override
            public void onNewIncidentEventHandler(NewIncidentEvent event) {

                newIncident(event.getStartDate());
                //addTodo(event.getTodoTitle());
            }
        });
    }



    protected void newIncident(Date startTime) {
        Incident incident=new Incident();
        incident.setIncedentStart(startTime);
        _incidentModelHandler.add(incident);
        _incidentWidget=new IncidentWidget(incident,_eventBus);
        _mainPanel.newIncident(_incidentWidget);
        _incidentWidget.setUpStartDate();

    }

};
