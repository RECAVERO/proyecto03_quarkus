package com.nttdata.domain.contract;

import com.nttdata.domain.models.CustomerDto;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;

public interface CustomerRepository {
  Multi<CustomerDto> list();

  Uni<CustomerDto> findByNroDocument(CustomerDto customerDto);
  Uni<CustomerDto> addCustomer(CustomerDto customerDto);

  Uni<CustomerDto> updateCustomer(CustomerDto customerDto);

  Uni<CustomerDto> deleteCustomer(CustomerDto customerDto);
}
