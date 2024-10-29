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
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * В данном классе вызываются методы для расчета статистических функций итерационным способом.
 */
public class CountByLoop {
    /**
     * Объект класса {@link Logger}, используемый для логирования.
     */
    private static final Logger LOGGER = Logger.getLogger(CountByDefaultCollectorParallel.class.getName());

    /**
     * Главный метод, в котором происходит вызов всех методов для расчета. Также здесь выводится результат работы
     * каждого из методов.
     *
     * @param matchArrayList список объектов класса {@link Match}, для которых необходимо рассчитать статистические
     *                       характеристики.
     */
    public static void countByLoop(ArrayList<Match> matchArrayList, int delay) {
        int members1 = 2, members2 = 3;
        int score1 = 5, score2 = 10;
        LocalDate date = LocalDate.ofEpochDay(378);
        LocalDateTime localDateTime = LocalDateTime.of(date, LocalTime.ofSecondOfDay(15 * 20 * 10));
        MatchType matchType = MatchType.DEATHMATCH;

        System.out.printf("""
                
                
                ----------------------CountByLoop----------------------
                
                countMatchesWithSpecifiedTeamsMembersCount=%s,
                countMatchesWithSpecifiedTeamsScores=%s,
                countMatchesWithSpecifiedStartDate=%s,
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
    private static @NotNull Map<MatchType, Integer> countMatchesWithSpecifiedTeamsMembersCount(@NotNull ArrayList<Match> matchArrayList, int members1,
                                                                                             int members2, int delay) {
        try {
            TimeUnit.MILLISECONDS.sleep(delay);
            LOGGER.log(Level.INFO, "Установлена задержка в %d миллисекунд!".formatted(delay));
        } catch (InterruptedException ex) {
            LOGGER.log(Level.WARNING, "Не удалось установить задержку!");
        }
        Map<MatchType, Integer> result = new HashMap<>();
        for (Match match : matchArrayList) {
            Team team1 = match.getTeam1();
            Team team2 = match.getTeam2();
            MatchType matchType = match.getMatchType();

            if (team1 != null && team1.getMembers().size() > members1 && team2 != null && team2.getMembers().size() > members2) {
                if (!result.containsKey(matchType)) {
                    result.put(matchType, 0);
                }
                result.put(matchType, result.get(matchType) + 1);
            }
        }
        return result;
    }

    /**
     * Метод рассчитывает количество матчей, у которых счет каждой из команд равен переданным значениям.
     *
     * @param matchArrayList список матчей, среди которых будет производиться расчет статистических данных.
     * @param score1         счет первой команды. У команды 1 должно быть количество очков, равное данному значению.
     * @param score2         счет второй команды. У команды 2 должно быть количество очков, равное данному значению.
     * @return Количество матчей, удовлетворяющих условию.
     */
    private static @NotNull Map<MatchType, Integer> countMatchesWithSpecifiedTeamsScores(@NotNull ArrayList<Match> matchArrayList, int score1, int score2) {
        Map<MatchType, Integer> result = new HashMap<>();
        for (Match match : matchArrayList) {
            MatchType matchType = match.getMatchType();
            int teamScore1 = match.getScoreTeam1();
            int teamScore2 = match.getScoreTeam2();
            if (teamScore1 != score1 || teamScore2 != score2) {
                continue;
            }

            if (!result.containsKey(matchType)) {
                result.put(matchType, 0);
            }
            result.put(matchType, result.get(matchType) + 1);
        }
        return result;
    }

    /**
     * Метод рассчитывает количество матчей, которые начинаются после определенной даты.
     *
     * @param matchArrayList список матчей, среди которых будет производиться расчет статистических данных.
     * @param localDateTime  дата, после которой должен начаться матч.
     * @return Количество матчей, удовлетворяющих условию.
     */
    private static @NotNull Map<MatchType, Integer> countMatchesWithSpecifiedStartDate(@NotNull ArrayList<Match> matchArrayList,
                                                                                           LocalDateTime localDateTime) {
        Map<MatchType, Integer> result = new HashMap<>();
        for (Match match : matchArrayList) {
            MatchType matchType = match.getMatchType();
            LocalDateTime startTime = match.getStartDateTime();
            if (startTime == null || !startTime.isAfter(localDateTime)) {
                continue;
            }

            if (!result.containsKey(matchType)) {
                result.put(matchType, 0);
            }
            result.put(matchType, result.get(matchType) + 1);
        }
        return result;
    }

    /**
     * Метод рассчитывает количество матчей, у которых тип соответствует переданному значению.
     *
     * @param matchArrayList список матчей, среди которых будет производиться расчет статистических данных.
     * @param matchType      тип матча, которому должны соответствовать матчи.
     * @return Количество матчей, удовлетворяющих условию.
     */
    @Contract(pure = true)
    private static @NotNull Map<MatchType, Integer> countMatchesWithSpecifiedType(@NotNull ArrayList<Match> matchArrayList, MatchType matchType) {
        Map<MatchType, Integer> result = new HashMap<>();
        for (Match match : matchArrayList) {
            MatchType matchType1 = match.getMatchType();
            if (matchType1 == null || matchType1 != matchType) {
                continue;
            }

            if (!result.containsKey(matchType1)) {
                result.put(matchType1, 0);
            }
            result.put(matchType1, result.get(matchType1) + 1);
        }
        return result;
    }
}
