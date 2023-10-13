package de.dxfrontiers.sample.cdctmessaging.customerservice.exception;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super(message);
    }
}
