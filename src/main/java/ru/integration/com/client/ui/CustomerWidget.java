package ru.integration.com.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Widget;
import ru.integration.com.client.ui.component.ImageButton;
import ru.integration.com.common.model.Customer;

/**
 * Created by eljah32 on 11/17/2018.
 */
public class CustomerWidget extends Composite {

    private static CustomerWidgetUiBinder uiBinder = GWT
            .create(CustomerWidgetUiBinder.class);

    interface CustomerWidgetUiBinder extends UiBinder<Widget, CustomerWidget> {
    }

    @UiField
    ImageButton editButton;

    @UiField
    ImageButton deleteButton;

    @UiField
    InlineLabel nameBox;

    @UiField
    InlineLabel locationBox;

    @UiField
    InlineLabel phoneNumberBox;

    @UiField
    InlineLabel nodeId;

    private Customer currentCustomer;

    private SimpleEventBus _eventBus;

    public CustomerWidget() {
        initWidget(uiBinder.createAndBindUi(this));
    }

    public CustomerWidget(Customer t, SimpleEventBus eventBus) {
        _eventBus = eventBus;
        // init display
        initWidget(uiBinder.createAndBindUi(this));
        this.currentCustomer = t;
        // format display
        nameBox.setText(t.getName());
        locationBox.setText(t.getLocation());
        phoneNumberBox.setText(t.getPhoneNumber()+"");
    }

}
