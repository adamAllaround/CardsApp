package com.allaroundjava.customers;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RequiredArgsConstructor
@RestController
@RequestMapping("/customers")
class CustomersController {
    private final CustomerService customerService;

    @PostMapping
    public ResponseEntity<CustomerResponse> registerCustomer(@RequestBody CustomerRequest customerRequest) {
        Customer customer = customerService.register(customerRequest.toCustomer());
        return ResponseEntity.created(URI.create(String.format("/customers/%s", customer.getId()))).build();

    }

    @GetMapping("/{customerId}")
    public ResponseEntity<CustomerResponse> getCustomer(@PathVariable String customerId) {
        return
        customerService.find(customerId)
                .map(CustomerResponse::from)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}

@Getter
@Setter
@NoArgsConstructor
class CustomerRequest {
    private String name;
    private String surname;
    private String email;

    public Customer toCustomer() {
        return new Customer(name, surname, email);
    }
}

@Getter
@Setter
@NoArgsConstructor
class CustomerResponse {
    private String id;
    private String name;
    private String surname;
    private String email;

    public static CustomerResponse from(Customer customer) {
        CustomerResponse customerResponse = new CustomerResponse();
        customerResponse.id = customer.getId();
        customerResponse.name = customer.getName();
        customerResponse.surname = customer.getSurname();
        customerResponse.email = customer.getEmail();
        return customerResponse;
    }
}