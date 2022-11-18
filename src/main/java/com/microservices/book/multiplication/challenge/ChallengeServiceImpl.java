package com.microservices.book.multiplication.challenge;

import com.microservices.book.multiplication.repository.ChallengeAttemptRepository;
import com.microservices.book.multiplication.repository.UserRepository;
import com.microservices.book.multiplication.serviceclients.GamificationServiceClient;
import com.microservices.book.multiplication.user.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ChallengeServiceImpl implements ChallengeService{

    private final UserRepository userRepository;
    private final ChallengeAttemptRepository attemptRepository;
//    private final GamificationServiceClient gameClient;

    private final ChallengeEventPub challengeEventPub;

    /**
     * If there is an exception in a method annotated with @Transactional, the transaction
     * will be rolled back. If we would need all our methods within a given service to be
     * transactional, we can add instead this annotation at the class level.
     * @param attemptDTO
     * @return
     */
    @Transactional
    @Override
    public ChallengeAttempt verifyAttempt(ChallengeAttemptDTO attemptDTO) {
//        check if user already exists for that alias, otherwise create it
//        orElseGet()->Remember (from the Javadoc) that the Supplier method passed as an argument is only executed when an Optional value isn't present.
        User user = userRepository.findByAlias(attemptDTO.getUserAlias())
                .orElseGet(() -> {
                    log.info("Creating new user with alias {}",
                            attemptDTO.getUserAlias());
                    return userRepository.save(
                            new User(attemptDTO.getUserAlias())
                    );
                });

//        check if the attempt is correct
        boolean isCorrect = attemptDTO.getGuess() == attemptDTO.getFactorA() * attemptDTO.getFactorB();

//        Builds the domain object
        ChallengeAttempt checkedAttempt = new ChallengeAttempt(user,
                attemptDTO.getFactorA(),
                attemptDTO.getFactorB(),
                attemptDTO.getGuess(),
                isCorrect
        );

        ChallengeAttempt storedAttempt = attemptRepository.save(checkedAttempt);

//        Sends the attempt to gamification and prints the response
//        boolean status = gameClient.sendAttempt(storedAttempt);
//        log.info("Gamification service response: {} ", status);

        challengeEventPub.challengeSolved(storedAttempt);

        return storedAttempt;
    }

    @Override
    public List<ChallengeAttempt> getStatsForUser(String userAlias) {
        return attemptRepository.findTop10ByUserAliasOrderByIdDesc(userAlias);
    }
}
