package com.kast.hltv.dto.match;

import com.kast.hltv.common.abstraction.match.Match;
import com.kast.hltv.enums.Months;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Kirill "Tamada" Simovin
 */
public class LifeMatch extends Match {
    public LifeMatch(String matchLink) throws Exception {
        super(matchLink);
    }

    public ArrayList<String> mapPick() {
        ArrayList<String> list = new ArrayList<>();
        Elements column = getMatchDoc().select("body > div.bgPadding > div > div.colCon > div.contentCol > div.match-page > div.g-grid.maps > div.col-6.col-7-small");
        for (Element mapHolder : column.select("div.flexbox-column > div.mapholder")) {
            list.add(mapHolder.child(0).text());
        }
        return list;
    }

    @Override
    public int mapPicker(int mapNumber) {
        Elements mapholder = getMatchDoc().select("body > div.bgPadding > div > div.colCon > div.contentCol > div.match-page > div.g-grid.maps > div.col-6.col-7-small > div.flexbox-column > div:nth-child(" + mapNumber + ")");

        if (mapholder.select("div.results.played > div.results-left").hasClass("pick")) {
            return 1;
        }
        if (mapholder.select("div.results.played > span.results-right").hasClass("pick")) {
            return 2;
        } else return 0;
    }


    @Override
    public String dateOfMatch() {
        String date = getMatchDoc().select("body > div.bgPadding > div > div.colCon > div.contentCol > div.match-page > div.standard-box.teamsBox > div.timeAndEvent > div.date").text();
        String year = date.substring(date.length() - 4);
        String day = "";
        String regex = "^\\d{1,2}";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(date);
        if (matcher.find()) {
            day = matcher.group(0);
        }
        if (Integer.parseInt(day) < 10) {
            day = "0" + day;
        }
        String month = "";
        for (Months monthS : Months.values()) {
            if (date.contains(monthS.getName())) {
                month = monthS.getTitle();
                break;
            }
        }
        return year + "-" + month + "-" + day;
    }
}
