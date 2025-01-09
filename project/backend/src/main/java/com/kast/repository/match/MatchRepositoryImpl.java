package com.kast.repository.match;

import com.kast.entity.match.Match;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import com.mongodb.reactivestreams.client.FindPublisher;
import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoCollection;
import io.reactivex.rxjava3.core.Single;
import jakarta.inject.Singleton;
import org.bson.conversions.Bson;
import org.jetbrains.annotations.NotNull;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Singleton
public class MatchRepositoryImpl implements MatchRepository {
    private final MongoClient mongoClient;

    public MatchRepositoryImpl(MongoClient mongoClient) {
        this.mongoClient = mongoClient;
    }

    @Override
    public Single<Match> save(Match match) {
        return Single
                .fromPublisher(getCollection().insertOne(match))
                .map(success -> match);
    }

    @Override
    public Single<Match> updateMaps(@NotNull Long id, @NotNull Match match) {
        Bson targetMatch = Filters.eq("id", id);
        Bson updateFields = Updates.set("maps", match.getMaps());
        return Single
                .fromPublisher(getCollection().updateOne(targetMatch, updateFields))
                .map(success -> match);
    }

    @Override
    public Single<Match> updateSubscribe(Long id) {
        Bson targetMatch = Filters.eq("id", id);
        Match targetMatchObj = findPublisherToFlux(getCollection().find(targetMatch)).blockFirst();
        Bson updateFields = Updates.set("subscribed", !targetMatchObj.isSubscribed());
        return Single
                .fromPublisher(getCollection().updateOne(targetMatch, updateFields))
                .map(success -> targetMatchObj);
    }

    @Override
    public Flux<Match> findByStartTimeBefore(LocalDateTime startTime) {
        Bson filter = Filters.lte("startTime", startTime);
        return findPublisherToFlux(findAll().filter(filter));
    }

    @Override
    public Mono<Boolean> existsById(Long id) {
        Bson filter = Filters.eq("id", id);
        return findPublisherToFlux(findAll().filter(filter)).hasElements();
    }

    @NotNull
    @Override
    public FindPublisher<Match> findAll() {
        return getCollection().find();
    }

    // https://stackoverflow.com/a/64910258
    @NotNull
    private Flux<Match> findPublisherToFlux(FindPublisher<Match> findPublisher) {
        return Flux.from(findPublisher);
    }

    @NotNull
    private MongoCollection<Match> getCollection() {
        return mongoClient
                .getDatabase("kast")
                .getCollection("matches", Match.class);
    }
}
