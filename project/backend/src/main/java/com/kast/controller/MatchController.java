package com.kast.controller;

import com.kast.entity.match.Match;
import com.kast.service.MatchService;
import com.mongodb.reactivestreams.client.FindPublisher;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.annotation.Post;
import io.reactivex.rxjava3.core.Single;
import reactor.core.publisher.Flux;

@Controller("/match")
public class MatchController {
    private final MatchService matchService;

    public MatchController(MatchService matchService) {
        this.matchService = matchService;
    }

    @Get("/ongoing")
    public FindPublisher<Match> getOngoingMatches() {
        return matchService.getOngoingMatches();
    }

    @Get("/upcoming")
    public FindPublisher<Match> getUpcomingMatches() {
        return matchService.getUpcomingMatches();
    }

    @Get("/subscriptions")
    public Flux<Match> getSubscribedMatches() {
        return matchService.getSubscribedMatches();
    }

    @Post("/subscribe/{id}")
    public Single<Match> updateSubscribe(@PathVariable Long id) {
        return matchService.updateSubscribe(id);
    }

    @Get("/{id}")
    public Flux<Match> getMatchById(@PathVariable Long id) {
        return matchService.getMatchById(id);
    }
}
