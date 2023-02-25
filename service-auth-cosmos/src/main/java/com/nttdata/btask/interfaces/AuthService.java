package com.nttdata.btask.interfaces;

import com.nttdata.domain.models.AuthDto;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;

public interface AuthService {
  Multi<AuthDto> list();

  Uni<AuthDto> findByNroAuth(AuthDto authDto);

  Uni<AuthDto> addAuth(AuthDto authDto);

  Uni<AuthDto> updateAuth(AuthDto authDto);

  Uni<AuthDto> deleteAuth(AuthDto authDto);
}
