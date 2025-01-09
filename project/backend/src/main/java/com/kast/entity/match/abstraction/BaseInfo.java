package com.kast.entity.match.abstraction;

import com.kast.entity.match.abstraction.dto.SrcName;
import io.micronaut.core.annotation.Creator;
import io.micronaut.serde.annotation.Serdeable;
import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonProperty;

@Serdeable
public abstract class BaseInfo {
    @BsonProperty("country")
    private SrcName country;

    @BsonProperty("base")
    private SrcName base;

    @Creator
    @BsonCreator
    public BaseInfo(SrcName country, SrcName base) {
        this.country = country;
        this.base = base;
    }

    public BaseInfo() {
    }

    public SrcName getCountry() {
        return country;
    }

    public SrcName getBase() {
        return base;
    }

    @Override
    public String toString() {
        return "%s{country=%s, base=%s}".formatted(this.getClass().getSimpleName(), this.country, this.base);
    }
}
