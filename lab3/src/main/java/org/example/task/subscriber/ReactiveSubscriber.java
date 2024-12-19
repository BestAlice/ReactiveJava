package org.example.task.subscriber;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.FlowableSubscriber;
import org.example.entity.Match;
import org.example.enums.MatchType;
import org.jetbrains.annotations.NotNull;
import org.reactivestreams.Subscription;

public class ReactiveSubscriber implements FlowableSubscriber<Match> {
    private Subscription subscription;

    private static final long count = 20;

    private int successElems = 0;

    private final String functionName;

    public ReactiveSubscriber(String functionName) {
        this.functionName = functionName;
    }

    @Override
    public void onNext(Match match) {
        System.out.printf("Получен Match: %s\n", match);
        if (match.getMatchType() != null && match.getMatchType() == MatchType.DEATHMATCH) {
            successElems++;
        }

        this.subscription.request(count);
    }

    @Override
    public void onError(Throwable throwable) {
        if (throwable != null) {
            throwable.printStackTrace();
        }
    }

    @Override
    public void onComplete() {
        System.out.printf("%s: %d", this.functionName, this.successElems);
    }

    @Override
    public void onSubscribe(@NotNull @NonNull Subscription s) {
        this.subscription = s;
        s.request(count);
    }
}