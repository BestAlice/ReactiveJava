package org.example.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Данное перечисление содержит все типы матчей, которые могут проводиться в рамках турниров.
 */
@Getter
@AllArgsConstructor
public enum MatchType {
    /**
     * Deathmatch - каждый сам за себя.
     */
    DEATHMATCH("Deathmatch"),

    /**
     * Захват флага - одна команда защищает флаг, а вторая пытается его захватить.
     */
    FLAG_CAPTURE("Flag Capture"),

    /**
     * Командный бой - команды играют до тех пор, пока одна из команд не наберет определенное количество очков.
     */
    TEAM_BATTLE("Team Battle");

    private final String type;
}