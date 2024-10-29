package org.example.task;

import org.example.entity.Match;
import org.example.entity.Team;
import org.example.enums.MatchType;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * В данном классе вызываются методы для расчета статистических функций с использованием встроенного коллектора.
 */
public class CountByDefaultCollectorLinear {
    /**
     * Объект класса {@link Logger}, используемый для логирования.
     */
    private static final Logger LOGGER = Logger.getLogger(CountByDefaultCollectorLinear.class.getName());

    /**
     * Главный метод, в котором происходит вызов всех методов для расчета. Также здесь выводится результат работы
     * каждого из методов.
     *
     * @param matchArrayList список объектов класса {@link Match}, для которых необходимо рассчитать статистические
     *                       характеристики.
     * @param delay          количество секунд, на которое нужно установить задержку.
     */
    public static void countByDefaultCollectorLinear(ArrayList<Match> matchArrayList, int delay) {
        int members1 = 2, members2 = 3;
        int score1 = 5, score2 = 10;
        LocalDate date = LocalDate.ofEpochDay(378);
        LocalDateTime localDateTime = LocalDateTime.of(date, LocalTime.ofSecondOfDay(15 * 20 * 10));
        MatchType matchType = MatchType.DEATHMATCH;

        System.out.printf("""
                
                
                ----------------------CountByDefaultCollector----------------------
                
                countMatchesWithSpecifiedTeamsMembersCount=%s,
                countMatchesWithSpecifiedTeamsScores=%s,
                countMatchesWithSpecifiedStart=%s,
                countMatchesWithSpecifiedType=%s
                
                """.formatted(
                countMatchesWithSpecifiedTeamsMembersCount(matchArrayList, members1, members2, delay),
                countMatchesWithSpecifiedTeamsScores(matchArrayList, score1, score2),
                countMatchesWithSpecifiedStartDate(matchArrayList, localDateTime),
                countMatchesWithSpecifiedType(matchArrayList, matchType)
        ));
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
    private static Map<MatchType, Long> countMatchesWithSpecifiedTeamsMembersCount(@NotNull ArrayList<Match> matchArrayList, int members1,
                                                                                   int members2, int delay) {
        try {
            TimeUnit.MILLISECONDS.sleep(delay);
            LOGGER.log(Level.INFO, "Установлена задержка в %d миллисекунд!".formatted(delay));
        } catch (InterruptedException ex) {
            LOGGER.log(Level.WARNING, "Не удалось установить задержку!");
        }
        return matchArrayList.stream().filter(match -> {
            Team team1 = match.getTeam1();
            Team team2 = match.getTeam2();
            return team1 != null && team1.getMembers().size() > members1 && team2 != null && team2.getMembers().size() > members2;
        }).collect(Collectors.groupingBy(Match::getMatchType, Collectors.counting()));
    }

    /**
     * Метод рассчитывает количество матчей, у которых счет каждой из команд равен переданным значениям.
     *
     * @param matchArrayList список матчей, среди которых будет производиться расчет статистических данных.
     * @param score1         счет первой команды. У команды 1 должно быть количество очков, равное данному значению.
     * @param score2         счет второй команды. У команды 2 должно быть количество очков, равное данному значению.
     * @return Количество матчей, удовлетворяющих условию.
     */
    private static Map<MatchType, Long> countMatchesWithSpecifiedTeamsScores(@NotNull ArrayList<Match> matchArrayList, int score1, int score2) {

        return matchArrayList.stream().filter(match -> match.getScoreTeam1() == score1 && match.getScoreTeam2() == score2)
                .collect(Collectors.groupingBy(Match::getMatchType, Collectors.counting()));
    }

    /**
     * Метод рассчитывает количество матчей, которые начинаются после определенной даты.
     *
     * @param matchArrayList список матчей, среди которых будет производиться расчет статистических данных.
     * @param localDateTime  дата, после которой должен начаться матч.
     * @return Количество матчей, удовлетворяющих условию.
     */
    private static Map<MatchType, Long> countMatchesWithSpecifiedStartDate(@NotNull ArrayList<Match> matchArrayList,
                                                                           LocalDateTime localDateTime) {
        return matchArrayList.stream().filter(match -> {
            LocalDateTime startTime = match.getStartDateTime();
            return startTime != null && startTime.isAfter(localDateTime);
        }).collect(Collectors.groupingBy(Match::getMatchType, Collectors.counting()));
    }

    /**
     * Метод рассчитывает количество матчей, у которых тип соответствует переданному значению.
     *
     * @param matchArrayList список матчей, среди которых будет производиться расчет статистических данных.
     * @param matchType      тип матча, которому должны соответствовать матчи.
     * @return Количество матчей, удовлетворяющих условию.
     */
    @Contract(pure = true)
    private static Map<MatchType, Long> countMatchesWithSpecifiedType(@NotNull ArrayList<Match> matchArrayList, MatchType matchType) {
        return matchArrayList.stream().filter(match -> match.getMatchType() != null && match.getMatchType() == matchType)
                .collect(Collectors.groupingBy(Match::getMatchType, Collectors.counting()));
    }
}
