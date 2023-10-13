package de.dxfrontiers.sample.cdctmessaging.customerservice.customer;

import java.util.UUID;

public interface Customer {
    UUID getId();
    String getMail();
    String getName();
    String getAddress();
}
