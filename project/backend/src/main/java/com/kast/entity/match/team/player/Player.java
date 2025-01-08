package com.kast.entity.match.team.player;

import com.kast.entity.match.abstraction.BaseInfo;
import com.kast.entity.match.abstraction.dto.SrcName;
import io.micronaut.core.annotation.Creator;
import io.micronaut.serde.annotation.Serdeable;
import org.bson.codecs.pojo.annotations.BsonCreator;

/**
 * @author Kirill "Tamada" Simovin
 */
@Serdeable
public class Player extends BaseInfo {
    @Creator
    @BsonCreator
    public Player(SrcName country, SrcName base) {
        super(country, base);
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
        return super.toString();
    }
}
