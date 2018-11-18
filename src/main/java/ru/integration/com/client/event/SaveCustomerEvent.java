package ru.integration.com.client.event;

import com.google.gwt.event.shared.GwtEvent;
import ru.integration.com.common.model.Customer;

/**
 * Created by eljah32 on 11/17/2018.
 */
public class SaveCustomerEvent extends GwtEvent<SaveCustomerEventHandler> {

    public static Type<SaveCustomerEventHandler> TYPE = new Type<SaveCustomerEventHandler>();

    /**
     * todo Id
     */
    Customer customer;

    public Customer getCustomer() {
        return customer;
    }

    public SaveCustomerEvent(Customer t) {
        this.customer = t;
    }

    @Override
    protected void dispatch(SaveCustomerEventHandler handler) {
        handler.onSaveCustomerEventHandler(this);
    }

    @Override
    public Type<SaveCustomerEventHandler> getAssociatedType() {
        return TYPE;
    }

}
