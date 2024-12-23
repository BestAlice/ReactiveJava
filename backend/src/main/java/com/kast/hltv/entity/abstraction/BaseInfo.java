package com.kast.hltv.entity.abstraction;

import com.kast.hltv.entity.abstraction.dto.SrcName;
import lombok.Getter;

/**
 * @author Kirill "Tamada" Simovin
 */
@Getter
public abstract class BaseInfo {
    private SrcName country;

    private SrcName base;

    public BaseInfo(SrcName country, SrcName base) {
        this.country = country;
        this.base = base;
    }

    public BaseInfo() {
    }

    @Override
    public String toString() {
        return "%s{country=%s, base=%s}".formatted(this.getClass().getSimpleName(), this.country, this.base);
    }
}
