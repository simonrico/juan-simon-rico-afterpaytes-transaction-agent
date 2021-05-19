package com.simon.rico.afterpaytest.transactionagent.services.impl;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.simon.rico.afterpaytest.transactionagent.dto.TransferDto;
import com.simon.rico.afterpaytest.transactionagent.model.Transfer;
import com.simon.rico.afterpaytest.transactionagent.services.TransferAgentService;
import com.simon.rico.afterpaytest.transactionagent.services.TransferService;

@Component
public class TransferAgentServiceImpl implements TransferAgentService {

    private final Map<String, TransferService> transferServices;


    public TransferAgentServiceImpl(Map<String, TransferService> transferServices) {
        this.transferServices = transferServices;
    }

    @Override
    public Transfer makeTransfer(TransferDto transferDto) {
        return transferServices.get(transferDto.getTransferType().name()).transfer(transferDto.getOrigin(), transferDto.getDestination(), transferDto.getAmount());
    }
}
