package com.nttdata.domain.contract;

import com.nttdata.domain.models.CreditDto;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;

public interface CreditRepository {
  Multi<CreditDto> list();

  Uni<CreditDto> findByNroAccount(CreditDto creditDto);

  Uni<CreditDto> addCredit(CreditDto creditDto);

  Uni<CreditDto> updateCredit(CreditDto creditDto);

  Uni<CreditDto> deleteCredit(CreditDto creditDto);
}
