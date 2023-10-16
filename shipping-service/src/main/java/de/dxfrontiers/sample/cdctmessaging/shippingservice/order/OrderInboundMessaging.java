package de.dxfrontiers.sample.cdctmessaging.shippingservice.order;

import de.dxfrontiers.sample.cdctmessaging.shippingservice.shipping.ShippingService;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.UUID;
import java.util.function.Consumer;

@Component("order")
public class OrderInboundMessaging implements Consumer<OrderInboundMessaging.OrderPlacedEvent> {

    private final ShippingService shippingService;

    public OrderInboundMessaging(ShippingService shippingService) {
        this.shippingService = shippingService;
    }

    @Override
    public void accept(OrderPlacedEvent orderPlacedEvent) {
        shippingService.requestShipping(orderPlacedEvent.order.getId(), orderPlacedEvent.order.shippingAddress);
    }

    @Data
    public static class OrderPlacedEvent {
        private Instant publishedAt;
        private Order order;

        @Data
        public static class Order {
            private UUID id;
            private String shippingAddress;
        }
    }
}
