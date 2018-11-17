package ru.integration.com.common.model;

/**
 * Created by eljah32 on 11/17/2018.
 */
public class Customer {
    String name;
    String location;
    String nodeId;
    String phoneNumber;

    private static final long serialVersionUID = -5744307016899515615L;

    public Customer()
    {}

    public Customer(String name, String location, String nodeId, String phoneNumber)
    {
        this.name=name;
        this.location=location;
        this.nodeId=nodeId;
        this.phoneNumber=phoneNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
