package de.dxfrontiers.sample.cdctmessaging.orderservice.order;

import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderOutboundMessaging orderOutboundMessaging;

    public OrderService(OrderRepository orderRepository, OrderOutboundMessaging orderOutboundMessaging) {
        this.orderRepository = orderRepository;
        this.orderOutboundMessaging = orderOutboundMessaging;
    }

    public Collection<? extends Order> listOrders() {
        return orderRepository.getAllOrders();
    }

    public Order placeOrder(
            String customer,
            Collection<String> items,
            int price,
            String shippingAddress
    ) {
        Order order = orderRepository.createOrder(customer, items, price, shippingAddress);
        orderOutboundMessaging.publishOrderPlacedEvent(order);
        return order;
    }
}
