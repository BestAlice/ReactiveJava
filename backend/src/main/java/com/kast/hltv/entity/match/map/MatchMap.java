package com.kast.hltv.entity.match.map;

import com.kast.hltv.entity.abstraction.dto.SrcName;
import com.kast.hltv.entity.match.map.enums.PickedBy;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Kirill "Tamada" Simovin
 */
@AllArgsConstructor
@Getter
public class MatchMap {
    private final SrcName map;

    private final PickedBy pickedBy;

    @Override
    public String toString() {
        return "%s{map=%s, pickedBy=%s}".formatted(this.getClass().getSimpleName(), this.map, this.pickedBy);
    }
}
