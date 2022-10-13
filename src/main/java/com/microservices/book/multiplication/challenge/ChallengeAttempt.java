package com.microservices.book.multiplication.challenge;


import com.microservices.book.multiplication.user.User;
import lombok.*;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChallengeAttempt {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name="USER_ID")
    private User user;

    private int factorA;
    private int factorB;
    private int resultAttempt;
    private boolean correct;

    public ChallengeAttempt(User user, int factorA, int factorB, int resultAttempt, boolean correct) {
        this.user = user;
        this.factorA = factorA;
        this.factorB = factorB;
        this.resultAttempt = resultAttempt;
        this.correct = correct;
    }
}
