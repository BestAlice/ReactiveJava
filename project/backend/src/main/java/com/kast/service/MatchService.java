package com.kast.service;

import com.kast.entity.match.Match;
import com.kast.repository.match.MatchRepositoryImpl;
import com.mongodb.client.model.Filters;
import com.mongodb.reactivestreams.client.FindPublisher;
import io.reactivex.rxjava3.core.Single;
import jakarta.inject.Singleton;
import org.bson.conversions.Bson;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;

@Singleton
public class MatchService {
    private final MatchRepositoryImpl matchRepository;

    public MatchService(MatchRepositoryImpl matchRepository) {
        this.matchRepository = matchRepository;
    }

    public FindPublisher<Match> getOngoingMatches() {
        Bson filter = Filters.lte("startTime", LocalDateTime.now());
        return matchRepository.findAll().filter(filter);
    }

    public FindPublisher<Match> getUpcomingMatches() {
        Bson filter = Filters.gt("startTime", LocalDateTime.now());
        return matchRepository.findAll().filter(filter);
    }

    public Flux<Match> getSubscribedMatches() {
        Flux<Match> matches = Flux.from(matchRepository.findAll());
        return matches.filter(match -> Boolean.TRUE.equals(match.isSubscribed()));
    }

    public Flux<Match> getMatchById(Long id) {
        Flux<Match> matches = Flux.from(matchRepository.findAll());
        return matches.filter(match -> match.getId().equals(id));
    }

    public Single<Match> updateSubscribe(Long id) {
        return matchRepository.updateSubscribe(id);
    }
}
