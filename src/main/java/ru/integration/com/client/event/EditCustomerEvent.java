package ru.integration.com.client.event;

import com.google.gwt.event.shared.GwtEvent;
import ru.integration.com.common.model.Customer;

/**
 * Created by eljah32 on 11/17/2018.
 */
public class EditCustomerEvent extends GwtEvent<EditCustomerEventHandler> {

    public static Type<EditCustomerEventHandler> TYPE = new Type<EditCustomerEventHandler>();

    /**
     * todo Id
     */
    Customer customer;

    public Customer getCustomer() {
        return customer;
    }

    public EditCustomerEvent(Customer t) {
        this.customer = t;
    }

    @Override
    protected void dispatch(EditCustomerEventHandler handler) {
        handler.onEditCustomerEventHandler(this);
    }

    @Override
    public com.google.gwt.event.shared.GwtEvent.Type<EditCustomerEventHandler> getAssociatedType() {
        return TYPE;
    }

}
