package com.kast.hltv.common.util;

import generator.RandomUserAgentGenerator;
import org.jetbrains.annotations.Nullable;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Base64;

/**
 * @author Kirill "Tamada" Simovin
 */
public class Util {
    private static final Logger LOG = LoggerFactory.getLogger(Util.class);

    private static final int NEW_REQUEST_TIMEOUT = 10000;
    private static final int RETRY_REQUEST_TIMEOUT = 50000;

    @Nullable
    synchronized public static Document getHtml(String link) throws Exception {
        try {
            LOG.info("New data grab request! Link: {}", link);
            Thread.sleep(NEW_REQUEST_TIMEOUT);
            return Jsoup.connect(link)
                    .referrer("https://www.hltv.org/")
                    .userAgent(RandomUserAgentGenerator.getNext())
                    .ignoreHttpErrors(true)
                    .get();
        } catch (Exception e) {
            LOG.warn("An exception occurred during parse link: {}. Error: {}", link, e.getMessage());
            LOG.info("Retry to connect to grab data.");
            Thread.sleep(RETRY_REQUEST_TIMEOUT);
            getHtml(link);
        }
        return null;
    }

    @Nullable
    synchronized public static Connection.Response getImage(String link) throws Exception {
        try {
            LOG.info("New image grab request! Link: {}", link);
            Thread.sleep(NEW_REQUEST_TIMEOUT);
            return Jsoup.connect(link)
                    .userAgent(RandomUserAgentGenerator.getNext())
                    .ignoreContentType(true)
                    .execute();
        } catch (Exception e) {
            LOG.warn("An exception occurred during parse image link: {}. Error: {}", link, e.getMessage());
            LOG.info("Retry to connect to grab image.");
            Thread.sleep(RETRY_REQUEST_TIMEOUT);
            getImage(link);
        }
        return null;
    }

    public static String convertBytesToBase64(byte[] data) {
        if (data == null) {
            return null;
        }
        return Base64.getEncoder().encodeToString(data);
    }
}
