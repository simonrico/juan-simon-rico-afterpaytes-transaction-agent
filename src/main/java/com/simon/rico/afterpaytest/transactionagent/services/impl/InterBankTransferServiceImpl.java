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

@Component("INTER_BANK")
public class InterBankTransferServiceImpl extends AbstractTransferService implements TransferService {

    private BankRepository bankRepository;

    private static final long COMMISSION = 5;
    private static final long TRANSFER_LIMIT = 1000;
    private static final double CHANCE_OF_FAILURE = 30.0;

    public InterBankTransferServiceImpl(BankRepository bankRepository) {
        this.bankRepository = bankRepository;
    }
    @Override
    public Transfer transfer(AccountDto source, AccountDto destination, long amount) {
        Account account1 = obtainAccount(source, bankRepository);
        Account account2 = obtainAccount(destination, bankRepository);

        if (StringUtils.equals(source.getBankId(), destination.getBankId())) {
            throw new TransferException("Invalid transfer type");
        }
        if (amount > TRANSFER_LIMIT) {
            throw new TransferException("Amount to transfer is higher than the limit for INTER_BANK transactions");
        }


        return executeTransfer(account1, account2, amount, COMMISSION);
    }

    @Override
    protected TransferStatus getTransferResult() {
        double randomValue = getRandomResult();
        return randomValue <= CHANCE_OF_FAILURE ? TransferStatus.FAILED : TransferStatus.SUCCESSFUL;
    }
    @Override
    protected double getRandomResult() {
        return Math.random()*100;
    }
}
