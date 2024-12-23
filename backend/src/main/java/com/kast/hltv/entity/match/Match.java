package com.kast.hltv.entity.match;

import com.kast.hltv.entity.match.map.MatchMap;
import com.kast.hltv.entity.team.Team;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * @author Kirill "Tamada" Simovin
 */
@AllArgsConstructor
@Data
@Builder
public class Match {
    private Team leftTeam;

    private Team rightTeam;

    private LocalDateTime startTime;

    private String eventName;

    private ArrayList<MatchMap> maps;

    private String matchInfo;
}
