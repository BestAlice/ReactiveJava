package com.kast.entity.match;

import com.kast.entity.match.map.MatchMap;
import com.kast.entity.match.team.Team;
import io.micronaut.core.annotation.Creator;
import io.micronaut.serde.annotation.Serdeable;
import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonProperty;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Serdeable
public class Match {
    @BsonProperty("id")
    private Long id;

    @BsonProperty("link")
    private String link;

    @BsonProperty("leftTeam")
    private Team leftTeam;

    @BsonProperty("rightTeam")
    private Team rightTeam;

    @BsonProperty("startTime")
    private LocalDateTime startTime;

    @BsonProperty("eventName")
    private String eventName;

    @BsonProperty("maps")
    private ArrayList<MatchMap> maps;

    @BsonProperty("matchInfo")
    private String matchInfo;

    @BsonProperty("subscribed")
    private boolean subscribed;

    @Creator
    @BsonCreator
    public Match() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Team getLeftTeam() {
        return leftTeam;
    }

    public void setLeftTeam(Team leftTeam) {
        this.leftTeam = leftTeam;
    }

    public Team getRightTeam() {
        return rightTeam;
    }

    public void setRightTeam(Team rightTeam) {
        this.rightTeam = rightTeam;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public ArrayList<MatchMap> getMaps() {
        return maps;
    }

    public void setMaps(ArrayList<MatchMap> maps) {
        this.maps = maps;
    }

    public String getMatchInfo() {
        return matchInfo;
    }

    public void setMatchInfo(String matchInfo) {
        this.matchInfo = matchInfo;
    }

    public boolean isSubscribed() {
        return subscribed;
    }

    public void setSubscribed(boolean subscribed) {
        this.subscribed = subscribed;
    }

    @Override
    public String toString() {
        return "%s{id=%d, link=%s, leftTeam=%s, rightTeam=%s, startTime=%s, eventName=%s, maps=%s, matchInfo=%s, subscribed=%b}"
                .formatted(this.getClass().getSimpleName(), id, link, leftTeam, rightTeam, startTime, eventName, maps, matchInfo, subscribed);
    }
}
