package com.kast.hltv.data;

import com.kast.hltv.parser.HLTVDataParser;
import com.kast.interfaces.pause.IPaused;
import io.micronaut.context.event.StartupEvent;
import io.micronaut.scheduling.annotation.Scheduled;
import jakarta.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;

@Singleton
public class HLTVDataCollector implements IPaused {
    private static final Logger LOG = LoggerFactory.getLogger(HLTVDataCollector.class);
    private boolean paused = true;

    private final HLTVDataParser hltvDataParser;

    public HLTVDataCollector(HLTVDataParser hltvDataParser) {
        this.hltvDataParser = hltvDataParser;
    }

    @Scheduled(fixedDelay = "24h", condition = "#{!this.paused}")
    public void parseHLTVData() {
        LOG.info("{} HLTV data parser started!", new SimpleDateFormat("dd/M/yyyy hh:mm:ss").format(new Date()));
        try {
           hltvDataParser.startHLTVParser(0);
        } catch (Exception e) {
            LOG.error("An exception occurred during HLTV parsing! Exception: {}", e.getMessage());
        }
        LOG.info("{} HLTV data parser ended!", new SimpleDateFormat("dd/M/yyyy hh:mm:ss").format(new Date()));
    }

    @Override
    public boolean isPaused() {
        return paused;
    }

    @Override
    public void onStartup(StartupEvent event) {
        this.paused = false;
        LOG.info("HLTV data collector enabled!");
    }
}
