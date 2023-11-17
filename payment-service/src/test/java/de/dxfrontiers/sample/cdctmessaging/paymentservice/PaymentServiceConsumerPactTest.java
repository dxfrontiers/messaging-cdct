package de.dxfrontiers.sample.cdctmessaging.paymentservice;

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
import de.dxfrontiers.sample.cdctmessaging.paymentservice.order.OrderInboundMessaging;
import de.dxfrontiers.sample.cdctmessaging.paymentservice.payment.PaymentService;
import org.junit.jupiter.api.Test;

import java.io.IOException;

@PactConsumerTest
@PactTestFor(
        providerType = ProviderType.ASYNCH,
        pactVersion = PactSpecVersion.V4
)
public class PaymentServiceConsumerPactTest {

    ObjectMapper mapper = new ObjectMapper().findAndRegisterModules();
    PaymentService paymentService = new PaymentService();

    @Pact(consumer = "PaymentService", provider = "OrderService")
    V4Pact pact4OrderUpdateEvents(MessagePactBuilder builder) {
        PactDslJsonBody order = new PactDslJsonBody()
                .uuid("id")
                .stringType("customer", "Digital Frontiers")
                .integerType("price", 2000)
                .array("items")
                .stringValue("Laptop")
                .stringValue("Bildschirm")
                .closeArray()
                .asBody();

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

            paymentService.startPaymentProcess(
                    orderPlacedEvent.getOrder().getId(),
                    orderPlacedEvent.getOrder().getCustomer(),
                    orderPlacedEvent.getOrder().getItems(),
                    orderPlacedEvent.getOrder().getPrice()
            );


        } catch (IOException e) {
            throw e;
        }
    }

}
