package ru.integration.com.client.event.incident;

import com.google.gwt.event.shared.GwtEvent;
import ru.integration.com.client.event.AddCustomerEventHandler;

import java.util.Date;

/**
 * Created by eljah32 on 11/18/2018.
 */
public class NewIncidentEvent extends GwtEvent<NewIncidentEventHandler>{
    public static GwtEvent.Type<NewIncidentEventHandler> TYPE = new GwtEvent.Type<NewIncidentEventHandler>();

    /**
     * todo title
     */
    private Date startDate;

    public Date getStartDate() {
        return startDate;
    }

    public NewIncidentEvent(Date startDate) {
        this.startDate = startDate;
    }

    @Override
    protected void dispatch(NewIncidentEventHandler handler) {
        handler.onNewIncidentEventHandler(this);
    }

    @Override
    public com.google.gwt.event.shared.GwtEvent.Type<NewIncidentEventHandler> getAssociatedType() {
        return TYPE;
    }

}
