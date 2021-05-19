package com.simon.rico.afterpaytest.transactionagent.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.simon.rico.afterpaytest.transactionagent.dto.TransferDto;
import com.simon.rico.afterpaytest.transactionagent.model.Transfer;
import com.simon.rico.afterpaytest.transactionagent.services.TransferAgentService;

import javax.validation.Valid;


@RequestMapping(value = "/api/v1/transfers")
@RestController
public class TransferController {

    @Autowired
    private TransferAgentService transferAgentService;


    @PostMapping
    public Transfer createTransfer(@Valid @RequestBody TransferDto transferDto) {
        return transferAgentService.makeTransfer(transferDto);
    }
}
