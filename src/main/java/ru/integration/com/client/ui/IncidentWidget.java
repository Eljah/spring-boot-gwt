package ru.integration.com.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Widget;
import ru.integration.com.common.model.Customer;
import ru.integration.com.common.model.Incident;

import java.util.Date;

/**
 * Created by eljah32 on 11/18/2018.
 */
public class IncidentWidget extends Composite {
    public IncidentWidget() {
        initWidget(uiBinder.createAndBindUi(this));
    }

    public IncidentWidget(Incident t, SimpleEventBus eventBus) {
        _eventBus = eventBus;
        // init display
        initWidget(uiBinder.createAndBindUi(this));
        this.currentIncident = t;
    }

    private static IncidentWidgetUiBinder uiBinder = GWT
            .create(IncidentWidgetUiBinder.class);

    interface IncidentWidgetUiBinder extends UiBinder<Widget, IncidentWidget> {
    }

    private Incident currentIncident;

    private SimpleEventBus _eventBus;


    @UiField
    InlineLabel incidentStartedTime;

    public void setUpStartDate()
    {
        incidentStartedTime.setText(currentIncident.getIncedentStart().toLocaleString());
    }

    @UiField
    InlineLabel incidentPhoneNumber;

    public void setIncidentPhoneNumber()
    {
        incidentPhoneNumber.setText(currentIncident.getPhoneNumber());
    }

}
