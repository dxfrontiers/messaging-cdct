package de.dxfrontiers.sample.cdctmessaging.orderservice.order;

import java.util.Collection;
import java.util.UUID;

public interface Order {
    UUID getId();
    String getCustomer();
    Collection<String> getItems();
    int getPrice();
    String getShippingAddress();
}
