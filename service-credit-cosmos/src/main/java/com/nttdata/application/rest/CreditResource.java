package com.nttdata.application.rest;

import com.nttdata.btask.interfaces.CreditService;
import com.nttdata.domain.models.CreditDto;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/credits")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CreditResource {
  private final CreditService creditService;

  public CreditResource(CreditService creditService) {
    this.creditService = creditService;
  }

  @GET
  public Multi<CreditDto> findAll() {
    return creditService.list();
  }

  @POST
  public Uni<CreditDto> add(CreditDto creditDto) {
    return creditService.addCredit(creditDto);
  }

  @PUT
  public Uni<CreditDto> updateCustomer(CreditDto creditDto) {
    return creditService.updateCredit(creditDto);
  }

  @POST
  @Path("/search")
  public Uni<CreditDto> findByNroDocument(CreditDto creditDto) {
    return creditService.findByNroAccount(creditDto);
  }

  @DELETE
  public Uni<CreditDto> deleteCustomer(CreditDto creditDto) {
    return creditService.deleteCredit(creditDto);
  }
}
