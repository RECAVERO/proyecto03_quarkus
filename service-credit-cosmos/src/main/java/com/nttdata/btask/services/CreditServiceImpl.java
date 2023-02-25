package com.nttdata.btask.services;

import com.nttdata.btask.interfaces.CreditService;
import com.nttdata.domain.contract.CreditRepository;
import com.nttdata.domain.models.CreditDto;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.vertx.core.json.JsonObject;

import javax.enterprise.context.ApplicationScoped;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class CreditServiceImpl implements CreditService {
  private final CreditRepository creditRepository;
  private final redisServiceImpl redisService;

  public CreditServiceImpl(CreditRepository creditRepository, redisServiceImpl redisService) {
    this.creditRepository = creditRepository;
    this.redisService = redisService;
  }

  @Override
  public Multi<CreditDto> list() {

    List<JsonObject> lista = new ArrayList<>();
    return creditRepository.list().map(c->{
      return c;

    }).call(creditDto -> {
      JsonObject jsonCustomer = new JsonObject()
          .put("numberDocument", creditDto.getNumberDocument())
          .put("dateStart", creditDto.getDateStart())
          .put("share", creditDto.getShare())
          .put("datePay", creditDto.getDatePay())
          .put("balanceStart", creditDto.getBalanceStart())
          .put("amount", creditDto.getAmount())
          .put("balanceEnd", creditDto.getBalanceEnd())
          .put("created_datetime", creditDto.getCreated_datetime())
          .put("updated_datetime", creditDto.getUpdated_datetime())
          .put("active", creditDto.getActive());
      lista.add(jsonCustomer);
      return redisService.redisAdd("listCredit",lista.toString());
    });
  }

  @Override
  public Uni<CreditDto> findByNroAccount(CreditDto creditDto) {
    return creditRepository.findByNroAccount(creditDto);
  }

  @Override
  public Uni<CreditDto> addCredit(CreditDto creditDto) {
    return creditRepository.addCredit(creditDto);
  }

  @Override
  public Uni<CreditDto> updateCredit(CreditDto creditDto) {
    return creditRepository.updateCredit(creditDto);
  }

  @Override
  public Uni<CreditDto> deleteCredit(CreditDto creditDto) {
    return creditRepository.deleteCredit(creditDto);
  }
}
