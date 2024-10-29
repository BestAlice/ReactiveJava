package org.example.task;

import org.example.entity.Match;
import org.example.entity.Team;
import org.example.enums.MatchType;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * В данном классе вызываются методы для расчета статистических функций с использованием собственного коллектора.
 */
public class CustomForkJoinPoolTask extends RecursiveTask<Integer> {
    /**
     * Объект класса {@link Logger}, используемый для логирования.
     */
    private final Logger LOGGER = Logger.getLogger(CustomForkJoinPoolTask.class.getName());

    private final List<Match> matchArrayList;

    private final int delay;

    private final int members1;

    private final int members2;

    private final int score1;

    private final int score2;

    private final LocalDateTime localDateTime;

    private final MatchType matchType;

    /**
     * Главный метод, в котором происходит вызов всех методов для расчета. Также здесь выводится результат работы
     * каждого из методов.
     *
     * @param matchArrayList список объектов класса {@link Match}, для которых необходимо рассчитать статистические
     *                       характеристики.
     * @param delay          количество секунд, на которое нужно установить задержку.
     */
    public CustomForkJoinPoolTask(List<Match> matchArrayList, int delay) {
        this.matchArrayList = matchArrayList;
        this.delay = delay;
        this.members1 = 2;
        this.members2 = 3;
        this.score1 = 5;
        this.score2 = 10;
        this.localDateTime = LocalDateTime.of(LocalDate.ofEpochDay(378), LocalTime.ofSecondOfDay(15 * 20 * 10));
        this.matchType = MatchType.DEATHMATCH;
    }

    @Override
    protected Integer compute() {
        if (matchArrayList.size() < 536) {
            return countMatchesWithSpecifiedTeamsMembersCount(matchArrayList, members1, members2, delay) +
                    countMatchesWithSpecifiedTeamsScores(matchArrayList, score1, score2) +
                    countMatchesWithSpecifiedStartDate(matchArrayList, localDateTime) +
                    countMatchesWithSpecifiedType(matchArrayList, matchType);
        }
        int midIndex = ((matchArrayList.size() / 2) - (((matchArrayList.size() % 2) > 0) ? 0 : 1));

        CustomForkJoinPoolTask firstHalfArrayValueSumCounter = new CustomForkJoinPoolTask(matchArrayList.subList(0, midIndex), delay);
        CustomForkJoinPoolTask secondHalfArrayValueSumCounter = new CustomForkJoinPoolTask(matchArrayList.subList(midIndex, matchArrayList.size()), delay);
        firstHalfArrayValueSumCounter.fork();
        secondHalfArrayValueSumCounter.fork();
        return firstHalfArrayValueSumCounter.join() + secondHalfArrayValueSumCounter.join();
    }

    /**
     * Метод рассчитывает количество матчей, у команд которых количество участников больше переданных значений.
     *
     * @param matchArrayList список матчей, среди которых будет производиться расчет статистических данных.
     * @param members1       количество участников в первой команде. В команде 1 должно быть участников больше, чем
     *                       данное значение.
     * @param members2       количество участников во второй команде. В команде 2 должно быть участников больше, чем
     *                       данное значение.
     * @return Количество матчей, удовлетворяющих условию.
     */
    private int countMatchesWithSpecifiedTeamsMembersCount(@NotNull List<Match> matchArrayList, int members1,
                                                           int members2, int delay) {
        try {
            TimeUnit.SECONDS.sleep(delay);
            LOGGER.log(Level.INFO, "Установлена задержка в %d секунд!".formatted(delay));
        } catch (InterruptedException ex) {
            LOGGER.log(Level.WARNING, "Не удалось установить задержку!");
        }

        int count = 0;
        for (Match match : matchArrayList) {
            Team team1 = match.getTeam1();
            Team team2 = match.getTeam2();
            if (team1 != null && team1.getMembers().size() > members1 && team2 != null && team2.getMembers().size() > members2) {
                count++;
            }
        }
        return count;
    }

    /**
     * Метод рассчитывает количество матчей, у которых счет каждой из команд равен переданным значениям.
     *
     * @param matchArrayList список матчей, среди которых будет производиться расчет статистических данных.
     * @param score1         счет первой команды. У команды 1 должно быть количество очков, равное данному значению.
     * @param score2         счет второй команды. У команды 2 должно быть количество очков, равное данному значению.
     * @return Количество матчей, удовлетворяющих условию.
     */
    private int countMatchesWithSpecifiedTeamsScores(@NotNull List<Match> matchArrayList, int score1, int score2) {
        int count = 0;
        for (Match match : matchArrayList) {
            if (match.getScoreTeam1() == score1 && match.getScoreTeam2() == score2) {
                count++;
            }
        }
        return count;
    }

    /**
     * Метод рассчитывает количество матчей, которые начинаются после определенной даты.
     *
     * @param matchArrayList список матчей, среди которых будет производиться расчет статистических данных.
     * @param localDateTime  дата, после которой должен начаться матч.
     * @return Количество матчей, удовлетворяющих условию.
     */
<<<<<<< HEAD:lab2/src/main/java/org/example/task/CustomForkJoinPoolTask.java
    private int countMatchesWithSpecifiedStartDate(@NotNull List<Match> matchArrayList,
                                                   LocalDateTime localDateTime) {
=======
    private int countMatchesWithSpecifiedStartDateAndTournamentNameLength(@NotNull List<Match> matchArrayList,
                                                                          LocalDateTime localDateTime, int length) {
>>>>>>> c172c38a27aea05f171b4ad238ec12fbc0e50468:lab2/src/main/java/org/example/task/forkjoinpool/CustomForkJoinPoolTask.java
        int count = 0;
        for (Match match : matchArrayList) {
            LocalDateTime startTime = match.getStartDateTime();
            if (startTime != null && startTime.isAfter(localDateTime)) {
                count++;
            }
        }
        return count;
    }

    /**
     * Метод рассчитывает количество матчей, у которых тип соответствует переданному значению.
     *
     * @param matchArrayList список матчей, среди которых будет производиться расчет статистических данных.
     * @param matchType      тип матча, которому должны соответствовать матчи.
     * @return Количество матчей, удовлетворяющих условию.
     */
    @Contract(pure = true)
    private int countMatchesWithSpecifiedType(@NotNull List<Match> matchArrayList, MatchType matchType) {
        int count = 0;
        for (Match match : matchArrayList) {
            if (match.getMatchType() != null && match.getMatchType() == matchType) {
                count++;
            }
        }
        return count;
    }
}
