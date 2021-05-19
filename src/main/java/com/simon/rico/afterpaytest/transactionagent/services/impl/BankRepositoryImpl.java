package com.simon.rico.afterpaytest.transactionagent.services.impl;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.simon.rico.afterpaytest.transactionagent.model.Account;
import com.simon.rico.afterpaytest.transactionagent.model.Bank;
import com.simon.rico.afterpaytest.transactionagent.services.BankRepository;
@Component
public class BankRepositoryImpl implements BankRepository {


    private Map<String, Bank> banks;

    @PostConstruct
    public void init() throws IOException {

        ObjectMapper jsonMapper = new ObjectMapper();
        banks = jsonMapper.readValue(getClass().getResourceAsStream("/data.json"), new TypeReference<Map<String, Bank>>() {
        });

    }

    @Override
    public Bank findById(String bankId) {
        return banks.get(bankId);
    }

    @Override public Account findByBankIdAndCustomerIdAndAccountId(String bankId, String customerId, String accountId) {
        return  Optional.ofNullable(banks.get(bankId))
                .map( b -> b.getCustomers().get(customerId))
                .map(c -> c.getAccounts().get(accountId)).orElse(null);
    }
}
