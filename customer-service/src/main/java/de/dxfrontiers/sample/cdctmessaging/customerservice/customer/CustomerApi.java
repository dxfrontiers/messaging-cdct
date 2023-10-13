package de.dxfrontiers.sample.cdctmessaging.customerservice.customer;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.UUID;

@RestController
@RequestMapping("/customers")
public class CustomerApi {

    private final CustomerService customerService;

    public CustomerApi(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public Collection<? extends Customer> listCustomers() {
        return customerService.listCustomers();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Customer createCustomer(
            @RequestParam("mail") String mail,
            @RequestParam("name") String name,
            @RequestParam("address") String address
    ) {
        return customerService.createCustomer(mail, name, address);
    }

    @PutMapping("/{id}")
    public Customer createCustomer(
            @PathVariable("id") UUID id,
            @RequestParam("mail") String mail,
            @RequestParam("name") String name,
            @RequestParam("address") String address
    ) {
        return customerService.updateCustomer(id, mail, name, address);
    }
}
