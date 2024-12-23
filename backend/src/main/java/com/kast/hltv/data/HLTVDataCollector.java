package com.kast.hltv.data;

import com.kast.hltv.parser.HLTVDataParser;
import io.micronaut.context.event.StartupEvent;
import io.micronaut.runtime.event.annotation.EventListener;
import io.micronaut.scheduling.annotation.Scheduled;
import jakarta.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Kirill "Tamada" Simovin
 */
@Singleton
public class HLTVDataCollector {
    private static final Logger LOG = LoggerFactory.getLogger(HLTVDataCollector.class);
    private boolean paused = true;

    @Scheduled(fixedDelay = "24h", condition = "#{!this.paused}")
    void parseHLTVData() {
        LOG.info("{} HLTV data parser started!", new SimpleDateFormat("dd/M/yyyy hh:mm:ss").format(new Date()));
        try {
//            new HLTVDataParser().startHLTVParser();
            new HLTVDataParser().loadMatchInfo("https://www.hltv.org/matches/2378071/wopa-vs-heimo-european-pro-league-2nd-division-season-18", 0);
        } catch (Exception e) {
            e.printStackTrace();
            LOG.error("An exception occurred during HLTV parsing! Exception: {}", e.getMessage());
        }
        LOG.info("{} HLTV data parser ended!", new SimpleDateFormat("dd/M/yyyy hh:mm:ss").format(new Date()));
    }

    public boolean isPaused() {
        return paused;
    }

    // https://stackoverflow.com/a/77787292/18750563
    @EventListener
    public void onStartup(StartupEvent event) {
        this.paused = false;
        LOG.info("HLTV data collector enabled!");
    }
}
