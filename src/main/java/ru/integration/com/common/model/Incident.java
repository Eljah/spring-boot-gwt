package ru.integration.com.common.model;

import java.util.Date;
import java.util.List;

/**
 * Created by eljah32 on 11/18/2018.
 */
public class Incident {
    String description;
    Date incedentStart;
    Date conversationEnd;
    List<Long> nodesAffected;
    List<Customer> cutomersAffected;
    Date reactionStart;
    Date reactionEnd;
    Date informingStart;
    Date informingEnd;
}
