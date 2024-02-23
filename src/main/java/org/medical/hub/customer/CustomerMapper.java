package org.medical.hub.customer;

import org.mapstruct.Mapper;

@Mapper
public interface CustomerMapper {

    Customer dtoToCustomer(CustomerRequest request);

    CustomerRequest toDTO(Customer customer);
}
