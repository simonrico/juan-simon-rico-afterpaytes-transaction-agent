package com.simon.rico.afterpaytest.transactionagent.services;

import com.simon.rico.afterpaytest.transactionagent.dto.TransferDto;
import com.simon.rico.afterpaytest.transactionagent.model.Transfer;

public interface TransferAgentService {

    Transfer makeTransfer(TransferDto transferDto);
}
