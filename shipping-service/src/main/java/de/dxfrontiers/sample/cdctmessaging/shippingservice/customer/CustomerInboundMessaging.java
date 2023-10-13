package de.dxfrontiers.sample.cdctmessaging.shippingservice.customer;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.UUID;
import java.util.function.Consumer;

@Component("customer")
public class CustomerInboundMessaging implements Consumer<CustomerInboundMessaging.CustomerUpdatedEvent> {

    @Override
    public void accept(CustomerUpdatedEvent customerUpdatedEvent) {
        System.out.println(customerUpdatedEvent);
    }

    @Data
    public static class CustomerUpdatedEvent {
        private Instant updatedAt;
        private Customer customer;

        @Data
        public static class Customer {
            private UUID id;
            private String mail;
            private String name;
            private String address;
        }
    }
}
