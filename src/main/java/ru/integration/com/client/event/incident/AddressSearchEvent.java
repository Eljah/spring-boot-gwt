package ru.integration.com.client.event.incident;

import com.google.gwt.event.shared.GwtEvent;
import ru.integration.com.common.model.Customer;
import ru.integration.com.common.model.Incident;

import java.util.List;

/**
 * Created by eljah32 on 11/18/2018.
 */
public class AddressSearchEvent extends GwtEvent<AddressSearchEventHandler> {
    public static GwtEvent.Type<AddressSearchEventHandler> TYPE = new GwtEvent.Type<AddressSearchEventHandler>();

    /**
     * todo title
     */
    private String typedAddress;
    private List<String> listOfNodes;
    private List<Customer> listOfCustomers;

    public List<String> getListOfNodes() {
        return listOfNodes;
    }

    public List<Customer> getListOfCustomers() {
        return listOfCustomers;
    }

    public AddressSearchEvent(String typedAddress) {
        this.typedAddress=typedAddress;
    }

    public String getTypedAddress() {
        return typedAddress;
    }

    @Override
    protected void dispatch(AddressSearchEventHandler handler) {
        handler.onAddressSearchEventHandler(this);
    }

    @Override
    public com.google.gwt.event.shared.GwtEvent.Type<AddressSearchEventHandler> getAssociatedType() {
        return TYPE;
    }
}
