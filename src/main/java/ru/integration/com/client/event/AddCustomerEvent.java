package ru.integration.com.client.event;

import com.google.gwt.event.shared.GwtEvent;
import ru.integration.com.common.model.Customer;

/**
 * Created by eljah32 on 11/17/2018.
 */
public class AddCustomerEvent extends GwtEvent<AddCustomerEventHandler> {

    public static Type<AddCustomerEventHandler> TYPE = new Type<AddCustomerEventHandler>();

    /**
     * todo title
     */
    private Customer _customer;

    public Customer getCustomer() {
        return _customer;
    }

    public AddCustomerEvent(Customer customer) {
        _customer = customer;
    }

    @Override
    protected void dispatch(AddCustomerEventHandler handler) {
        handler.onAddCustomerEventHandler(this);
    }

    @Override
    public com.google.gwt.event.shared.GwtEvent.Type<AddCustomerEventHandler> getAssociatedType() {
        return TYPE;
    }

}
