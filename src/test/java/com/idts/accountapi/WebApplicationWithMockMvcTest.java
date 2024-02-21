package com.idts.accountapi;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.idts.accountapi.customHandler.AccountNotFoundException;
import com.idts.accountapi.dao.AccountRepository;
import com.idts.accountapi.dao.TransactionRequest;
import com.idts.accountapi.dao.UserRepository;
import com.idts.accountapi.model.Account;
import com.idts.accountapi.model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.util.Objects;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class WebApplicationWithMockMvcTest {
    private static final String COULD_NOT_FIND_ACCOUNT_MESSAGE = "Could not find account: ";
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private UserRepository userRepository;

    private MockHttpServletResponse response;

    @Test
    public void getAllUsersTest() throws Exception {
        response = this.mockMvc.perform(get("/api/users/getAll"))
                .andDo(print())
                .andReturn().getResponse();

        Assert.isTrue(Objects.requireNonNull(response.getContentType()).contains("hal"),
                "The response is not producing hypermedia");
        Assert.isNull(response.getErrorMessage(), "Expected error message to be null");
        Assert.isTrue(response.getStatus() == 200, "The expected response was 200");
    }

    @Test
    public void createAccountForUserTest() throws Exception {
        String validUsername = "Poul";
        String accountName = "MoneyTrees";

        response = this.mockMvc.perform(get("/api/users/getByUsername/" + validUsername))
                .andReturn().getResponse();

        Assert.isTrue(response.getStatus() == 200, "Username could not be retrieved.");

        response = this.mockMvc.perform(post("/api/accounts/newAccount/" + validUsername + "/" + accountName))
                .andReturn().getResponse();

        Assert.isTrue(response.getContentAsString().contains(accountName),
                "Unexpected error creating account: " + accountName + " for user: " + validUsername);
    }

    @Test
    @Commit
    public void createTransactionFromTransferOfFundsTest() throws Exception {
        User user = new User("Test User");

        userRepository.save(user);

        Account fromAccount = new Account(1L, "fromAccount", BigDecimal.ZERO, user);
        Account toAccount = new Account(2L, "toAccount", BigDecimal.ZERO, user);

        accountRepository.save(fromAccount);
        accountRepository.save(toAccount);

        fromAccount.updateBalance(BigDecimal.valueOf(100));
        Assert.isTrue(fromAccount.getBalance().compareTo(BigDecimal.valueOf(100)) == 0,
                "The balance of the sending account is not correct.");

        accountRepository.save(fromAccount);

        TransactionRequest transactionRequest = new TransactionRequest();
        transactionRequest.setFromAccountId(fromAccount.getId());
        transactionRequest.setToAccountId(toAccount.getId());
        transactionRequest.setAmountToTransfer(BigDecimal.valueOf(50));

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonRequest = objectMapper.writeValueAsString(transactionRequest);

        response = this.mockMvc.perform(put("/api/transactions/transferFunds")
                        .content(jsonRequest)
                        .contentType(MediaType.APPLICATION_JSON))
                        .andDo(print())
                        .andReturn().getResponse();

        Assert.isTrue(response.getStatus() == 201, "The transfer of funds was not successful");

        Assert.isTrue(response.getContentAsString().contains("timestamp"), "Timestamp not found in the transaction");

        Assert.isTrue(response.getContentAsString().contains("amountToTransfer"),
                "Amount to transfer not saved in the transaction");

    }

    @Test
    public void getInvalidAccountTest() throws Exception {
        long invalidAccountId = 100L;
        response = this.mockMvc.perform(get("/api/accounts/getById/" + invalidAccountId))
                .andDo(print())
                .andReturn().getResponse();

        Assert.isTrue(response.getStatus() == 404, "Expected a 404, not found response.");
        Assert.isTrue(response.getContentAsString().equals(COULD_NOT_FIND_ACCOUNT_MESSAGE + invalidAccountId),
                "Expected a custom message from: " + AccountNotFoundException.class.getSimpleName() + " handler.");
    }
}
