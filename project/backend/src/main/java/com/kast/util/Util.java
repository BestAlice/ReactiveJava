package com.kast.util;

import generator.RandomUserAgentGenerator;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Base64;
import java.util.concurrent.TimeUnit;

public class Util {
    private static final Logger LOG = LoggerFactory.getLogger(Util.class);

    private static final int RETRY_REQUEST_TIMEOUT = 15000;

    synchronized public static Observable<Document> getHtml(String link) {
        try {
            LOG.info("New data grab request! Link: {}", link);
            return Observable.just(link)
                    .subscribeOn(Schedulers.io())
                    .timeout(RETRY_REQUEST_TIMEOUT, TimeUnit.MILLISECONDS)
                    .map(lnk -> Jsoup.connect(link)
                            .referrer("https://www.hltv.org/")
                            .userAgent(RandomUserAgentGenerator.getNext())
                            .ignoreHttpErrors(true)
                            .get());
        } catch (Exception e) {
            LOG.warn("An exception occurred during parse link: {}. Error: {}", link, e.getMessage());
            LOG.info("Retry to connect to grab data.");
//            Observable.timer(RETRY_REQUEST_TIMEOUT, TimeUnit.MILLISECONDS).subscribe();
            return getHtml(link);
        }
    }

    synchronized public static Observable<Connection.Response> getImage(String link) {
        try {
            LOG.info("New image grab request! Link: {}", link);
            return Observable.just(link)
                    .subscribeOn(Schedulers.io())
                    .timeout(RETRY_REQUEST_TIMEOUT, TimeUnit.MILLISECONDS)
                    .map(lnk -> Jsoup.connect(link)
                            .userAgent(RandomUserAgentGenerator.getNext())
                            .ignoreContentType(true)
                            .execute());
        } catch (Exception e) {
            LOG.warn("An exception occurred during parse image link: {}. Error: {}", link, e.getMessage());
            LOG.info("Retry to connect to grab image.");
//            Observable.timer(RETRY_REQUEST_TIMEOUT, TimeUnit.MILLISECONDS).blockingFirst();
            return getImage(link);
        }
//        return null;
    }

    public static String convertBytesToBase64(byte[] data) {
        if (data == null) {
            return null;
        }
        return Base64.getEncoder().encodeToString(data);
    }
}
