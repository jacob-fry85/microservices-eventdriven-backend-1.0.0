package com.microservices.book.multiplication.user;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue
    private Long id;

    private String alias;

    public User(final String userAlias) {
        this(null, userAlias);
    }
}
