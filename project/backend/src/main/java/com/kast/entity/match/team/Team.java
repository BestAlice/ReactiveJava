package com.kast.entity.match.team;

import com.kast.entity.match.abstraction.BaseInfo;
import com.kast.entity.match.abstraction.dto.SrcName;
import com.kast.entity.match.team.player.Player;
import io.micronaut.core.annotation.Creator;
import io.micronaut.serde.annotation.Serdeable;
import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonProperty;

import java.util.ArrayList;

@Serdeable
public class Team extends BaseInfo {
    @BsonProperty("players")
    private ArrayList<Player> players;

    @Creator
    @BsonCreator
    public Team(SrcName country, SrcName base) {
        super(country, base);
    }

    public Team() {
    }

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    @Override
    public SrcName getCountry() {
        return super.getCountry();
    }

    @Override
    public SrcName getBase() {
        return super.getBase();
    }

    @Override
    public String toString() {
        return "%s{country=%s, base=%s, players=%s}".formatted(this.getClass().getSimpleName(), this.getCountry(), this.getBase(), this.players);
    }
}
