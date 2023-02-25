package com.nttdata.btask.services;

import com.nttdata.btask.interfaces.CardService;
import com.nttdata.domain.contract.CardRepository;
import com.nttdata.domain.models.CardDto;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.vertx.core.json.JsonObject;

import javax.enterprise.context.ApplicationScoped;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class CardServiceImpl implements CardService {
  private final CardRepository cardRepository;
  private final redisServiceImpl redisService;

  public CardServiceImpl(CardRepository cardRepository, redisServiceImpl redisService) {
    this.cardRepository = cardRepository;
    this.redisService = redisService;
  }

  @Override
  public Multi<CardDto> list() {

    List<JsonObject> lista = new ArrayList<>();
    return cardRepository.list().map(c->{
      return c;
    }).call(customerDto -> {
      JsonObject jsonCustomer = new JsonObject()
          .put("typeCard", customerDto.getTypeCard())
          .put("numberCard", customerDto.getNumberCard())
          .put("numberAccountAssociated", customerDto.getNumberAccountAssociated())
          .put("pin", customerDto.getPin())
          .put("dueDate", customerDto.getDueDate())
          .put("codeValidation", customerDto.getCodeValidation())
          .put("created_datetime", customerDto.getCreated_datetime())
          .put("updated_datetime", customerDto.getUpdated_datetime())
          .put("active", customerDto.getActive());
      lista.add(jsonCustomer);
      return redisService.redisAdd("listCard",lista.toString());
    });
  }

  @Override
  public Uni<CardDto> findByNroDocument(CardDto cardDto) {
    return cardRepository.findByNroDocument(cardDto);
  }

  @Override
  public Uni<CardDto> addCard(CardDto cardDto) {
    return cardRepository.addCard(cardDto);
  }

  @Override
  public Uni<CardDto> updateCard(CardDto cardDto) {
    return cardRepository.updateCard(cardDto);
  }

  @Override
  public Uni<CardDto> deleteCard(CardDto cardDto) {
    return cardRepository.deleteCard(cardDto);
  }
}
