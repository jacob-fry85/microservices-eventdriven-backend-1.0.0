package com.microservices.book.multiplication.controller;

import com.microservices.book.multiplication.challenge.Challenge;
import com.microservices.book.multiplication.challenge.ChallengeAttempt;
import com.microservices.book.multiplication.challenge.ChallengeAttemptDTO;
import com.microservices.book.multiplication.challenge.ChallengeService;
import com.microservices.book.multiplication.repository.ChallengeAttemptRepository;
import com.microservices.book.multiplication.repository.UserRepository;
import com.microservices.book.multiplication.user.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/attempts")
public class ChallengeAttemptController {
    private final ChallengeService challengeService;
    private final ChallengeAttemptRepository attemptRepository;
    private final UserRepository userRepository;

    @PostMapping
    ResponseEntity<ChallengeAttempt> postResult(
            @RequestBody @Valid ChallengeAttemptDTO challengeAttemptDTO) {
        log.info("Received Data : {}", challengeAttemptDTO.toString());
        return ResponseEntity.ok(challengeService.verifyAttempt(challengeAttemptDTO));
    }

    @GetMapping("/alias")
    ResponseEntity<ChallengeAttempt> getAttemptSearch(@RequestParam("alias") String alias) {
        Optional<User> findUser = userRepository.findByAlias(alias);
        if(findUser.isPresent()) {
            User user = findUser.get();
            List<ChallengeAttempt> findAttempt = attemptRepository.lastAttempts(user.getAlias());
            ChallengeAttempt lastAttempt = findAttempt.get(0);
            return ResponseEntity.ok(lastAttempt);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping()
    ResponseEntity<List<ChallengeAttempt>> getStatistic(@RequestParam("alias") String alias) {
        Optional<User> findUser = userRepository.findByAlias(alias);
        if(findUser.isPresent()) {
            User user = findUser.get();
            log.info("Hasil " + challengeService.getStatsForUser(alias).toString());
            return ResponseEntity.ok((challengeService.getStatsForUser(user.getAlias())));
        }
        return ResponseEntity.notFound().build();
    }
}
