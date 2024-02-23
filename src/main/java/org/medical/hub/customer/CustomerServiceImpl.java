package org.medical.hub.customer;

import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.medical.hub.datatable.*;
import org.medical.hub.exception.MailNotFoundException;
import org.medical.hub.mail.MailRepository;
import org.medical.hub.mail.UserEmails;
import org.medical.hub.repository.SearchCriteria;
import org.medical.hub.repository.SearchOperation;
import org.medical.hub.specification.CustomerSpecification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerServiceImpl.class);
    private final CustomerRepository customerRepository;
    private final MailRepository mailRepository;

    @Override
    public void save(CustomerRequest request) {
        LOGGER.info("Adding the customer details.");
        try {
            if(findByEmail(request.getEmail()) != null){
                LOGGER.info("Customer already exists having email "+request.getEmail());
                throw new CustomerEmailAlreadyExists("Customer having email already exists", "/customer/add");
            }

            Customer customer = new Customer();
            customer.setName(request.getName());
            customer.setEmail(request.getEmail());
            customer.setPhone(request.getPhone());
            customer.setCreatedAt(System.currentTimeMillis());
            customer.setUpdatedAt(System.currentTimeMillis());

            customerRepository.save(customer);
            LOGGER.info("Customer details saved successfully.");
        } catch (Exception ex) {
            LOGGER.error("Error while storing the details.", ex);
        }
    }

    @Override
    public void update(long id, CustomerRequest request) {
        LOGGER.info("Updating the customer details having id: "+id);
        try {

            if(findByEmail(request.getEmail()) != null){
                LOGGER.info("Customer already exists having email "+request.getEmail());
                throw new CustomerEmailAlreadyExists("Customer having email already exists", "/customer/"+id+"/edit");
            }

            Customer customer = findCustomerById(id);
            customer.setName(request.getName());
            customer.setEmail(request.getEmail());
            customer.setPhone(request.getPhone());
            customer.setUpdatedAt(System.currentTimeMillis());

            customerRepository.save(customer);
            LOGGER.info("Customer details updated successfully.");
        } catch (Exception ex) {
            LOGGER.error("Error while updating the customer details.", ex);
        }
    }

    @Override
    @Transactional
    public void saveCustomerAndLinkWithMail(CustomerRequest request, Long mailId) {
        LOGGER.info("Adding the customer details and linking with the mail.");
        try {

            UserEmails mailById = findById(mailId);
            Customer byEmail = findByEmail(request.getEmail());
            if(byEmail != null){
                LOGGER.info("Customer already exists having email "+request.getEmail());
                throw new CustomerEmailAlreadyExists("Customer having email already exists", "/customer/add");
            }

            // save customer
            Customer customer = new Customer();
            customer.setName(request.getName());
            customer.setEmail(request.getEmail());
            customer.setPhone(request.getPhone());
            customer.setCreatedAt(System.currentTimeMillis());
            customer.setUpdatedAt(System.currentTimeMillis());
            customerRepository.save(customer);

            // link with customer
            mailById.setCustomer(customer);
            mailRepository.save(mailById);

            LOGGER.info("Customer details saved successfully.");
        } catch (Exception ex) {
            LOGGER.error("Error while storing the details.", ex);
        }
    }

    public UserEmails findById(long mailId) {
        Optional<UserEmails> byId = mailRepository.findById(mailId);
        if (byId.isEmpty()) {
            LOGGER.debug("Unable to find the mail having id " + mailId);
            throw new CustomerNotFoundException();
        }
        return byId.get();
    }

    @Override
    public Customer findCustomerById(long customerId) {

        return customerRepository.findById(customerId)
                .orElseThrow(MailNotFoundException::new);
    }

    private Customer findByEmail(String email){

        return customerRepository.findByEmail(email);
    }

    @Override
    public DataTableResponse<CustomerDataTable> getCustomersForDataTable(DataTableRequest dataTable) {
        var specification = new CustomerSpecification();
        String value = dataTable.getSearch().getValue();
        if (StringUtils.isNotBlank(value)) {
            specification.add(new SearchCriteria("email", value, SearchOperation.LIKE));
            specification.add(new SearchCriteria("name", value, SearchOperation.LIKE));
//            specification.add(new SearchCriteria("phone", value, SearchOperation.LIKE));
        }

        DataTableOrder order = dataTable.getOrder().stream().findFirst().orElse(null);

        Pageable sortedByName = PageRequest.of(dataTable.getStart(), dataTable.getLength());
        if (order != null) {
            DataTableColumnSpecs dataTableColumnSpecs = dataTable.getColumns().get(order.getColumn());
            if (dataTableColumnSpecs != null) {
                String data = dataTableColumnSpecs.getData();
                if (data.equals("sn") || data.equals("action")) {
                    data = "id";
                }
                Sort.Direction dir = (order.getDir().equals("asc")) ? Sort.Direction.ASC : Sort.Direction.DESC;
                Sort by = Sort.by(new Sort.Order(dir, data));
                int pageNumber = DataTableUtils.getPageNumber(dataTable);
                sortedByName = PageRequest.of(pageNumber, dataTable.getLength(), by);
            }
        }

        Page<Customer> all = customerRepository.findAll(specification, sortedByName);
        List<CustomerDataTable> dataTablesData = mapRuleData(all.getContent(), dataTable);
        long totalRecord = customerRepository.count();
        long filteredRecord = customerRepository.count(specification);
        DataTableResponse<CustomerDataTable> response = new DataTableResponse<>();
        response.setData(dataTablesData);
        response.setDraw(dataTable.getDraw());
        response.setRecordsFiltered(filteredRecord);
        response.setRecordsTotal(totalRecord);

        return response;
    }

    private List<CustomerDataTable> mapRuleData(List<Customer> customers, DataTableRequest dataTableRequest) {
        List<CustomerDataTable> dataTablesData = new ArrayList<>();
        int i = 1;
        for (Customer customer : customers) {

            CustomerDataTable ruleDataTable = new CustomerDataTable();
            ruleDataTable.setId(customer.getId());
            ruleDataTable.setSn(getSN(dataTableRequest, i));
            ruleDataTable.setName(customer.getName());
            ruleDataTable.setEmail(customer.getEmail());
            ruleDataTable.setPhoneNumber(getPhoneNumber(customer.getPhone()));
            String format = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(customer.getCreatedAt());
            ruleDataTable.setCreatedAt(format);
            ruleDataTable.setAction(String.valueOf(customer.getId()));
            dataTablesData.add(ruleDataTable);

            i++;
        }

        return dataTablesData;
    }

    @Override
    public void delete(Long id) {
        LOGGER.info("Deleting the customer details having id: {}", id);
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Could not find Customer with ID=" + id));
        customerRepository.delete(customer);
        LOGGER.info("Customer deleted having id: {}", id);
    }

    private String getPhoneNumber(List<String> phoneNumbers){
        return String.join(", ", phoneNumbers);
    }

    private Integer getSN(DataTableRequest dataTable, int i) {
        return dataTable.getStart() + i;
    }
}
