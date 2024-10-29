package org.example.util;

import org.example.entity.Match;
import org.example.entity.Team;
import org.example.entity.Tournament;
import org.example.enums.MatchType;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.IntFunction;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Данный класс содержит методы для генерации объектов.
 */
public class Generator {
    /**
     * Константа, задающая верхнюю границу для случайного целого числа.
     */
    private static final int MAX_GENERATED_INT = 40;

    /**
     * Константа, задающая нижнюю границу длины для названия команды.
     */
    private static final int MIN_TEAM_NAME_LEN = 5;

    /**
     * Константа, задающая верхнюю границу длины для названия команды.
     */
    private static final int MAX_TEAM_NAME_LEN = 10;

    /**
     * Данный метод позволяет сгенерировать необходимое количество объектов класса {@link Match}.
     *
     * @param count количество объектов, которое необходимо сгенерировать.
     * @return Список объектов класса {@link Match}, количество которых равно запрашиваемому.
     */
    public static @NotNull ArrayList<Match> generateMatchCollection(int count) {
        return (ArrayList<Match>) generateCollection(count, o -> generateMatch());
    }

    /**
     * Данный метод позволяет вернуть коллекцию объектов, состоящую из необходимого количества объектов.
     *
     * @param count       количество объектов, которое необходимо сгенерировать.
     * @param intFunction функция, вызывающая необходимый генератор.
     * @return Список объектов, количество которых равно запрашиваемому, а тип объектов соответствует переданной функции
     * генерации.
     */
    private static ArrayList<?> generateCollection(int count, IntFunction<?> intFunction) {
        return IntStream.range(0, count)
                .mapToObj(intFunction)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    /**
     * Данный метод позволяет сгенерировать случайный матч.
     *
     * @return Случайно сгенерированный объект класса {@link Match}, описывающий случайный матч.
     */
    private static @NotNull Match generateMatch() {
        Team team1 = isReturnNull() ? null : generateTeam();
        Team team2 = isReturnNull() ? null : generateTeam();

        LocalDateTime startDateTime = generateLocalDateTime();
        LocalDateTime endDateTime = generateLocalDateTime();

        Tournament tournament = isReturnNull() ? null : generateTournament();

        int scoreTeam1 = generateInt();
        int scoreTeam2 = generateInt();

        MatchType matchType = generateMatchType();

        String map = generateString(generateInt());

        return new Match(team1, team2, startDateTime, endDateTime, tournament, scoreTeam1, scoreTeam2, matchType, map);
    }

    /**
     * Данный метод позволяет сгенерировать случайный турнир.
     *
     * @return Случайно сгенерированный объект класса {@link Tournament}, описывающий случайный турнир.
     */
    @Contract(" -> new")
    private static @NotNull Tournament generateTournament() {
        String name = generateString(generateInt());
        String place = generateString(generateInt());

        LocalDate startDate = generateLocalDate();
        LocalDate endDate = generateLocalDate();

        if (startDate != null && endDate != null && startDate.isBefore(endDate)) {
            return new Tournament(name, place, startDate, endDate);
        }

        return new Tournament(name, place, endDate, startDate);
    }

    /**
     * Данный метод позволяет сгенерировать случайную команду.
     *
     * @return Случайно сгенерированный объект класса {@link Team}, описывающий случайную команду.
     */
    private static @NotNull Team generateTeam() {
        String name = generateString(generateIntInRange(MIN_TEAM_NAME_LEN, MAX_TEAM_NAME_LEN));

        int membersCount = generateInt();
        List<String> members = isReturnNull() ? new ArrayList<>() : IntStream.range(0, membersCount).mapToObj(memb -> generateString(generateInt())).toList();

        return new Team(name, members, generateInt(), generateInt());
    }

    /**
     * Данный метод позволяет сгенерировать случайный тип матча.
     *
     * @return Если условие для возвращения <code>null</code> - истинное, то возвращается <code>null</code>. Иначе -
     * случайно сгенерированный элемент перечисления {@link MatchType}.
     */
    private static @Nullable MatchType generateMatchType() {
        MatchType[] types = MatchType.values();
        return types[generateIntInRange(0, types.length - 1)];
    }

    /**
     * Данный метод позволяет сгенерировать случайное целое число в заданном диапазоне.
     *
     * @param min нижняя граница для генерации числа.
     * @param max верхняя граница (включительно) для генерации числа.
     * @return Случайно сгенерированное целое число в заданном диапазоне.
     */
    private static int generateIntInRange(int min, int max) {
        return new Random().nextInt(max - min + 1) + min;
    }

    /**
     * Данный метод позволяет сгенерировать случайное целое число от 1 до MAX_GENERATED_INT включительно.
     *
     * @return Случайно сгенерированное целое число от 1 до MAX_GENERATED_INT включительно.
     */
    private static int generateInt() {
        return new Random().nextInt(MAX_GENERATED_INT) + 1;
    }

    /**
     * Данный метод генерирует случайное число и проверяет его на соответствие условию.
     * </br>
     * Используется другими генераторами для того, чтоб определить, присвоить переменной <code>null</code> или некоторое
     * сгенерированное значение.
     *
     * @return Если условие истинно - <code>true</code>. Иначе - <code>false</code>.
     */
    private static boolean isReturnNull() {
        return generateIntInRange(0, 99) > 49;
    }

    /**
     * Данный метод позволяет сгенерировать случайную строку необходимой длины.
     *
     * @param len необходимая длина строки.
     * @return Если условие для возвращения <code>null</code> - истинное, то возвращается <code>null</code>. Иначе -
     * сгенерированная случайным образом строка запрашиваемой длины.
     */
    @Contract("_ -> new")
    private static @Nullable String generateString(int len) {
        String candidateChars = "AaBbCcDdEeFfGgHhIiJjKkLlMmNnOoPpQqRrSsTtUuVbWwXxYyZz1234567890!@#$%^&*()?{}[]<>";

        Random rand = new Random();
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < len; i++) {
            int randIndex = rand.nextInt(candidateChars.length());
            res.append(candidateChars.charAt(randIndex));
        }
        return isReturnNull() ? null : res.toString();
    }

    /**
     * Данный метод позволяет сгенерировать случайным образом объект класса {@link LocalDateTime}.
     *
     * @return Если условие для возвращения <code>null</code> - истинное, то возвращается <code>null</code>. Иначе -
     * сгенерированный случайным образом объект класса {@link LocalDateTime}.
     */
    private static @Nullable LocalDateTime generateLocalDateTime() {
        int startSeconds = LocalTime.MIN.toSecondOfDay();
        int endSeconds = LocalTime.MAX.toSecondOfDay();
        LocalTime time = LocalTime.ofSecondOfDay(generateIntInRange(startSeconds, endSeconds));
        LocalDate date = generateLocalDate();

        if (date == null) {
            return null;
        }

        return isReturnNull() ? null : LocalDateTime.of(date, time);
    }

    /**
     * Данный метод позволяет сгенерировать случайным образом объект класса {@link LocalDate}.
     *
     * @return Если условие для возвращения <code>null</code> - истинное, то возвращается <code>null</code>. Иначе -
     * сгенерированный случайным образом объект класса {@link LocalDate}.
     */
    @Contract(" -> new")
    private static @Nullable LocalDate generateLocalDate() {
        return isReturnNull() ? null : LocalDate.ofEpochDay(generateIntInRange(50, 12000));
    }
}