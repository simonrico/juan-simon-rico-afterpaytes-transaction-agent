package com.simon.rico.afterpaytest.transactionagent.services;

import com.simon.rico.afterpaytest.transactionagent.dto.AccountDto;
import com.simon.rico.afterpaytest.transactionagent.model.Transfer;

public interface TransferService {
    Transfer transfer(AccountDto source, AccountDto destination, long amount);
}
