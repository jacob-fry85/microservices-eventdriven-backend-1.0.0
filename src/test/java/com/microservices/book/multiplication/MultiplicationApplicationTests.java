package com.microservices.book.multiplication;

import com.microservices.book.multiplication.challenge.Challenge;
import com.microservices.book.multiplication.challenge.ChallengeGeneratorService;
import com.microservices.book.multiplication.challenge.ChallengeGeneratorServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Random;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.BDDMockito.given;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class MultiplicationApplicationTests {
    private ChallengeGeneratorService challengeGeneratorService;

    @Spy
    private Random random;

    @BeforeEach
    public void setup() {
        challengeGeneratorService = new ChallengeGeneratorServiceImpl(random);
    }
    @Test
    public void generateRandomFactorisBetweenExpectedLimits() {
//        89 is max - min range
        given(random.nextInt(89)).willReturn(20, 30);

        Challenge challenge = challengeGeneratorService.randomChallenge();

        then(challenge).isEqualTo(new Challenge(31, 41));
    }


}
