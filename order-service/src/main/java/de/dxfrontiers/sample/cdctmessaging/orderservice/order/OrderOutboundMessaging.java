package de.dxfrontiers.sample.cdctmessaging.orderservice.order;

import lombok.Builder;
import lombok.Value;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class OrderOutboundMessaging {

    private final StreamBridge streamBridge;

    public OrderOutboundMessaging(StreamBridge streamBridge) {
        this.streamBridge = streamBridge;
    }

    public void publishOrderPlacedEvent(Order updatedCustomer) {
        streamBridge.send(
                "order",
                MessageBuilder
                        .withPayload(
                                OrderPlacedEvent.builder()
                                        .publishedAt(Instant.now())
                                        .order(updatedCustomer)
                                        .build()
                        )
                        .setHeader("messageType", "ORDER_PLACED")
                        .build()
        );
    }

    @Value
    @Builder
    private static class OrderPlacedEvent {
        private final Instant publishedAt;
        private final Order order;
    }
}
