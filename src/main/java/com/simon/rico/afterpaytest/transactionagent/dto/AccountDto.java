package com.simon.rico.afterpaytest.transactionagent.dto;

import javax.validation.constraints.NotNull;

public class AccountDto {
    @NotNull
    private String accountId;
    @NotNull
    private String bankId;
    @NotNull
    private String customerId;

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getBankId() {
        return bankId;
    }

    public void setBankId(String bankId) {
        this.bankId = bankId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }
}

