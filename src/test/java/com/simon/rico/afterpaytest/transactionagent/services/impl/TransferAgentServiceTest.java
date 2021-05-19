package com.simon.rico.afterpaytest.transactionagent.services.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.simon.rico.afterpaytest.transactionagent.dto.AccountDto;
import com.simon.rico.afterpaytest.transactionagent.dto.TransferDto;
import com.simon.rico.afterpaytest.transactionagent.model.Account;
import com.simon.rico.afterpaytest.transactionagent.model.TransferStatus;
import com.simon.rico.afterpaytest.transactionagent.model.TransferType;
import com.simon.rico.afterpaytest.transactionagent.services.BankRepository;
import com.simon.rico.afterpaytest.transactionagent.services.TransferService;

@SpringBootTest
public class TransferAgentServiceTest {
    @Autowired
    private BankRepository bankRepository;


    @Mock
    Map<String, TransferService> transferServices;


    private IntraBankTransferServiceImpl intraBankTransferService;


    private InterBankTransferServiceImpl interBankTransferService;

    @InjectMocks
    private TransferAgentServiceImpl transferAgentService;

    @Test
    public void testMakeInterTransfer() {

        transferAgentService = new TransferAgentServiceImpl(transferServices);
        TransferDto transferDto = new TransferDto();

        AccountDto source = new AccountDto();
        source.setBankId("TEST_BANK_A");
        source.setCustomerId("CUSTOMER_A");
        source.setAccountId("ACCOUNT_A");

        AccountDto destination = new AccountDto();
        destination.setBankId("TEST_BANK_B");
        destination.setCustomerId("CUSTOMER_AB");
        destination.setAccountId("ACCOUNT_AB");
        transferDto.setTransferType(TransferType.INTER_BANK);
        transferDto.setOrigin(source);
        transferDto.setAmount(2);
        transferDto.setDestination(destination);


        interBankTransferService = new InterBankTransferServiceImpl(bankRepository);
        InterBankTransferServiceImpl spyInterBankTransferService = Mockito.spy(interBankTransferService);

        Random random = new Random();
        // tests probability of fail and success for inter bank transfer.
        when(spyInterBankTransferService.getRandomResult()).thenReturn(random.nextDouble() * (30.0));
        when(transferServices.get(TransferType.INTER_BANK.name())).thenReturn(spyInterBankTransferService);
        assertEquals(TransferStatus.FAILED, transferAgentService.makeTransfer(transferDto).getTransferStatus());

        when(spyInterBankTransferService.getRandomResult()).thenReturn(random.nextDouble() * (100.0 + 31.0) + 31.0);
        assertEquals(TransferStatus.SUCCESSFUL, transferAgentService.makeTransfer(transferDto).getTransferStatus());

        assertEquals(5, transferAgentService.makeTransfer(transferDto).getCommission());
        Exception exception = assertThrows(RuntimeException.class, () -> {
            transferDto.setAmount(1001);
            transferAgentService.makeTransfer(transferDto);
        });

        String expectedMessage = "Amount to transfer is higher than the limit for INTER_BANK transactions";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));

    }

    @Test
    public void testMakeIntraTransfer() {

        transferAgentService = new TransferAgentServiceImpl(transferServices);
        TransferDto transferDto = new TransferDto();

        AccountDto source = new AccountDto();
        source.setBankId("TEST_BANK_A");
        source.setCustomerId("CUSTOMER_A");
        source.setAccountId("ACCOUNT_A");

        AccountDto destination = new AccountDto();
        destination.setBankId("TEST_BANK_A");
        destination.setCustomerId("CUSTOMER_B");
        destination.setAccountId("ACCOUNT_BA");
        transferDto.setTransferType(TransferType.INTRA_BANK);
        transferDto.setOrigin(source);
        transferDto.setAmount(2);
        transferDto.setDestination(destination);


        intraBankTransferService = new IntraBankTransferServiceImpl(bankRepository);
        IntraBankTransferServiceImpl spyInterBankTransferService = Mockito.spy(intraBankTransferService);

        Random random = new Random();
        //Tests always successful.
        when(spyInterBankTransferService.getRandomResult()).thenReturn(random.nextDouble() * (100.0));
        when(transferServices.get(TransferType.INTRA_BANK.name())).thenReturn(spyInterBankTransferService);
        assertEquals(TransferStatus.SUCCESSFUL, transferAgentService.makeTransfer(transferDto).getTransferStatus());
        assertEquals(0, transferAgentService.makeTransfer(transferDto).getCommission());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            transferDto.setAmount(1001);
            transferAgentService.makeTransfer(transferDto);
        });

        String expectedMessage = "Not enough funds in account ACCOUNT_A to make the transfer";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));

    }

}
