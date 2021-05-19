package com.simon.rico.afterpaytest.transactionagent.services;

import com.simon.rico.afterpaytest.transactionagent.model.Account;
import com.simon.rico.afterpaytest.transactionagent.model.Bank;

public interface BankRepository {

    Bank findById(String bankId);

    Account findByBankIdAndCustomerIdAndAccountId(String bankId, String customerId, String accountId);
}
