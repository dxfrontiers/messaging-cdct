package de.dxfrontiers.sample.cdctmessaging.orderservice.order;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/orders")
public class OrderApi {

    private final OrderService orderService;

    public OrderApi(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public Collection<? extends Order> listOrders() {
        return orderService.listOrders();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Order placeOrder(
            @RequestParam("customer") String customer,
            @RequestParam("items") List<String> items,
            @RequestParam("price") int price,
            @RequestParam("shippingAddress") String shippingAddress
    ) {
        return orderService.placeOrder(customer, items, price, shippingAddress);
    }
}
