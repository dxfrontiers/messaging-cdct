package de.dxfrontiers.sample.cdctmessaging.customerservice.customer;

import de.dxfrontiers.sample.cdctmessaging.customerservice.exception.NotFoundException;
import lombok.Builder;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class CustomerRepository {

    private final Map<UUID, CustomerEntity> customers = new ConcurrentHashMap<>();

    public Collection<? extends Customer> getAllCustomers() {
        return customers.values();
    }

    public Customer createCustomer(
            String mail,
            String name,
            String address
    ) {
        UUID id;
        do {
            id = UUID.randomUUID();
        } while (customers.containsKey(id));

        CustomerEntity customer = CustomerEntity.builder()
                .id(id)
                .mail(mail)
                .name(name)
                .address(address)
                .build();

        customers.put(id, customer);
        return customer;
    }

    public Customer updateCustomer(
            UUID id,
            String mail,
            String name,
            String address
    ) {
        if (!customers.containsKey(id)) {
            throw new NotFoundException("Customer Not Found");
        }
        CustomerEntity customer = CustomerEntity.builder()
                .id(id)
                .mail(mail)
                .name(name)
                .address(address)
                .build();

        customers.put(id, customer);

        return customer;
    }

    @Data
    @Builder
    public static class CustomerEntity implements Customer {
        private UUID id;
        private String mail;
        private String name;
        private String address;
    }
}
