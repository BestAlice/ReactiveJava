package com.kast.hltv.entity.abstraction.dto;

import com.kast.hltv.common.util.Util;
import lombok.Data;
import lombok.Getter;

/**
 * @author Kirill "Tamada" Simovin
 */
@Getter
public class SrcName {
    private final byte[] src;

    private final String name;

    public SrcName(byte[] src, String name) {
        this.src = src;
        this.name = name;
    }

    @Override
    public String toString() {
        return "%s{src=%s, name=%s}".formatted(this.getClass().getSimpleName(), Util.convertBytesToBase64(src), name);
    }
}
