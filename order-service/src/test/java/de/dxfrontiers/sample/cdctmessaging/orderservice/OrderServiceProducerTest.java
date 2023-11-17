package de.dxfrontiers.sample.cdctmessaging.orderservice;

import au.com.dius.pact.provider.PactVerifyProvider;
import au.com.dius.pact.provider.junit5.MessageTestTarget;
import au.com.dius.pact.provider.junit5.PactVerificationContext;
import au.com.dius.pact.provider.junit5.PactVerificationInvocationContextProvider;
import au.com.dius.pact.provider.junitsupport.Provider;
import au.com.dius.pact.provider.junitsupport.loader.PactFolder;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.dxfrontiers.sample.cdctmessaging.orderservice.order.Order;
import de.dxfrontiers.sample.cdctmessaging.orderservice.order.OrderOutboundMessaging;
import de.dxfrontiers.sample.cdctmessaging.orderservice.order.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;

import java.time.Instant;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

@Provider("OrderService")
@PactFolder("pacts")
public class OrderServiceProducerTest {
    ObjectMapper objectMapper = new ObjectMapper().findAndRegisterModules();

    @BeforeEach
    void before(PactVerificationContext context) {
        context.setTarget(new MessageTestTarget());
    }

    @TestTemplate
    @ExtendWith(PactVerificationInvocationContextProvider.class)
    void pactVerificationTestTemplate(PactVerificationContext context) {
        context.verifyInteraction();
    }

    @PactVerifyProvider("a newly placed order")
    String sendOrderUpdatedEvent() {
        List<String> items = new LinkedList<>();
        items.add("Laptop");
        items.add("Bildschirm");
        Order order = OrderRepository.OrderEntity.builder()
                .id(UUID.randomUUID())
                .customer("Digital Frontiers")
                .shippingAddress("Digital Frontiers, Tilsiter Str. 6, Sindelfingen")
                .price(2000)
                .items(items)
                .build();
        OrderOutboundMessaging.OrderPlacedEvent event = OrderOutboundMessaging.OrderPlacedEvent.builder()
                .order(order)
                .publishedAt(Instant.now())
                .build();
        try {
            return objectMapper.writeValueAsString(event);
        } catch (Exception e) {
            return null;
        }
    }

}