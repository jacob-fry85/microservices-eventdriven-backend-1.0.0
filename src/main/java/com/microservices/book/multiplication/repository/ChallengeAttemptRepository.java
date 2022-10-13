package com.microservices.book.multiplication.repository;

import com.microservices.book.multiplication.challenge.Challenge;
import com.microservices.book.multiplication.challenge.ChallengeAttempt;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ChallengeAttemptRepository extends CrudRepository<ChallengeAttempt, Integer> {
    List<ChallengeAttempt> findTop10ByUserAliasOrderByIdDesc(String userAlias);

    @Query("SELECT a FROM ChallengeAttempt a WHERE a.user.alias = ?1 ORDER BY a.id DESC")
    List<ChallengeAttempt> lastAttempts(String userAlias);
}
