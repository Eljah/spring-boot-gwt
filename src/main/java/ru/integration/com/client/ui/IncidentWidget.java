package ru.integration.com.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.*;
import org.gwtopenmaps.openlayers.client.MapWidget;
import ru.integration.com.client.event.EditCustomerEvent;
import ru.integration.com.client.event.incident.AddressSearchEvent;
import ru.integration.com.client.event.incident.RepairCallEvent;
import ru.integration.com.client.ui.component.ImageButton;
import ru.integration.com.client.ui.component.MapPanel;
import ru.integration.com.common.model.Address;
import ru.integration.com.common.model.Customer;
import ru.integration.com.common.model.Incident;

import java.util.Date;
import java.util.stream.Collectors;
import java.util.stream.Stream;


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

    @UiField
    InlineLabel incidentConversationEndTime;

    public void populateFieldsFromObject()
    {
        incidentStartedTime.setText(currentIncident.getIncedentStart().toLocaleString());
        incidentPhoneNumber.setText(currentIncident.getPhoneNumber());
        descriptionText.setText(currentIncident.getDescription());
        nodesLabel.setText(currentIncident.getNodesAffected().stream().map(Object::toString).collect(Collectors.joining(", ")));
        customersLabel.setText(currentIncident.getCutomersAffected().stream().map(Customer::getName).collect(Collectors.joining(", ")));
        incidentConversationEndTime.setText(currentIncident.getConversationEnd().toLocaleString());
    }

    @UiField
    MapPanel map;

    @UiField
    TextArea descriptionText;

    @UiField
    ImageButton searchAddressButton;

    @UiField
    TextBox searchAddressBox;

    @UiHandler("searchAddressButton")
    void onSearchAddressButtonClick(ClickEvent e) {
        // show confirm popup
        // ask controller for delete
        _eventBus.fireEvent(new AddressSearchEvent(searchAddressBox.getText()));
    }


    @UiField
    ImageButton repairCallButton;

    @UiHandler("repairCallButton")
    void onRepairCallButtonClick(ClickEvent e) {
        // show confirm popup
        // ask controller for delete
        currentIncident.setDescription(descriptionText.getText());
        _eventBus.fireEvent(new RepairCallEvent(currentIncident));

    }


    @UiField
    InlineLabel typedAddressLabel;

    @UiField
    InlineLabel nodesLabel;

    @UiField
    InlineLabel customersLabel;


    public void setAddressSearchResults(Address address)
    {
        currentIncident.setNodesAffected(address.getNodes());
        currentIncident.setCutomersAffected(address.getCustomers());
        typedAddressLabel.setText(address.getTypedAddress());
        nodesLabel.setText(address.getNodes().stream().map(Object::toString).collect(Collectors.joining(", ")));
        customersLabel.setText(address.getCustomers().stream().map(Customer::getName).collect(Collectors.joining(", ")));
    }

    public void fakePointShow()
    {
        map.showFakeObjects();
    }

}
