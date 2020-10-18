package com.allaroundjava.customers;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
class CustomerService {
    private final CustomersRepository customersRepository;
    private final EventSender sender;

    public Customer register(Customer customer) {
        CustomerEntity entity = customersRepository.save(customer.toEntity());
        sender.send(NewCustomerEvent.from(entity));
        return customer.withId(entity.getId());
    }

    public Optional<Customer> find(String customerId) {
        return Optional.empty();
    }
}

@Getter
class Customer {
    private final String id;
    private final String name;
    private final String surname;
    private final String email;

    public Customer(String id, String name, String surname, String email) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.email = email;
    }

    public Customer(String name, String surname, String email) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.surname = surname;
        this.email = email;
    }

    public CustomerEntity toEntity() {
        CustomerEntity c = new CustomerEntity();
        c.setEmail(email);
        c.setName(name);
        c.setSurname(surname);
        return c;
    }

    public Customer withId(String id) {
        return new Customer(id, name, surname, email);
    }
}

@NoArgsConstructor
@Getter
@Setter
class NewCustomerEvent {
    private String id;
    private Instant createdAt;
    private String email;

    public static NewCustomerEvent from(CustomerEntity entity) {
        NewCustomerEvent message = new NewCustomerEvent();
        message.createdAt = Instant.now();
        message.id = entity.getId();
        message.email = entity.getEmail();
        return message;
    }
}
