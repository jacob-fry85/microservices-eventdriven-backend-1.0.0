package com.microservices.book.multiplication.challenge;

import com.microservices.book.multiplication.repository.ChallengeAttemptRepository;
import com.microservices.book.multiplication.repository.UserRepository;
import com.microservices.book.multiplication.serviceclients.GamificationServiceClient;
import com.microservices.book.multiplication.user.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@Slf4j
@ExtendWith(MockitoExtension.class)
public class ChallengeServiceTest {
    private ChallengeService challengeService;

    @Mock
    private UserRepository userRepository;

    @Mock private GamificationServiceClient gameClient;
    @Mock
    private ChallengeAttemptRepository attemptRepository;

    @BeforeEach
    public void setUp() {
        challengeService = new ChallengeServiceImpl(
                userRepository,
                attemptRepository,
                gameClient
        );
        given(attemptRepository.save(any()))
            .will(returnsFirstArg());
    }

    @Test
    public void checkCorrectAttemptTest() {
//        given
        ChallengeAttemptDTO attemptDTO =
                new ChallengeAttemptDTO(50, 60, "Max", 3000);
//        when
        ChallengeAttempt resultAttempt =
                challengeService.verifyAttempt(attemptDTO);
//        then
        then(resultAttempt.isCorrect()).isTrue();

        verify(gameClient).sendAttempt(resultAttempt);
        verify(userRepository).save(new User("Max"));
        verify(attemptRepository).save(resultAttempt);
    }

    @Test
    public void checkWrongAttemptTest() {
//        given
        ChallengeAttemptDTO attemptDTO = new ChallengeAttemptDTO(50, 60, "john_doe", 5000);
//        when
        ChallengeAttempt resultAttempt = challengeService.verifyAttempt(attemptDTO);
//                then
      then(resultAttempt.isCorrect()).isFalse();
    }

    @Test
    public void checkExistingUserTest() {
//        given
        User existingUser = new User(1L, "john doe");
        given(userRepository.findByAlias("john_doe"))
                .willReturn(Optional.of(existingUser));
        ChallengeAttemptDTO attemptDTO =
                new ChallengeAttemptDTO(50, 60, "john_doe", 5000);
//        when
        ChallengeAttempt resultAttempt =
                challengeService.verifyAttempt(attemptDTO);

        List<ChallengeAttempt> findAttempt = attemptRepository.lastAttempts(existingUser.getAlias());
//        then
        then(resultAttempt.isCorrect()).isFalse();
        then(resultAttempt.getUser()).isEqualTo(existingUser);
        verify(userRepository, never()).save(any());
        verify(attemptRepository).save(resultAttempt);
    }
}
