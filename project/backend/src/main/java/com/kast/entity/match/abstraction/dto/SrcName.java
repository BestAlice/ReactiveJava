package com.kast.entity.match.abstraction.dto;

import io.micronaut.core.annotation.Creator;
import io.micronaut.serde.annotation.Serdeable;
import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonProperty;

/**
 * @author Kirill "Tamada" Simovin
 */
@Serdeable
public class SrcName {
    @BsonProperty("src")
    private final String src;

    @BsonProperty("name")
    private final String name;

    @Creator
    @BsonCreator
    public SrcName(String src, String name) {
        this.src = src;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getSrc() {
        return src;
    }

    @Override
    public String toString() {
        return "%s{src=%s, name=%s}".formatted(this.getClass().getSimpleName(), src, name);
    }
}
