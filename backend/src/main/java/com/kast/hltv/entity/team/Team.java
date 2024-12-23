package com.kast.hltv.entity.team;

import com.kast.hltv.entity.abstraction.BaseInfo;
import com.kast.hltv.entity.abstraction.dto.SrcName;
import com.kast.hltv.entity.team.player.Player;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

/**
 * @author Kirill "Tamada" Simovin
 */
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Team extends BaseInfo {

    private ArrayList<Player> players;

    public Team(SrcName country, SrcName base) {
        super(country, base);
    }

    public Team() {

    }

    @Override
    public String toString() {
        return "%s{country=%s, base=%s, players=%s}".formatted(this.getClass().getSimpleName(), this.getCountry(), this.getBase(), this.players);
    }
}
