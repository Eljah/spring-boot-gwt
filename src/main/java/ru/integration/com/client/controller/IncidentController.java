package ru.integration.com.client.controller;

import com.github.nmorel.gwtjackson.client.ObjectMapper;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.http.client.*;
import com.google.gwt.user.client.Window;
import com.google.inject.Inject;
import ru.integration.com.client.event.incident.*;
import ru.integration.com.client.model.IncidentModelHandler;
import ru.integration.com.client.ui.IncidentWidget;
import ru.integration.com.client.ui.MainPanel;
import ru.integration.com.common.model.Address;
import ru.integration.com.common.model.Incident;

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

    IncidentMapper mapper = GWT.create(IncidentMapper.class);

    public static interface AddressMapper extends ObjectMapper<Address> {
    }

    AddressMapper addressMapper = GWT.create(AddressMapper.class);
    IncidentMapper incidentMapper = GWT.create(IncidentMapper.class);


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

                newIncident(event.getIncident());
                //addTodo(event.getTodoTitle());
            }
        });

        _eventBus.addHandler(AddressSearchEvent.TYPE, new AddressSearchEventHandler() {

            @Override
            public void onAddressSearchEventHandler(AddressSearchEvent event) {
                requestAddress(event.getTypedAddress());
                //addTodo(event.getTodoTitle());
            }
        });

        _eventBus.addHandler(RepairCallEvent.TYPE, new RepairCallEventHandler() {

            @Override
            public void onRepairCallEventHandler(RepairCallEvent event) {

                updateIncident(event.getIncident());
                //addTodo(event.getTodoTitle());
            }
        });
    }


    protected void newIncident(Incident incident) {
        _incidentModelHandler.add(incident);
        _incidentWidget = new IncidentWidget(incident, _eventBus);
        _mainPanel.newIncident(_incidentWidget);
        _incidentWidget.setUpStartDate();
        _incidentWidget.setIncidentPhoneNumber();
    }

    protected void requestAddress(String address) {
        String pageBaseUrl = GWT.getHostPageBaseURL();
        RequestBuilder rb = new RequestBuilder(RequestBuilder.GET, pageBaseUrl + "/rest/incidents/address/" + address);
        rb.setCallback(new RequestCallback() {

            public void onError(Request request, Throwable e) {
                // some error handling code here
                Window.alert("error = " + e.getMessage());
            }

            public void onResponseReceived(Request request, Response response) {
                if (200 == response.getStatusCode()) {
                    String text = response.getText();
                    if (text != null) {
                        Address addressReturned = addressMapper.read(text);
                        if (address != null) {
                            _incidentWidget.setAddressSearchResults(addressReturned);
                            _incidentWidget.fakePointShow();
                        } else {
                            Window.alert("Empty");
                        }
                    }

                } else {

                }

            }
        });
        try {
            rb.send();
        } catch (RequestException e) {
            e.printStackTrace();
            Window.alert("error = " + e.getMessage());

        }
    }


    protected void updateIncident(Incident incident) {
        String pageBaseUrl = GWT.getHostPageBaseURL();
        RequestBuilder rb = new RequestBuilder(RequestBuilder.POST, pageBaseUrl + "/rest/incidents/current");
        rb.setRequestData(incidentMapper.write(incident));
        rb.setCallback(new RequestCallback() {
            Incident incident2=null;
            public void onError(Request request, Throwable e) {
                // some error handling code here
                Window.alert("error = " + e.getMessage());
            }

            public void onResponseReceived(Request request, Response response) {
                if (200 == response.getStatusCode()) {
                    String text = response.getText();
                    if (text != null) {
                        incident2 = incidentMapper.read(text);
                        _incidentModelHandler.update(incident2);
                        //_incidentWidget.
                    }

                } else {

                }

            }
        });
        try {
            rb.send();
        } catch (RequestException e) {
            e.printStackTrace();
            Window.alert("error = " + e.getMessage());

        }
        ;
    }

    ;


};
