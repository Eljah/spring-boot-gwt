package ru.integration.com.common.model;

import java.util.Date;
import java.util.List;

/**
 * Created by eljah32 on 11/18/2018.
 */
public class Incident {
    String description;
    String phoneNumber;
    Date incedentStart;
    Date conversationEnd;
    List<Long> nodesAffected;
    List<Customer> cutomersAffected;
    Date reactionStart;
    Date reactionEnd;
    Date informingStart;
    Date informingEnd;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Date getIncedentStart() {
        return incedentStart;
    }

    public void setIncedentStart(Date incedentStart) {
        this.incedentStart = incedentStart;
    }

    public Date getConversationEnd() {
        return conversationEnd;
    }

    public void setConversationEnd(Date conversationEnd) {
        this.conversationEnd = conversationEnd;
    }

    public List<Long> getNodesAffected() {
        return nodesAffected;
    }

    public void setNodesAffected(List<Long> nodesAffected) {
        this.nodesAffected = nodesAffected;
    }

    public List<Customer> getCutomersAffected() {
        return cutomersAffected;
    }

    public void setCutomersAffected(List<Customer> cutomersAffected) {
        this.cutomersAffected = cutomersAffected;
    }

    public Date getReactionStart() {
        return reactionStart;
    }

    public void setReactionStart(Date reactionStart) {
        this.reactionStart = reactionStart;
    }

    public Date getReactionEnd() {
        return reactionEnd;
    }

    public void setReactionEnd(Date reactionEnd) {
        this.reactionEnd = reactionEnd;
    }

    public Date getInformingStart() {
        return informingStart;
    }

    public void setInformingStart(Date informingStart) {
        this.informingStart = informingStart;
    }

    public Date getInformingEnd() {
        return informingEnd;
    }

    public void setInformingEnd(Date informingEnd) {
        this.informingEnd = informingEnd;
    }
}
