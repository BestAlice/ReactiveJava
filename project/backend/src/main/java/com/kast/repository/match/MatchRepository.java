package com.kast.repository.match;

import com.kast.entity.match.Match;
import com.mongodb.reactivestreams.client.FindPublisher;
import io.micronaut.core.annotation.NonNull;
import io.reactivex.rxjava3.core.Single;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

public interface MatchRepository {
    Single<Match> save(@NonNull @NotNull @Valid Match match);

    Single<Match> updateMaps(@NonNull @NotNull @Valid Long id, @NonNull @NotNull @Valid Match match);

    Single<Match> updateSubscribe(@NonNull @NotNull @Valid Long id);

    // https://habr.com/ru/companies/otus/articles/704488/
    Flux<Match> findByStartTimeBefore(LocalDateTime startTime);

    Mono<Boolean> existsById(Long id);

    FindPublisher<Match> findAll();
}