package ru.integration.com.client.event.incident;

import com.google.gwt.event.shared.GwtEvent;
import ru.integration.com.common.model.Incident;

/**
 * Created by eljah32 on 11/22/2018.
 */
public class RepairCallEvent extends GwtEvent<RepairCallEventHandler> {
    public static GwtEvent.Type<RepairCallEventHandler> TYPE = new GwtEvent.Type<RepairCallEventHandler>();

    /**
     * todo title
     */
    private Incident incident;

    public Incident getIncident() {
        return incident;
    }

    public RepairCallEvent(Incident incident) {
        this.incident = incident;
    }

    @Override
    protected void dispatch(RepairCallEventHandler handler) {
        handler.onRepairCallEventHandler(this);
    }

    @Override
    public com.google.gwt.event.shared.GwtEvent.Type<RepairCallEventHandler> getAssociatedType() {
        return TYPE;
    }

}
