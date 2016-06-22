package com.github.apycazo.quinielator2.network;

import com.github.apycazo.quinielator2.domain.model.MatchInfo;
import com.github.apycazo.quinielator2.domain.service.MatchInfoService;
import com.github.apycazo.quinielator2.network.HttpWrapper.HttpResponse;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Scanner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("downloader")
public class MatchDataDownloader {

    @Autowired
    private HttpWrapper http;
    @Autowired
    private MatchInfoService dao;
    private boolean verbose;

    public MatchDataDownloader() {
        verbose = false;
    }

    public HttpWrapper getHttpWrapper() {
        return this.http;
    }

    public void setHttpWrapper(HttpWrapper http) {
        this.http = http;
    }

    public void setVerbose(boolean verbose) {
        this.verbose = verbose;
    }

    // http://www.webprincipal.com/futbol/liga2010/division1/div1jor1.html
    private String generateURL(int division, int season, int day) {
        String url = "http://www.webprincipal.com/futbol/liga";
        url = String.format("%s%d/division%d/div%djor%d.html", url, season, division, division, day);
        return url;
    }

    public boolean updateDatabaseLast() {
        Calendar cal = new GregorianCalendar();
        int currentYear = cal.get(Calendar.YEAR);
        int lastSeasonSaved = dao.getLastSeason();
        int lastDaySaved = dao.getLastDay();

        int initialDay = currentYear > lastSeasonSaved ? 1 : lastDaySaved + 1;

        if (currentYear > lastSeasonSaved) {
            boolean finishedYear = updateDatabase(new int[]{lastSeasonSaved}, initialDay);
            if (finishedYear) {
                while (lastSeasonSaved <= currentYear && finishedYear) {
                    lastSeasonSaved++;
                    finishedYear = finishedYear && updateDatabase(new int[]{lastSeasonSaved}, 1);
                }
            }
            return finishedYear;
        } else {
            return updateDatabase(new int[]{currentYear}, initialDay);
        }
    }

    public boolean updateDatabase() {
        int[] seasons = {2011, 2012};
        return updateDatabase(seasons, 1);
    }

    public boolean updateDatabase(int[] seasons, int initialDay) {
        int[] divisions = {1,2};
        try {
            for (int division : divisions) {
                int days = division == 1 ? 38 : 42;
                int matchesPerDay = division == 1 ? 10 : 11;
                for (int season : seasons) {
                    for (int day = 1; day <= days; day++) {
                        String _url = generateURL(division, season, day);
                        System.out.println("URL :: " + _url);
                        URL url = new URL(_url);
                        if (http.get(url) == HttpResponse.Success) {
                            Scanner sc = new Scanner(http.getResponse());
                            sc.useDelimiter("<");
                            boolean odd = true; // tags change every odd match
                            // 10 matches for day
                            for (int i = 0; i < matchesPerDay; i++) {
                                try {
                                    MatchInfo matchInfo = new MatchInfo();
                                    matchInfo.setDivision(division);
                                    matchInfo.setSeason(season);
                                    matchInfo.setDay(day);
                                    matchInfo.setMatch(i + 1); // match
                                    // Local data
                                    String tag = odd ? "<td class=\"eq1\"><B>" : "<td class=\"eq2\"><B>";
                                    sc.findWithinHorizon(tag, 0);
                                    matchInfo.setLocalTeam(sc.next());
                                    tag = odd ? "<td class=\"gol1\">" : "<td class=\"gol2\">";
                                    sc.findWithinHorizon(tag, 0);
                                    matchInfo.setLocalGoals(Integer.parseInt(sc.next()));
                                    // Visitor data
                                    tag = odd ? "<td class=\"eq1\"><B>" : "<td class=\"eq2\"><B>";
                                    sc.findWithinHorizon(tag, 0);
                                    matchInfo.setVisitorTeam(sc.next());
                                    tag = odd ? "<td class=\"gol1\">" : "<td class=\"gol2\">";
                                    sc.findWithinHorizon(tag, 0);
                                    matchInfo.setVisitorGoals(Integer.parseInt(sc.next()));
                                    odd = !odd;
                                    dao.saveOrUpdate(matchInfo);
                                    if (this.verbose) {
                                        System.out.println(matchInfo.toString());
                                    }
                                }
                                catch (Exception matchError) {
                                    System.err.println("Match error: No data?. Ex :: " + matchError.getMessage());
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            return false;
        }

        return true;

    }
}
