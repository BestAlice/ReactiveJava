package org.example.task;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import org.example.entity.Match;
import org.example.enums.MatchType;
import org.jetbrains.annotations.Contract;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

/**
 * @author Kirill "Tamada" Simovin
 */
public class ReactiveTask {
    /**
     * Объект класса {@link Logger}, используемый для логирования.
     */
    private final Logger LOGGER = Logger.getLogger(ReactiveTask.class.getName());

    private final Observable<Match> observable;

    private final int delay;

    private final int members1;

    private final int members2;

    private final int score1;

    private final int score2;

    private final LocalDateTime localDateTime;

    private final MatchType matchType;

    public ReactiveTask(ArrayList<Match> matchArrayList, int delay) {
        observable = Observable.fromIterable(matchArrayList)
                .subscribeOn(Schedulers.io())
                .buffer(20)
                .flatMap(Observable::fromIterable);
        this.delay = delay;
        this.members1 = 2;
        this.members2 = 3;
        this.score1 = 5;
        this.score2 = 10;
        this.localDateTime = LocalDateTime.of(LocalDate.ofEpochDay(378), LocalTime.ofSecondOfDay(15 * 20 * 10));
        this.matchType = MatchType.DEATHMATCH;
    }

    public void executeReactive() {
        countMatchesWithSpecifiedTeamsMembersCount(members1, members2, delay);
        countMatchesWithSpecifiedTeamsScores(score1, score2);
        countMatchesWithSpecifiedStartDate(localDateTime);
        countMatchesWithSpecifiedType(matchType);
    }

    /**
     * Метод рассчитывает количество матчей, у команд которых количество участников больше переданных значений.
     *
     * @param members1 количество участников в первой команде. В команде 1 должно быть участников больше, чем
     *                 данное значение.
     * @param members2 количество участников во второй команде. В команде 2 должно быть участников больше, чем
     *                 данное значение.
     */
    private void countMatchesWithSpecifiedTeamsMembersCount(int members1,
                                                            int members2, int delay) {
        observable.delay(delay, TimeUnit.MILLISECONDS)
                .flatMap(mtch -> Observable.just(mtch)
                        .subscribeOn(Schedulers.computation())
                        .filter(match -> match.getTeam1() != null && match.getTeam1().getMembers().size() > members1 &&
                                match.getTeam2() != null && match.getTeam2().getMembers().size() > members2))
                .count()
                .subscribe(count -> System.out.printf("\ncountMatchesWithSpecifiedTeamsMembersCount: %d", count));
    }

    /**
     * Метод рассчитывает количество матчей, у которых счет каждой из команд равен переданным значениям.
     *
     * @param score1 счет первой команды. У команды 1 должно быть количество очков, равное данному значению.
     * @param score2 счет второй команды. У команды 2 должно быть количество очков, равное данному значению.
     */
    private void countMatchesWithSpecifiedTeamsScores(int score1, int score2) {
        observable.flatMap(mtch -> Observable.just(mtch)
                        .subscribeOn(Schedulers.computation())
                        .filter(match -> match.getScoreTeam1() == score1 && match.getScoreTeam2() == score2))
                .count()
                .subscribe(count -> System.out.printf("\ncountMatchesWithSpecifiedTeamsScores: %d", count));
    }

    /**
     * Метод рассчитывает количество матчей, которые начинаются после определенной даты.
     *
     * @param localDateTime дата, после которой должен начаться матч.
     */
    private void countMatchesWithSpecifiedStartDate(LocalDateTime localDateTime) {
        observable.flatMap(mtch -> Observable.just(mtch)
                        .subscribeOn(Schedulers.computation())
                        .filter(match -> {
                            LocalDateTime startTime = match.getStartDateTime();
                            return startTime != null && startTime.isAfter(localDateTime);
                        }))
                .count()
                .subscribe(count -> System.out.printf("\ncountMatchesWithSpecifiedStartDate: %d", count));
    }

    /**
     * Метод рассчитывает количество матчей, у которых тип соответствует переданному значению.
     *
     * @param matchType тип матча, которому должны соответствовать матчи.
     */
    @Contract(pure = true)
    private void countMatchesWithSpecifiedType(MatchType matchType) {
        observable.flatMap(mtch -> Observable.just(mtch)
                        .subscribeOn(Schedulers.computation())
                        .filter(match -> match.getMatchType() != null && match.getMatchType() == matchType))
                .count()
                .subscribe(count -> System.out.printf("\ncountMatchesWithSpecifiedType: %d", count));
    }
}