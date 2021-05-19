package com.simon.rico.afterpaytest.transactionagent.services.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.simon.rico.afterpaytest.transactionagent.dto.AccountDto;
import com.simon.rico.afterpaytest.transactionagent.exceptions.TransferException;
import com.simon.rico.afterpaytest.transactionagent.model.Account;
import com.simon.rico.afterpaytest.transactionagent.model.Transfer;
import com.simon.rico.afterpaytest.transactionagent.model.TransferStatus;
import com.simon.rico.afterpaytest.transactionagent.services.BankRepository;
import com.simon.rico.afterpaytest.transactionagent.services.TransferService;

@Component("INTRA_BANK")
public class IntraBankTransferServiceImpl extends AbstractTransferService implements TransferService {

    private BankRepository bankRepository;

    public IntraBankTransferServiceImpl(BankRepository bankRepository) {
        this.bankRepository = bankRepository;
    }

    @Override
    public Transfer transfer(AccountDto source, AccountDto destination, long amount) {
        if (!StringUtils.equals(source.getBankId(), destination.getBankId())) {
            throw new TransferException("Invalid transfer type");
        }
        Account account1 = obtainAccount(source, bankRepository);
        Account account2 = obtainAccount(destination, bankRepository);
        return executeTransfer(account1, account2, amount, 0);

    }

    protected TransferStatus getTransferResult() {
        return TransferStatus.SUCCESSFUL;
    }

    @Override
    protected double getRandomResult() {
        return 0;
    }
}
