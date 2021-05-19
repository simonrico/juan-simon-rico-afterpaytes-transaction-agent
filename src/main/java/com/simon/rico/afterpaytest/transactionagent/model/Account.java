package com.simon.rico.afterpaytest.transactionagent.model;

import java.util.ArrayList;
import java.util.List;

public class Account {
    private String accountId;
    private long balance;
    private List<Transfer> transfers;

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public long getBalance() {
        return balance;
    }

    public void setBalance(long balance) {
        this.balance = balance;
    }

    public List<Transfer> getTransfers() {
        return transfers;
    }

    public void setTransfers(List<Transfer> transfers) {
        this.transfers = transfers;
    }

    public void addTransfer(Transfer transfer) {
        if(transfers == null) {
            transfers = new ArrayList<>();
        }
        transfers.add(transfer);
    }
}
