package com.microservices.book.multiplication.challenge;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class Challenge {
    private int factorA;
    private int factorB;
}
