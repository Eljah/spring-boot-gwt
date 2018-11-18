package ru.integration.com.client.event;

import com.google.gwt.event.shared.GwtEvent;
import ru.integration.com.common.model.Customer;
import ru.integration.com.common.model.Todo;

/**
 * Created by eljah32 on 11/17/2018.
 */
public class DeleteCustomerEvent extends GwtEvent<DeleteCustomerEventHandler> {

    public static Type<DeleteCustomerEventHandler> TYPE = new Type<DeleteCustomerEventHandler>();

    /**
     * todo Id
     */
    Customer customer;

    public Customer getCustomer() {
        return customer;
    }

    public DeleteCustomerEvent(Customer t) {
        this.customer = t;
    }

    @Override
    protected void dispatch(DeleteCustomerEventHandler handler) {
        handler.onDeleteCustomerEventHandler(this);
    }

    @Override
    public com.google.gwt.event.shared.GwtEvent.Type<DeleteCustomerEventHandler> getAssociatedType() {
        return TYPE;
    }

}

