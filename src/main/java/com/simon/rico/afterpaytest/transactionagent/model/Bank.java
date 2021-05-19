package com.simon.rico.afterpaytest.transactionagent.model;

import java.util.Map;

public class Bank {

    private String bankId;

    private Map<String, Customer> customers;

    public String getBankId() {
        return bankId;
    }

    public void setBankId(String bankId) {
        this.bankId = bankId;
    }

    public Map<String, Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(
            Map<String, Customer> customers) {
        this.customers = customers;
    }
}
