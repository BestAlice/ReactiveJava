package org.example.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.example.enums.MatchType;

import java.time.LocalDateTime;

/**
 * Класс, содержащий информацию о матче.
 */
@Data
@AllArgsConstructor
public class Match {
    /**
     * Объект класса {@link Team}, содержащий информацию о первой команде.
     */
    private Team team1;

    /**
     * Объект класса {@link Team}, содержащий информацию о второй команде.
     */
    private Team team2;

    /**
     * Объект класса {@link LocalDateTime}, содержащий информацию о дате и времени начала матча.
     */
    private LocalDateTime startDateTime;

    /**
     * Объект класса {@link LocalDateTime}, содержащий информацию о дате и времени окончания матча.
     */
    private LocalDateTime endDateTime;

    /**
     * Объект класса {@link Tournament}, содержащий информацию о турнире, в рамках которого проходит матч.
     */
    private Tournament tournament;

    /**
     * Счет первой команды - количество очков, которое заработала первая команда.
     */
    private int scoreTeam1;

    /**
     * Счет второй команды - количество очков, которое заработала вторая команда.
     */
    private int scoreTeam2;

    /**
     * Элемент перечисления {@link MatchType} - тип матча.
     */
    private MatchType matchType;

    /**
     * Название карты, на которой проходит матч.
     */
    private String map;
}