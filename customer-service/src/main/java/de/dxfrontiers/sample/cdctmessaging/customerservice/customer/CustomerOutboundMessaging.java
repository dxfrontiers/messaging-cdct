package de.dxfrontiers.sample.cdctmessaging.customerservice.customer;

import lombok.Builder;
import lombok.Value;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class CustomerOutboundMessaging {

    private final StreamBridge streamBridge;

    public CustomerOutboundMessaging(StreamBridge streamBridge) {
        this.streamBridge = streamBridge;
    }

    public void publishCustomerUpdatedEvent(Customer updatedCustomer) {
        streamBridge.send(
                "customer",
                CustomerUpdatedEvent.builder()
                        .updatedAt(Instant.now())
                        .customer(updatedCustomer)
                        .build()
        );
    }

    @Value
    @Builder
    private static class CustomerUpdatedEvent {
        private final Instant updatedAt;
        private final Customer customer;
    }
}
