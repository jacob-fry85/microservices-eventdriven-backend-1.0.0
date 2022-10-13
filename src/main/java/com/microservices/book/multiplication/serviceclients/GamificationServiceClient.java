package com.microservices.book.multiplication.serviceclients;

import com.microservices.book.multiplication.challenge.ChallengeAttempt;
import com.microservices.book.multiplication.challenge.ChallengeSolvedDTO;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
@AllArgsConstructor
public class GamificationServiceClient {
    private RestTemplate restTemplate;
    @Value("${service.gamification.host}")
    private String gamificationHostUrl;

    public GamificationServiceClient(){
        RestTemplateBuilder builder = new RestTemplateBuilder();
        restTemplate = builder.build();
    }

//    public GamificationServiceClient(final RestTemplateBuilder builder,
//                                     @Value("${service.gamification.host}") final String gamificationHostUrl) {
//        restTemplate = builder.build();
//        this.gamificationHostUrl = gamificationHostUrl;
//    }

    public boolean sendAttempt(final ChallengeAttempt attempt) {
        try {
            ChallengeSolvedDTO dto = new ChallengeSolvedDTO(attempt.getId(),
                    attempt.isCorrect(), attempt.getFactorA(),
                    attempt.getFactorB(), attempt.getUser().getId(),
                    attempt.getUser().getAlias());

            ResponseEntity<String> r = restTemplate.postForEntity(
                    gamificationHostUrl + "/attempts", dto,
                    String.class);
            log.info("Gamification service response: {}", r.getStatusCode());
            return r.getStatusCode().is2xxSuccessful();
        } catch (Exception e) {
            log.error("There was a problem sending the attempt.", e);
            return false;
        }
    }
}
