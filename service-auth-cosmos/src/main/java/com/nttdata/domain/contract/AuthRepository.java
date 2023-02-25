package com.nttdata.domain.contract;

import com.nttdata.domain.models.AuthDto;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;

public interface AuthRepository {
  Multi<AuthDto> list();

  Uni<AuthDto> findByNroAuth(AuthDto authDto);

  Uni<AuthDto> addAuth(AuthDto authDto);

  Uni<AuthDto> updateAuth(AuthDto authDto);

  Uni<AuthDto> deleteAuth(AuthDto authDto);
}
