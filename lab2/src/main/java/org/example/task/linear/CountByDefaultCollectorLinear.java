package org.example.task.linear;

import org.example.entity.Match;
import org.example.entity.Team;
import org.example.entity.Tournament;
import org.example.enums.MatchType;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

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
        int length = 7;
        LocalDate date = LocalDate.ofEpochDay(378);
        LocalDateTime localDateTime = LocalDateTime.of(date, LocalTime.ofSecondOfDay(15 * 20 * 10));
        MatchType matchType = MatchType.DEATHMATCH;

        System.out.printf("""
                
                
                ----------------------CountByDefaultCollector----------------------
                
                countMatchesWithSpecifiedTeamsMembersCount=%d,
                countMatchesWithSpecifiedTeamsScores=%d,
                countMatchesWithSpecifiedStartDateAndTournamentNameLength=%d,
                countMatchesWithSpecifiedType=%d
                
                """.formatted(
                countMatchesWithSpecifiedTeamsMembersCount(matchArrayList, members1, members2, delay),
                countMatchesWithSpecifiedTeamsScores(matchArrayList, score1, score2),
                countMatchesWithSpecifiedStartDateAndTournamentNameLength(matchArrayList, localDateTime, length),
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
    private static long countMatchesWithSpecifiedTeamsMembersCount(@NotNull ArrayList<Match> matchArrayList, int members1,
                                                                   int members2, int delay) {
        try {
            TimeUnit.SECONDS.sleep(delay);
            LOGGER.log(Level.INFO, "Установлена задержка в %d секунд!".formatted(delay));
        } catch (InterruptedException ex) {
            LOGGER.log(Level.WARNING, "Не удалось установить задержку!");
        }
        return matchArrayList.stream().filter(match -> {
            Team team1 = match.getTeam1();
            Team team2 = match.getTeam2();
            return team1 != null && team1.getMembers().size() > members1 && team2 != null && team2.getMembers().size() > members2;
        }).toList().size();
    }

    /**
     * Метод рассчитывает количество матчей, у которых счет каждой из команд равен переданным значениям.
     *
     * @param matchArrayList список матчей, среди которых будет производиться расчет статистических данных.
     * @param score1         счет первой команды. У команды 1 должно быть количество очков, равное данному значению.
     * @param score2         счет второй команды. У команды 2 должно быть количество очков, равное данному значению.
     * @return Количество матчей, удовлетворяющих условию.
     */
    private static int countMatchesWithSpecifiedTeamsScores(@NotNull ArrayList<Match> matchArrayList, int score1, int score2) {

        return matchArrayList.stream().filter(match -> match.getScoreTeam1() == score1 && match.getScoreTeam2() == score2)
                .toList().size();
    }

    /**
     * Метод рассчитывает количество матчей, которые начинаются после определенной даты, а также у которых длина
     * названия турнира больше переданного значения.
     *
     * @param matchArrayList список матчей, среди которых будет производиться расчет статистических данных.
     * @param localDateTime  дата, после которой должен начаться матч.
     * @param length         длина названия турнира. У турнира длина названия должна быть больше данного значения.
     * @return Количество матчей, удовлетворяющих условию.
     */
    private static int countMatchesWithSpecifiedStartDateAndTournamentNameLength(@NotNull ArrayList<Match> matchArrayList,
                                                                                 LocalDateTime localDateTime, int length) {
        return matchArrayList.stream().filter(match -> {
            LocalDateTime startTime = match.getStartDateTime();
            Tournament tournament = match.getTournament();
            return startTime != null && startTime.isAfter(localDateTime) && tournament != null && tournament.name() != null && tournament.name().length() == length;
        }).toList().size();
    }

    /**
     * Метод рассчитывает количество матчей, у которых тип соответствует переданному значению.
     *
     * @param matchArrayList список матчей, среди которых будет производиться расчет статистических данных.
     * @param matchType      тип матча, которому должны соответствовать матчи.
     * @return Количество матчей, удовлетворяющих условию.
     */
    @Contract(pure = true)
    private static int countMatchesWithSpecifiedType(@NotNull ArrayList<Match> matchArrayList, MatchType matchType) {
        return matchArrayList.stream().filter(match -> match.getMatchType() != null && match.getMatchType() == matchType)
                .toList().size();
    }
}