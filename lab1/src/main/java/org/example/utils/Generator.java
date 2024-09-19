package org.example.utils;

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
import java.util.stream.IntStream;

public class Generator {
    private static final int MAX_GENERATED_INT = 40;
    private static final int MIN_TEAM_NAME_LEN = 5;
    private static final int MAX_TEAM_NAME_LEN = 10;

    public static @NotNull Match generateMatch() {
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

    @Contract(" -> new")
    public static @NotNull Tournament generateTournament() {
        String name = generateString(generateInt());
        String place = generateString(generateInt());

        LocalDate startDate = generateLocalDate();
        LocalDate endDate = generateLocalDate();

        if (startDate != null && endDate != null && startDate.isBefore(endDate)) {
            return new Tournament(name, place, startDate, endDate);
        }

        return new Tournament(name, place, endDate, startDate);
    }

    public static @NotNull Team generateTeam() {
        String name = generateString(generateIntInRange(MIN_TEAM_NAME_LEN, MAX_TEAM_NAME_LEN));

        int membersCount = generateInt();
        List<String> members = isReturnNull() ? new ArrayList<>() : IntStream.range(0, membersCount).mapToObj(memb -> generateString(generateInt())).toList();

        return new Team(name, members, generateInt(), generateInt());
    }

    private static @Nullable MatchType generateMatchType() {
        MatchType[] types = MatchType.values();
        return isReturnNull() ? null : types[generateIntInRange(0, types.length)];
    }

    private static int generateIntInRange(int min, int max) {
        return new Random().nextInt(max - min + 1) + min;
    }

    private static int generateInt() {
        return new Random().nextInt(MAX_GENERATED_INT) + 1;
    }

    private static boolean isReturnNull() {
        return generateIntInRange(0, 99) > 49;
    }

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

    @Contract(" -> new")
    private static @Nullable LocalDate generateLocalDate() {
        return isReturnNull() ? null : LocalDate.ofEpochDay(generateIntInRange(50, 12000));
    }
}