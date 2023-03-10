package com.nttdata.btask.service;

import com.nttdata.btask.interfaces.AuthService;
import com.nttdata.domain.contract.AuthRepository;
import com.nttdata.domain.models.AuthDto;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class AuthServiceImpl implements AuthService {
  private final AuthRepository authRepository;

  public AuthServiceImpl(AuthRepository authRepository) {
    this.authRepository = authRepository;
  }

  @Override
  public Multi<AuthDto> list() {
    return authRepository.list();
  }

  @Override
  public Uni<AuthDto> findByNroAuth(AuthDto authDto) {
    return authRepository.findByNroAuth(authDto);
  }

  @Override
  public Uni<AuthDto> addAuth(AuthDto authDto) {
    return authRepository.addAuth(authDto);
  }

  @Override
  public Uni<AuthDto> updateAuth(AuthDto authDto) {
    return authRepository.updateAuth(authDto);
  }

  @Override
  public Uni<AuthDto> deleteAuth(AuthDto authDto) {
    return authRepository.deleteAuth(authDto);
  }
}
