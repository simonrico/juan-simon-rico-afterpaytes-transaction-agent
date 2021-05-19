package com.simon.rico.afterpaytest.transactionagent.controllers;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.google.gson.Gson;
import com.simon.rico.afterpaytest.transactionagent.SimonRicoAfterpaytestApplication;
import com.simon.rico.afterpaytest.transactionagent.dto.AccountDto;
import com.simon.rico.afterpaytest.transactionagent.dto.TransferDto;
import com.simon.rico.afterpaytest.transactionagent.model.TransferType;

@SpringBootTest(classes = SimonRicoAfterpaytestApplication.class)
@WebAppConfiguration
public class TransferAgentControllerTest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    public void setUp() {
        this.mvc = MockMvcBuilders.webAppContextSetup(this.context).build();
    }

    @Test
    public void testInterBankTransfer() throws Exception {
        setUp();
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
        Gson gson = new Gson();
        String json = gson.toJson(transferDto);
        this.mvc.perform(post("/api/v1/transfers").contentType(MediaType.APPLICATION_JSON).content(json)).andExpect(content().string(containsString("\"commission\":5")));
    }

    @Test
    public void testIntraBankTransfer() throws Exception {
        setUp();
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
        Gson gson = new Gson();
        String json = gson.toJson(transferDto);
        this.mvc.perform(post("/api/v1/transfers").contentType(MediaType.APPLICATION_JSON).content(json)).andExpect(content().string(("{\"sourceAccount\":\"ACCOUNT_A\",\"destinationAccount\":\"ACCOUNT_BA\",\"amount\":2,\"commission\":0,\"transferStatus\":\"SUCCESSFUL\"}")));
    }
}
