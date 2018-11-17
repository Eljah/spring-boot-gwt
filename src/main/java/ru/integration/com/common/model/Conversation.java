package ru.integration.com.common.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by eljah32 on 11/17/2018.
 */
public class Conversation implements Serializable{
    String description;
    Date conversationStart;
    Date conversationEnd;
    List<Long> nodesAffected;
    List<Customer> cutomersAffected;
    Date reactionStart;
    Date reactionEnd;
    Date informingStart;
    Date informingEnd;

}
