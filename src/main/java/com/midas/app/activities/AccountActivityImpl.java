package com.midas.app.activities;

import com.midas.app.models.Account;
import com.midas.app.models.Account.ProviderType;
import com.midas.app.providers.external.stripe.StripeConfiguration;
import com.midas.app.providers.payment.CreateAccount;
import com.midas.app.providers.payment.PaymentProvider;
import com.midas.app.repositories.AccountRepository;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import com.stripe.param.CustomerUpdateParams;
import jakarta.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;

@RequiredArgsConstructor
public class AccountActivityImpl implements AccountActivity {

  private final AccountRepository accountRepository;
  private final PaymentProvider paymentProvider;
  private final StripeConfiguration configuration;

  @Override
  @Transactional
  public Account saveAccount(Account account) {

    return accountRepository.save(account);
  }

  @Override
  public Account updateAccount(String accountId, Account account) {
    Stripe.apiKey = configuration.getApiKey();
    Optional<Account> response = accountRepository.findById(UUID.fromString(accountId));

    if (response.isPresent()) {
      Account update = response.get();

      if (StringUtils.hasLength(account.getFirstName())) {
        update.setFirstName(account.getFirstName());
      }
      if (StringUtils.hasLength(account.getLastName())) {
        update.setLastName(account.getLastName());
      }
      if (StringUtils.hasLength(account.getEmail())) {
        update.setEmail(account.getEmail());
      }
      Account updatedAc = accountRepository.save(update);

      try {
        CustomerUpdateParams params =
            CustomerUpdateParams.builder()
                .setEmail(updatedAc.getEmail())
                .setName(updatedAc.getFirstName() + " " + updatedAc.getLastName())
                .build();

        Customer customer = Customer.retrieve(updatedAc.getProviderId());
        customer.update(params);
      } catch (StripeException e) {
        throw new RuntimeException(e);
      }

      return updatedAc;
    }

    return null;
  }

  @Override
  public Account createPaymentAccount(Account account) {
    String response =
        paymentProvider.createAccount(
            CreateAccount.builder()
                .email(account.getEmail())
                .userId(account.getId().toString())
                .firstName(account.getFirstName())
                .lastName(account.getLastName())
                .build());
    ProviderType providerType = ProviderType.STRIPE;
    account.setProviderType(providerType);
    account.setProviderId(response);
    return saveAccount(account);
  }
}
