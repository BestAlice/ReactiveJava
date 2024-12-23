package com.kast.hltv.parser;

import com.kast.hltv.common.util.Util;
import com.kast.hltv.entity.abstraction.dto.SrcName;
import com.kast.hltv.entity.match.Match;
import com.kast.hltv.entity.match.map.MatchMap;
import com.kast.hltv.entity.match.map.enums.PickedBy;
import com.kast.hltv.entity.team.Team;
import com.kast.hltv.entity.team.player.Player;
import com.kast.hltv.parser.enums.Months;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Kirill "Tamada" Simovin
 */
public class HLTVDataParser {
    private static final Logger LOG = LoggerFactory.getLogger(HLTVDataParser.class);
    private static final int MAX_ITERATIONS_LIMIT = 150;

    public void startHLTVParser() throws RuntimeException {
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
            LOG.info("Founded {} links!", links.keySet().size());
            for (String link : links.keySet()) {
                loadMatchInfo(link, 0);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void loadMatchInfo(String matchLink, int i) throws Exception {
        LOG.info("Loading info for match: {} is started!", matchLink);
        Document document = Util.getHtml(matchLink);
//        Document document = Jsoup.parse(new File("src/main/java/com/kast/hltv/parser/hltv.html"));
        if (document == null) {
            throw new RuntimeException("An exception occurred during loading match link! Document is null!");
        }
        Elements matchContent = document.select("body > div.bgPadding > div.widthControl > div.colCon > div.contentCol > div.match-page");
        if (i == MAX_ITERATIONS_LIMIT) {
            LOG.info("Iterations limit exceeded!");
            return;
        }
        if (matchContent.isEmpty()) {
            loadMatchInfo(matchLink, i + 1);
        } else {
            Elements matchHeader = matchContent.select("div.standard-box.teamsBox");
            Match.MatchBuilder matchBuilder = Match.builder();

            performMatchHeader(matchBuilder, matchHeader);

            performMatchTimeAndEvent(matchBuilder, matchHeader.select("div.timeAndEvent").getFirst());

            performMatchMaps(matchBuilder, matchContent.select("div.maps > div"));

            performMatchTeamsLineups(matchBuilder, matchContent.select("div.lineups > div > div.lineup"));

            // Save to DB
            LOG.info("Loading info for match: {} is ended!", matchLink);
        }
    }

    private void performMatchTeamsLineups(@Nonnull Match.MatchBuilder matchBuilder, @Nonnull Elements lineups) {
        Match match = matchBuilder.build();

        Team leftTeam = match.getLeftTeam();
        leftTeam.setPlayers(getTeamPlayers(lineups.first()));

        Team rightTeam = match.getRightTeam();
        rightTeam.setPlayers(getTeamPlayers(lineups.last()));

        matchBuilder.leftTeam(leftTeam).rightTeam(rightTeam);
    }

    @Nonnull
    private ArrayList<Player> getTeamPlayers(Element lineup) {
        if (lineup == null) {
            return new ArrayList<>();
        }
        Elements lineupsRow = lineup.select("table > tbody > tr");
        if (lineupsRow.first() == null || lineupsRow.last() == null) {
            return new ArrayList<>();
        }
        Elements images = lineupsRow.first().select("td.player > div.player-compare > img");
        Elements names = lineupsRow.last().select("td.player > div.player-compare");
        ArrayList<Player> players = new ArrayList<>();
        for (int i = 0; i < images.size(); ++i) {
            Element playerNameRow = names.get(i);

            Element countryImage = playerNameRow.select("img").first();
            String flagImageLink = "https://www.hltv.org" + countryImage.attr("src");
            SrcName country = performSrcName(countryImage, flagImageLink);

            String playerImageLink = images.get(i).attr("src").replace("amp;", "");
            String playerName = playerNameRow.select("div").first().text();

            Player player = new Player(country, new SrcName(performSrc(playerImageLink), playerName));
            players.add(player);
        }
        return players;
    }

    private void performMatchHeader(Match.MatchBuilder matchBuilder, Elements matchHeader) {
        if (matchHeader == null) {
            return;
        }
        Elements matchHeaderTeams = matchHeader.select("div.team");
        Team leftTeam = performTeam(matchHeaderTeams.getFirst());
        Team rightTeam = performTeam(matchHeaderTeams.getLast());
        matchBuilder.leftTeam(leftTeam);
        matchBuilder.rightTeam(rightTeam);
    }

    private void performMatchMaps(Match.MatchBuilder matchBuilder, Elements mapsBlock) {
        if (mapsBlock == null || mapsBlock.first() == null) {
            return;
        }
        Element mapsBlockInfo = mapsBlock.first().select("div").first();
        if (mapsBlockInfo == null) {
            return;
        }
        String vetoText = mapsBlockInfo.select("div.veto-box > div").getFirst().text();
        int delimIdx = vetoText.indexOf('*');
        matchBuilder.matchInfo("%s\n\n%s".formatted(vetoText.substring(0, delimIdx - 1), vetoText.substring(delimIdx)));

        Elements matchPicks = mapsBlockInfo.select("div.flexbox-column > div.mapholder");

        ArrayList<MatchMap> maps = new ArrayList<>();
        for (Element pick : matchPicks) {
            Elements pickMap = pick.select("div.played");
            if (pickMap.isEmpty()) {
                pickMap = pick.select("div.optional");
                if (pickMap.isEmpty()) {
                    continue;
                }
            }

            PickedBy pickedBy;
            if (pickMap.size() == 2) {
                if (!pickMap.getLast().select("div.pick").isEmpty()) {
                    pickedBy = PickedBy.LEFT;
                } else if (!pickMap.getLast().select("span.pick").isEmpty()) {
                    pickedBy = PickedBy.RIGHT;
                } else {
                    pickedBy = PickedBy.DECIDER;
                }
            } else {
                pickedBy = PickedBy.TBA;
            }

            Element pickImage = pickMap.getFirst().select("div.map-name-holder > img").first();
            if (pickImage == null) {
                continue;
            }
            String mapImageLink = "https://www.hltv.org" + pickImage.attr("src");
            maps.add(new MatchMap(performSrcName(pickImage, mapImageLink), pickedBy));
        }

        matchBuilder.maps(maps);
    }

    private void performMatchTimeAndEvent(@Nonnull Match.MatchBuilder matchBuilder, @Nonnull Element timeAndEvent) {
        LocalTime localTime = performMatchStartTime(timeAndEvent.select("div.time").first());
        LocalDate localDate = performMatchStartDate(timeAndEvent.select("div.date").first());

        matchBuilder.startTime(LocalDateTime.of(localDate, localTime));
        matchBuilder.eventName(performEventName(timeAndEvent.select("div.event")));
    }

    @Nonnull
    private String performEventName(@Nonnull Elements eventElement) {
        return eventElement.select("a").text();
    }

    private LocalTime performMatchStartTime(Element timeElement) {
        if (timeElement != null) {
            return LocalTime.parse(timeElement.text());
        }
        return LocalTime.of(0, 0);
    }

    private LocalDate performMatchStartDate(Element dateElement) {
        if (dateElement == null) {
            return LocalDate.now();
        }
        String[] splitDate = dateElement.text().split(" ");
        String year = splitDate[splitDate.length - 1];
        String day = "";
        String regex = "^\\d{1,2}";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(splitDate[0]);
        if (matcher.find()) {
            day = matcher.group(0);
        }
        if (Integer.parseInt(day) < 10) {
            day = "0" + day;
        }
        String month = Months.fromTitle(splitDate[splitDate.length - 2]);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse("%s-%s-%s".formatted(year, month, day), formatter);
    }

    @Nonnull
    private Team performTeam(@Nonnull Element teamBlock) {
        SrcName country = performCountryInfo(teamBlock.select("img").first());
        SrcName team = performTeamInfo(teamBlock.select("div > a > img").first());
        return new Team(country, team);
    }

    private SrcName performCountryInfo(Element imageElement) {
        if (imageElement == null) {
            return null;
        }
        String countryImageLink = "https://www.hltv.org" + imageElement.attr("src");
        return performSrcName(imageElement, countryImageLink);
    }

    private SrcName performTeamInfo(Element teamInfo) {
        if (teamInfo == null) {
            return null;
        }
        String logoImageLink = teamInfo.attr("src").replace("amp;", "");
        return performSrcName(teamInfo, logoImageLink);
    }

    @Nonnull
    private SrcName performSrcName(@Nonnull Element element, String link) {
        byte[] src = performSrc(link);
        String countryName = element.attr("title");
        return new SrcName(src, countryName);
    }

    @Nullable
    private byte[] performSrc(String link) {
        byte[] src = null;
        try {
            Connection.Response resultImageResponse = Util.getImage(link);
            if (resultImageResponse != null) {
                src = resultImageResponse.bodyAsBytes();
            }
        } catch (Exception e) {
            LOG.warn("An error occurred while receiving the src: {}", e.getMessage());
        }
        return src;
    }
}
