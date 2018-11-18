package ru.integration.com.client.ui.schedule;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.http.client.*;
import com.google.gwt.user.client.Window;
import ru.integration.com.client.event.incident.IncidentMapper;
import ru.integration.com.client.event.incident.NewIncidentEvent;
import ru.integration.com.common.model.Customer;
import ru.integration.com.common.model.Incident;

import java.util.List;

/**
 * Created by eljah32 on 11/18/2018.
 */
public class CheckNewIncidentCommand implements Scheduler.ScheduledCommand {

    EventBus eventBus;
    IncidentMapper mapper = GWT.create(IncidentMapper.class);

    public CheckNewIncidentCommand(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    @Override
    public void execute() {

        String pageBaseUrl = GWT.getHostPageBaseURL();
        RequestBuilder rb = new RequestBuilder(RequestBuilder.GET, pageBaseUrl + "/rest/incidents/current");
        rb.setCallback(new RequestCallback() {

            public void onError(Request request, Throwable e) {
                // some error handling code here
                Scheduler.get().scheduleDeferred(new CheckNewIncidentCommand(eventBus));
                //Window.alert("error = " + e.getMessage());

            }

            public void onResponseReceived(Request request, Response response) {
                if (200 == response.getStatusCode()) {
                    Scheduler.get().scheduleDeferred(new CheckNewIncidentCommand(eventBus));
                    String text = response.getText();
                    if (text != null) {
                        Incident incident = mapper.read(text);
                        if (incident != null) {
                            eventBus.fireEvent(new NewIncidentEvent(incident));
                        } else {
                            Window.alert("Empty");
                        }
                    }

                } else {
                    Scheduler.get().scheduleDeferred(new CheckNewIncidentCommand(eventBus));
                }

            }
        });
        try {
            rb.send();
        } catch (RequestException e) {
            Scheduler.get().scheduleDeferred(new CheckNewIncidentCommand(eventBus));
            e.printStackTrace();
            Window.alert("error = " + e.getMessage());

        }
    }
}
