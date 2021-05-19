package com.simon.rico.afterpaytest.transactionagent.services.impl;

import com.simon.rico.afterpaytest.transactionagent.dto.AccountDto;
import com.simon.rico.afterpaytest.transactionagent.exceptions.TransferException;
import com.simon.rico.afterpaytest.transactionagent.model.Account;
import com.simon.rico.afterpaytest.transactionagent.model.Transfer;
import com.simon.rico.afterpaytest.transactionagent.model.TransferStatus;
import com.simon.rico.afterpaytest.transactionagent.services.BankRepository;

public abstract class AbstractTransferService {
    protected Account obtainAccount(AccountDto accountDto, BankRepository bankRepository) {
        Account account =  bankRepository
                .findByBankIdAndCustomerIdAndAccountId(accountDto.getBankId(),
                        accountDto.getCustomerId(), accountDto.getAccountId());
        if (account == null) {
            throw new TransferException(String.format("Account %s not found", accountDto.getAccountId()));
        }

        return account;
    }

    protected Transfer executeTransfer(Account source, Account destination, long amount, long commission) {
        Transfer transfer = new Transfer();
        transfer.setAmount(amount);
        transfer.setCommission(commission);
        transfer.setSourceAccount(source.getAccountId());
        transfer.setDestinationAccount(destination.getAccountId());
        transfer.setTransferStatus(getTransferResult());

        if(transfer.getTransferStatus().equals(TransferStatus.SUCCESSFUL)) {
            long newBalance = source.getBalance() - (amount + commission);

            if (newBalance < 0) {
                throw new TransferException(String.format("Not enough funds in account %s to make the transfer",
                        source.getAccountId()));
            }

            source.setBalance(newBalance);
            destination.setBalance(destination.getBalance() + amount);
            source.addTransfer(transfer);
            destination.addTransfer(transfer);
        }
        return transfer;

    }

    protected abstract TransferStatus getTransferResult();

    protected abstract double getRandomResult();
}
