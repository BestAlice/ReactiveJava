package com.kast.entity.match.map;

import com.kast.entity.match.abstraction.dto.SrcName;
import com.kast.entity.match.map.enums.PickedBy;
import com.kast.entity.match.map.log.MatchMapLog;
import io.micronaut.core.annotation.Creator;
import io.micronaut.serde.annotation.Serdeable;
import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonProperty;

import java.util.ArrayList;

/**
 * @author Kirill "Tamada" Simovin
 */
@Serdeable
public class MatchMap {
    @BsonProperty("map")
    private final SrcName map;

    @BsonProperty("pickedBy")
    private final PickedBy pickedBy;

    @BsonProperty("logs")
    private ArrayList<MatchMapLog> logs;

    @Creator
    @BsonCreator
    public MatchMap(SrcName map, PickedBy pickedBy) {
        this.map = map;
        this.pickedBy = pickedBy;
        this.logs = new ArrayList<>();
    }

    public SrcName getMap() {
        return map;
    }

    public PickedBy getPickedBy() {
        return pickedBy;
    }

    public ArrayList<MatchMapLog> getLogs() {
        return logs;
    }

    public void setLogs(ArrayList<MatchMapLog> logs) {
        this.logs = logs;
    }

    @Override
    public String toString() {
        return "%s{map=%s, pickedBy=%s, logs=%s}".formatted(this.getClass().getSimpleName(), this.map, this.pickedBy, this.logs);
    }
}
