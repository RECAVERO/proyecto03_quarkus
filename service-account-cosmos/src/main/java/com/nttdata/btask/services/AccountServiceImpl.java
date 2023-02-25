package com.nttdata.btask.services;

import com.nttdata.btask.interfaces.AccountService;
import com.nttdata.domain.contract.AccountRepository;
import com.nttdata.domain.models.AccountDto;
import com.nttdata.domain.models.TransferDto;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.vertx.core.json.JsonObject;

import javax.enterprise.context.ApplicationScoped;
import java.util.ArrayList;
import java.util.List;
@ApplicationScoped
public class AccountServiceImpl implements AccountService {
  private final AccountRepository accountRepository;
  private final redisServiceImpl redisService;

  public AccountServiceImpl(AccountRepository accountRepository, redisServiceImpl redisService) {
    this.accountRepository = accountRepository;
    this.redisService = redisService;
  }

  @Override
  public Multi<AccountDto> list() {
    List<JsonObject> lista = new ArrayList<>();
    return accountRepository.list().map(c->{
      return c;
    }).call(accountDto -> {
      JsonObject jsonCustomer = new JsonObject()
          .put("idTypeAccount", accountDto.getIdTypeAccount())
          .put("numberAccount", accountDto.getNumberAccount())
          .put("amount", accountDto.getAmount())
          .put("registrationDate", accountDto.getRegistrationDate())
          .put("idTypeCustomer", accountDto.getIdTypeCustomer())
          .put("nroDocument", accountDto.getNroDocument())
          .put("created_datetime", accountDto.getCreated_datetime())
          .put("updated_datetime", accountDto.getUpdated_datetime())
          .put("active", accountDto.getActive());
      lista.add(jsonCustomer);
      return redisService.redisAdd("listAccount",lista.toString());
    });
  }

  @Override
  public Multi<AccountDto> findByNroAccount(AccountDto accountDto) {
    return accountRepository.findByNroAccount(accountDto);
  }

  @Override
  public Uni<AccountDto> addAccount(AccountDto accountDto) {
    return accountRepository.addAccount(accountDto);
  }

  @Override
  public Uni<AccountDto> updateAccount(AccountDto accountDto) {
    return accountRepository.updateAccount(accountDto);
  }

  @Override
  public Uni<AccountDto> deleteAccount(AccountDto accountDto) {
    return accountRepository.deleteAccount(accountDto);
  }

  @Override
  public Uni<AccountDto> updateAccountToDeposit(AccountDto accountDto) {
    return accountRepository.updateAccountToDeposit(accountDto);
  }

  @Override
  public Uni<AccountDto> updateAccountToWithdrawal(AccountDto accountDto) {
    return accountRepository.updateAccountToWithdrawal(accountDto);
  }

  @Override
  public Uni<TransferDto> updateTransfer(TransferDto transferDto) {
    return accountRepository.updateTransfer(transferDto);
  }

  @Override
  public Uni<List<AccountDto>> getAccountByCustomer(AccountDto accountDto) {
    return accountRepository.getAccountByCustomer(accountDto);
  }
}
