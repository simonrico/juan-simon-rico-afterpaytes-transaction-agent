package com.simon.rico.afterpaytest.transactionagent.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import com.simon.rico.afterpaytest.transactionagent.model.TransferType;

public class TransferDto {
    @NotNull
    private AccountDto origin;
    @NotNull
    private AccountDto destination;
    @NotNull
    private TransferType transferType;
    @Positive
    private long amount;

    public void setTransferType(TransferType transferType) {
        this.transferType = transferType;
    }

    public AccountDto getOrigin() {
        return origin;
    }

    public void setOrigin(AccountDto origin) {
        this.origin = origin;
    }

    public AccountDto getDestination() {
        return destination;
    }

    public void setDestination(AccountDto destination) {
        this.destination = destination;
    }

    public TransferType getTransferType() {
        return transferType;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }
}

