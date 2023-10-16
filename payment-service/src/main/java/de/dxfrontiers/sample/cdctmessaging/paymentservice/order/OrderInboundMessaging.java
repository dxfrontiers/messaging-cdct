package de.dxfrontiers.sample.cdctmessaging.paymentservice.order;

import de.dxfrontiers.sample.cdctmessaging.paymentservice.payment.PaymentService;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;

@Component("order")
public class OrderInboundMessaging implements Consumer<OrderInboundMessaging.OrderPlacedEvent> {

    private final PaymentService paymentService;

    public OrderInboundMessaging(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @Override
    public void accept(OrderPlacedEvent orderPlacedEvent) {
        paymentService.startPaymentProcess(
                orderPlacedEvent.order.getId(),
                orderPlacedEvent.order.getCustomer(),
                orderPlacedEvent.order.getItems(),
                orderPlacedEvent.order.getPrice()
        );
    }

    @Data
    public static class OrderPlacedEvent {
        private Instant publishedAt;
        private Order order;

        @Data
        public static class Order {
            private UUID id;
            private String customer;
            private List<String> items;
            private int price;
        }
    }
}
