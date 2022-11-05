package com.microservices.book.multiplication.challenge;

import lombok.Value;

@Value
public class ChallengeSolvedEvent {

    Long attemptId;
    boolean correct;
    int factorA;
    int factorB;
    long userId;
    String userAlias;
}
