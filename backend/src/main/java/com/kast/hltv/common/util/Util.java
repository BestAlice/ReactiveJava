package com.kast.hltv.common.util;

import generator.RandomUserAgentGenerator;
import org.jetbrains.annotations.Nullable;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Kirill "Tamada" Simovin
 */
public class Util {
    private static final Logger LOG = LoggerFactory.getLogger(Util.class);

    @Nullable
    synchronized public static Document getHtml(String link) throws Exception {
        try {
            LOG.info("New request! Link: {}", link);
            Thread.sleep(1300);
            return Jsoup.connect(link)
                    .referrer("https://www.hltv.org/")
                    .userAgent(RandomUserAgentGenerator.getNext())
                    .ignoreHttpErrors(true)
                    .get();
        } catch (Exception e) {
            LOG.warn("An exception occurred during parse link: {}. Error: {}", link, e.getMessage());
            LOG.info("Retry to connect.");
            Thread.sleep(50000);
            getHtml(link);
        }
        return null;
    }
}
