package com.allaroundjava.customers;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "customers")
@Getter
@Setter
class CustomerEntity {
    private String id;
    private String name;
    private String surname;
    private String email;
}
