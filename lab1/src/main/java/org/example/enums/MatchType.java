package org.example.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MatchType {
    DEATHMATCH("Deathmatch"),
    FLAG_CAPTURE("Flag Capture"),
    TEAM_BATTLE("Team Battle");

    private final String type;
}