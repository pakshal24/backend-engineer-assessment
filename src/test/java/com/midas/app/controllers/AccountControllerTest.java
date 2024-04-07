package com.midas.app.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import com.midas.app.models.Account;
import com.midas.app.services.AccountService;
import com.midas.generated.model.AccountDto;
import com.midas.generated.model.CreateAccountDto;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class AccountControllerTest {

  @Mock private AccountService accountService;

  @Mock private Logger logger;

  @InjectMocks private AccountController accountController;

  public AccountControllerTest() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  public void testCreateUserAccount() {
    // Arrange
    CreateAccountDto createAccountDto = new CreateAccountDto();
    createAccountDto.setFirstName("John");
    createAccountDto.setLastName("Doe");
    createAccountDto.setEmail("john.doe@example.com");

    Account createdAccount = new Account();
    UUID accountId = UUID.randomUUID();
    createdAccount.setId(accountId);
    createdAccount.setFirstName(createAccountDto.getFirstName());
    createdAccount.setLastName(createAccountDto.getLastName());
    createdAccount.setEmail(createAccountDto.getEmail());

    when(accountService.createAccount(any(Account.class))).thenReturn(createdAccount);

    // Act
    ResponseEntity<AccountDto> responseEntity =
        accountController.createUserAccount(createAccountDto);

    // Assert
    assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
    AccountDto accountDto = responseEntity.getBody();
    System.out.println(accountDto.getId());
    assertEquals(accountId.toString(), accountDto.getId().toString());
    assertEquals(createAccountDto.getFirstName(), accountDto.getFirstName());
    assertEquals(createAccountDto.getLastName(), accountDto.getLastName());
    assertEquals(createAccountDto.getEmail(), accountDto.getEmail());
  }
}
