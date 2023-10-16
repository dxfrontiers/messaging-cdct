package de.dxfrontiers.sample.cdctmessaging.paymentservice.payment;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PaymentService {

    public void startPaymentProcess(UUID orderId, String customer, List<String> items, int price) {
        System.out.println(
                "Starting to payment process for order " + orderId + " invoicing " + price + " from customer " + customer + " for items " + items
        );
    }
}
