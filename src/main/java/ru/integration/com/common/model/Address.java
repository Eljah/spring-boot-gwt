package ru.integration.com.common.model;

import java.util.List;

/**
 * Created by eljah32 on 11/18/2018.
 */
public class Address {
    String typedAddress;
    List<String> nodes;
    List<Customer> customers;

    public String getTypedAddress() {
        return typedAddress;
    }

    public void setTypedAddress(String typedAddress) {
        this.typedAddress = typedAddress;
    }

    public List<String> getNodes() {
        return nodes;
    }

    public void setNodes(List<String> nodes) {
        this.nodes = nodes;
    }

    public List<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
    }
}
