package org.example.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.example.enums.MatchType;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class Match {
    private Team team1;

    private Team team2;

    private LocalDateTime startDateTime;

    private LocalDateTime endDateTime;

    private Tournament tournament;

    private int scoreTeam1;

    private int scoreTeam2;

    private MatchType matchType;

    private String map;
}