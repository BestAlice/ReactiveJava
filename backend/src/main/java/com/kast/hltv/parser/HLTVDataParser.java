package com.kast.hltv.parser;

import com.kast.hltv.common.abstraction.match.Match;
import com.kast.hltv.common.abstraction.team.Team;
import com.kast.hltv.common.generator.AdvantageGenerator;
import com.kast.hltv.common.image.ImageEditor;
import com.kast.hltv.common.thread.ThreadMatch;
import com.kast.hltv.common.util.Util;
import com.kast.hltv.dto.match.LifeMatch;
import com.kast.hltv.dto.team.LifeTeam;
import org.apache.commons.math3.util.Precision;
import org.jetbrains.annotations.NotNull;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Kirill "Tamada" Simovin
 */
public class HLTVDataParser {
    private static final Logger LOG = LoggerFactory.getLogger(HLTVDataParser.class);

    private double leftPerc;
    private double rightPerc;

    public void startHLTVParser() throws RuntimeException{
        try {
            Document document = Util.getHtml("https://www.hltv.org");
            if (document == null) {
                throw new RuntimeException("An exception occurred during HLTV parsing! Document is null!");
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
            throw new RuntimeException(e);
        }
    }

    public void loadAllLife(String lifeMatchLink) throws Exception {
        Match match = new LifeMatch(lifeMatchLink);
        if (match.mapPick().getFirst().equals("TBA")) {
            ThreadMatch threadMatch = new ThreadMatch(match);
            threadMatch.start();
        } else {
            Team leftTeam;
            Team rightTeam;

            double[] attributes = new double[17];
            Map<String, String[]> map_perWin = new LinkedHashMap<>();
            int mapNumber = 1;
            for (String map : match.mapPick()) {
                if (map.equals("Default")) {
                    mapNumber++;
                    continue;
                }
                LOG.info("First team download...");
                leftTeam = new LifeTeam(map, match, "left");
                LOG.info("Second team download...");
                rightTeam = new LifeTeam(map, match, "right");
                AdvantageGenerator generator = new AdvantageGenerator(leftTeam, rightTeam);

                LOG.info("Generating attitude...");
                attributes[0] = Precision.round(generator.KDRatioAttitude(), 3);
                attributes[1] = Precision.round(generator.headshotAttitude(), 3);
                attributes[2] = Precision.round(generator.damagePerRoundAttitude(), 3);
                attributes[3] = Precision.round(generator.assistsPerRoundAttitude(), 3);
                attributes[4] = Precision.round(generator.impactAttitude(), 3);
                attributes[5] = Precision.round(generator.kastAttitude(), 3);
                attributes[6] = Precision.round(generator.openingKillRatioAttitude(), 3);
                attributes[7] = Precision.round(generator.rating3mAttitude(), 3);
                attributes[8] = Precision.round(generator.ratingVStop5Attitude(), 3);
                attributes[9] = Precision.round(generator.ratingVStop10Attitude(), 3);
                attributes[10] = Precision.round(generator.ratingVStop20Attitude(), 3);
                attributes[11] = Precision.round(generator.ratingVStop30Attitude(), 3);
                attributes[12] = Precision.round(generator.ratingVStop50Attitude(), 3);
                attributes[13] = Precision.round(generator.totalKillsAttitude(), 3);
                attributes[14] = Precision.round(generator.mapsPlayedAttitude(), 3);
                attributes[15] = generator.rankingDifference();
                attributes[16] = match.mapPicker(mapNumber);
                mapNumber++;

                LOG.info("Python calling...");
                callPython(attributes);
                LOG.info("Next Map");
                map_perWin.put(map, new String[]{String.valueOf(getLeftPerc()), String.valueOf(getRightPerc())});
            }
            ImageEditor.fillImage(match, map_perWin);
        }
    }

    private synchronized void callPython(double @NotNull [] attributes) {
        String caller = "sh src/main/java/com/kast/hltv/parser/python/bashscript.sh "
                + attributes[0] + " "
                + attributes[1] + " "
                + attributes[2] + " "
                + attributes[3] + " "
                + attributes[4] + " "
                + attributes[5] + " "
                + attributes[6] + " "
                + attributes[7] + " "
                + attributes[8] + " "
                + attributes[9] + " "
                + attributes[10] + " "
                + attributes[11] + " "
                + attributes[12] + " "
                + attributes[13] + " "
                + attributes[14] + " "
                + attributes[15] + " "
                + attributes[16];

        if (attributes[0] == 0) {
            setLeftPerc("0.0", 1);
            setRightPerc("0.0", 1);
        } else {
            ProcessBuilder processBuilder = new ProcessBuilder();
            processBuilder.command("bash", "-c", caller);

            try {
                Process process = processBuilder.start();
                StringBuilder output = new StringBuilder();
                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                String line;
                while ((line = reader.readLine()) != null) {
                    output.append(line).append("\n");
                }
                int exitVal = process.waitFor();
                if (exitVal == 0) {
                    System.out.println(output);
                    setLeftPerc(output.toString(), 0);
                    setRightPerc(output.toString(), 0);
                }
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void setLeftPerc(String pyOutput, int a) {
        switch (a) {
            case 0:
                leftPerc = Double.parseDouble(pyOutput.substring(0, pyOutput.indexOf(':')));
                break;
            case 1:
                leftPerc = Double.parseDouble(pyOutput);
                break;
        }
    }

    private void setRightPerc(String pyOutput, int a) {
        switch (a) {
            case 0:
                rightPerc = Double.parseDouble(pyOutput.substring(pyOutput.indexOf(':') + 1));
                break;
            case 1:
                rightPerc = Double.parseDouble(pyOutput);
                break;
        }
    }

    private double getLeftPerc() {
        return leftPerc;
    }

    private double getRightPerc() {
        return rightPerc;
    }
}
