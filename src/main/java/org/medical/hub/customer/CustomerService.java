package org.medical.hub.customer;

import org.medical.hub.datatable.DataTableRequest;
import org.medical.hub.datatable.DataTableResponse;
import org.medical.hub.mail.UserEmails;

public interface CustomerService {

    void save(CustomerRequest request);

    void update(long id, CustomerRequest request);

    void saveCustomerAndLinkWithMail(CustomerRequest request, Long mailId);

    UserEmails findById(long mailId);

    Customer findCustomerById(long customerId);

    DataTableResponse<CustomerDataTable> getCustomersForDataTable(DataTableRequest dataTable);

    void delete(Long id);
}
