package org.example.task;

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

/**
 * В данном классе вызываются методы для расчета статистических функций итерационным способом.
 */
public class CountByLoop {
    /**
     * Главный метод, в котором происходит вызов всех методов для расчета. Также здесь выводится результат работы
     * каждого из методов.
     *
     * @param matchArrayList список объектов класса {@link Match}, для которых необходимо рассчитать статистические
     *                       характеристики.
     */
    public static void countByLoop(ArrayList<Match> matchArrayList) {
        int members1 = 2, members2 = 3;
        int score1 = 5, score2 = 10;
        int length = 7;
        LocalDate date = LocalDate.ofEpochDay(378);
        LocalDateTime localDateTime = LocalDateTime.of(date, LocalTime.ofSecondOfDay(15 * 20 * 10));
        MatchType matchType = MatchType.DEATHMATCH;

        System.out.printf("""
                
                
                ----------------------CountByLoop----------------------
                
                countMatchesWithSpecifiedTeamsMembersCount=%d,
                countMatchesWithSpecifiedTeamsScores=%d,
                countMatchesWithSpecifiedStartDateAndTournamentNameLength=%d,
                countMatchesWithSpecifiedType=%d
                
                """.formatted(
                countMatchesWithSpecifiedTeamsMembersCount(matchArrayList, members1, members2),
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
    private static int countMatchesWithSpecifiedTeamsMembersCount(@NotNull ArrayList<Match> matchArrayList, int members1,
                                                                  int members2) {
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
    private static int countMatchesWithSpecifiedTeamsScores(@NotNull ArrayList<Match> matchArrayList, int score1, int score2) {
        int count = 0;
        for (Match match : matchArrayList) {
            if (match.getScoreTeam1() == score1 && match.getScoreTeam2() == score2) {
                count++;
            }
        }
        return count;
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
        int count = 0;
        for (Match match : matchArrayList) {
            LocalDateTime startTime = match.getStartDateTime();
            Tournament tournament = match.getTournament();
            if (startTime != null && startTime.isAfter(localDateTime) && tournament != null && tournament.name() != null && tournament.name().length() == length) {
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
    private static int countMatchesWithSpecifiedType(@NotNull ArrayList<Match> matchArrayList, MatchType matchType) {
        int count = 0;
        for (Match match : matchArrayList) {
            if (match.getMatchType() != null && match.getMatchType() == matchType) {
                count++;
            }
        }
        return count;
    }
}
