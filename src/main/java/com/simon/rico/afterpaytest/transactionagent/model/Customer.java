package com.simon.rico.afterpaytest.transactionagent.model;

import java.util.Map;

public class Customer {

    private String customerId;
    private Map<String, Account> accounts;

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public Map<String, Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(Map<String, Account> accounts) {
        this.accounts = accounts;
    }
}
