package de.dxfrontiers.sample.cdctmessaging.customerservice.customer;

import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.UUID;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerOutboundMessaging customerOutboundMessaging;

    public CustomerService(CustomerRepository customerRepository, CustomerOutboundMessaging customerOutboundMessaging) {
        this.customerRepository = customerRepository;
        this.customerOutboundMessaging = customerOutboundMessaging;
    }

    public Collection<? extends Customer> listCustomers() {
        return customerRepository.getAllCustomers();
    }

    public Customer createCustomer(
            String mail,
            String name,
            String address
    ) {
        Customer customer = customerRepository.createCustomer(mail, name, address);
        customerOutboundMessaging.publishCustomerUpdatedEvent(customer);
        return customer;
    }

    public Customer updateCustomer(
            UUID id,
            String mail,
            String name,
            String address
    ) {
        Customer customer = customerRepository.updateCustomer(id, mail, name, address);
        customerOutboundMessaging.publishCustomerUpdatedEvent(customer);
        return customer;
    }
}
