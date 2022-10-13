package com.microservices.book.multiplication.challenge;

import java.util.List;

public interface ChallengeService {
    ChallengeAttempt verifyAttempt(ChallengeAttemptDTO resultAttempt);
    List<ChallengeAttempt> getStatsForUser(String userAlias);
}
