package de.dxfrontiers.sample.cdctmessaging.orderservice.order;

import lombok.Builder;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class OrderRepository {

    private final Map<UUID, OrderEntity> orders = new ConcurrentHashMap<>();

    public Collection<? extends Order> getAllOrders() {
        return orders.values();
    }

    public Order createOrder(
            String customer,
            Collection<String> items,
            int price,
            String shippingAddress
    ) {
        UUID id;
        do {
            id = UUID.randomUUID();
        } while (orders.containsKey(id));

        OrderEntity order = OrderEntity.builder()
                .id(id)
                .customer(customer)
                .items(items)
                .price(price)
                .shippingAddress(shippingAddress)
                .build();

        orders.put(id, order);
        return order;
    }

    @Data
    @Builder
    public static class OrderEntity implements Order {
        private UUID id;
        private String customer;
        private Collection<String> items;
        private int price;
        private String shippingAddress;
    }
}
