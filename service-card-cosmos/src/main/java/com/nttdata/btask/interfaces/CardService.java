package com.nttdata.btask.interfaces;

import com.nttdata.domain.models.CardDto;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;

public interface CardService {
  Multi<CardDto> list();

  Uni<CardDto> findByNroDocument(CardDto cardDto);

  Uni<CardDto> addCard(CardDto cardDto);

  Uni<CardDto> updateCard(CardDto cardDto);

  Uni<CardDto> deleteCard(CardDto cardDto);
}
