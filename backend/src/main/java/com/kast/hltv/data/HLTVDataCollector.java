package com.kast.hltv.data;

import com.kast.hltv.common.util.Util;
import io.micronaut.context.event.StartupEvent;
import io.micronaut.runtime.event.annotation.EventListener;
import io.micronaut.scheduling.annotation.Scheduled;
import jakarta.inject.Singleton;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

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
            Document document = Util.getHtml("https://www.hltv.org");
            if (document == null) {
                LOG.error("An exception occurred during HLTV parsing! Document is null!");
                return;
            }
            Elements matches = document.select("body > div.bgPadding > div > div.colCon > div.rightCol > aside:nth-child(1) > div.top-border-hide");
            ArrayList<Element> listEle = new ArrayList<>(matches.select("a"));
            Map<String, Boolean> links = new LinkedHashMap<>();
            for (Element element : listEle) {
                String key = "https://www.hltv.org" + element.attr("href");
                boolean val = element.getElementsByAttribute("filteraslive").equals(element.getElementsByAttributeValueContaining("filteraslive", "true"));
                links.put(key, val);
            }
            for (String link : links.keySet()){
                System.out.println(link);
            }
        } catch (Exception e) {
            LOG.error("An exception occurred during HLTV parsing! Exception: {}", e.getMessage());
        }
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
