package de.dxfrontiers.sample.cdctmessaging.shippingservice;

import au.com.dius.pact.consumer.MessagePactBuilder;
import au.com.dius.pact.consumer.dsl.PactDslJsonBody;
import au.com.dius.pact.consumer.junit5.PactConsumerTest;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.consumer.junit5.ProviderType;
import au.com.dius.pact.core.model.PactSpecVersion;
import au.com.dius.pact.core.model.V4Interaction;
import au.com.dius.pact.core.model.V4Pact;
import au.com.dius.pact.core.model.annotations.Pact;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.dxfrontiers.sample.cdctmessaging.shippingservice.order.OrderInboundMessaging;
import de.dxfrontiers.sample.cdctmessaging.shippingservice.shipping.ShippingService;
import org.junit.jupiter.api.Test;

import java.io.IOException;

@PactConsumerTest
@PactTestFor(
        providerType = ProviderType.ASYNCH,
        pactVersion = PactSpecVersion.V4
)
public class ShippingServiceConsumerPactTest {

    ObjectMapper mapper = new ObjectMapper().findAndRegisterModules();
    ShippingService shippingService = new ShippingService();

    @Pact(consumer = "ShippingService", provider = "OrderService")
    V4Pact pact4OrderUpdateEvents(MessagePactBuilder builder) {
        PactDslJsonBody order = new PactDslJsonBody()
                .stringType("shippingAddress", "Digital Frontiers, Tilsiter Str. 6, Sindelfingen")
                .uuid("id");
        PactDslJsonBody body = new PactDslJsonBody()
                .object("order", order);

        return builder
                .expectsToReceive("a newly placed order")
                .withContent(body)
                .toPact();
    }

    @Test
    @PactTestFor(pactMethod = "pact4OrderUpdateEvents")
    void processOrderUpdatedEvent(V4Interaction.AsynchronousMessage message) throws IOException {
        try {
            OrderInboundMessaging.OrderPlacedEvent orderPlacedEvent = mapper.readValue(
                    message.contentsAsBytes(),
                    OrderInboundMessaging.OrderPlacedEvent.class
            );
            shippingService.requestShipping(
                    orderPlacedEvent.getOrder().getId(),
                    orderPlacedEvent.getOrder().getShippingAddress()
            );
        } catch (IOException e) {
            throw e;
        }
    }

}
