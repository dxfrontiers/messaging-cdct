package de.dxfrontiers.sample.cdctmessaging.shippingservice.shipping;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ShippingService {

    public void requestShipping(UUID orderId, String shippingAddress) {
        System.out.println("Start shipping process for order " + orderId + " to address: " + shippingAddress);
    }
}
