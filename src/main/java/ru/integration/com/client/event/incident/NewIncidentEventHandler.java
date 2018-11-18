package ru.integration.com.client.event.incident;

import com.google.gwt.event.shared.EventHandler;

/**
 * Created by eljah32 on 11/18/2018.
 */
public interface NewIncidentEventHandler  extends EventHandler {
    void onNewIncidentEventHandler(NewIncidentEvent event);

}
