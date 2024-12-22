package com.kast.hltv.common.thread;

import com.kast.hltv.common.abstraction.match.Match;
import com.kast.hltv.parser.HLTVDataParser;

/**
 * @author Kirill "Tamada" Simovin
 */
public class ThreadMatch extends Thread {
    private Match match;
//    private HLTVDataCollector hltvDataCollector;

    public ThreadMatch(Match match) {
        this.match = match;
    }

    @Override
    public void run() {
        try {
            while (true) {
                match = match.updatedDoc();
                if (match.mapPick().getFirst().equals("TBA")) {
                    Thread.sleep(10000);
                } else {
                    break;
                }
            }
           new HLTVDataParser().loadAllLife(match.getMatchLink());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
