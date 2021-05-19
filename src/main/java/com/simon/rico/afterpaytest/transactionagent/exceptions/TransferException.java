package com.simon.rico.afterpaytest.transactionagent.exceptions;

public class TransferException extends RuntimeException {

    public TransferException(String errorMessage) {
        super(errorMessage);
    }

}