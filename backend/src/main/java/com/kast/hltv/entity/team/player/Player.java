package com.kast.hltv.entity.team.player;

import com.kast.hltv.entity.abstraction.BaseInfo;
import com.kast.hltv.entity.abstraction.dto.SrcName;

/**
 * @author Kirill "Tamada" Simovin
 */
public class Player extends BaseInfo {
    public Player(SrcName country, SrcName base) {
        super(country, base);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
