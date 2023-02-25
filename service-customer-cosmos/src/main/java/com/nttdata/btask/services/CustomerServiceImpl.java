package com.nttdata.btask.services;

import com.nttdata.btask.interfaces.CustomerService;
import com.nttdata.domain.contract.CustomerRepository;
import com.nttdata.domain.models.CustomerDto;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.vertx.core.json.JsonObject;

import javax.enterprise.context.ApplicationScoped;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class CustomerServiceImpl implements CustomerService {
  private final CustomerRepository customerRepository;
  private final redisServiceImpl redisService;

  public CustomerServiceImpl(CustomerRepository customerRepository, redisServiceImpl redisService) {
    this.customerRepository = customerRepository;
    this.redisService = redisService;
  }

  @Override
  public Multi<CustomerDto> list() {
    List<JsonObject> lista = new ArrayList<>();
    return customerRepository.list().map(c->{
      return c;
    }).call(customerDto -> {
      JsonObject jsonCustomer = new JsonObject()
          .put("name", customerDto.getName())
          .put("lastName", customerDto.getLastName())
          .put("nroDocument", customerDto.getNroDocument())
          .put("typeCustomer", customerDto.getTypeCustomer())
          .put("typeDocument", customerDto.getTypeDocument())
          .put("created_datetime", customerDto.getCreated_datetime())
          .put("updated_datetime", customerDto.getUpdated_datetime())
          .put("active", customerDto.getActive());
      lista.add(jsonCustomer);
      return redisService.redisAdd("listCustomer",lista.toString());
    });
  }

  @Override
  public Uni<CustomerDto> findByNroDocument(CustomerDto customerDto) {
    return customerRepository.findByNroDocument(customerDto);
  }

  @Override
  public Uni<CustomerDto> addCustomer(CustomerDto customerDto) {
    return customerRepository.addCustomer(customerDto);
  }

  @Override
  public Uni<CustomerDto> updateCustomer(CustomerDto customerDto) {
    return customerRepository.updateCustomer(customerDto);
  }

  @Override
  public Uni<CustomerDto> deleteCustomer(CustomerDto customerDto) {
    return customerRepository.deleteCustomer(customerDto);
  }

  @Override
  public Uni<String> getRedis(String key) {
    return redisService.getRedis(key);
  }

}
