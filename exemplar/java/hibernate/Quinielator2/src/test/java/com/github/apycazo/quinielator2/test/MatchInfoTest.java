package com.github.apycazo.quinielator2.test;

import com.github.apycazo.quinielator2.domain.dao.TeamStats;
import com.github.apycazo.quinielator2.domain.model.MatchInfo;
import com.github.apycazo.quinielator2.domain.service.MatchInfoService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.After;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import static org.junit.Assert.*;
import org.junit.Before;

public class MatchInfoTest {

    private List<MatchInfo> sampleMatches;
    private ApplicationContext appContext;
    private MatchInfoService service;
    private ArrayList<Long> newIds;
    private static String[] teams = {"A-TEAM", "B-TEAM", "C-TEAM", "D-TEAM"};
    private static int sampleMatchesSeason = 1800;
    private static int sampleMatchesDay = 1;

    public MatchInfoTest() {
    }

    @Before
    public void setup() {
        // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
        // Create samples
        // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
        sampleMatches = createSampleMatches();
        assertTrue("No sample matches generated", !sampleMatches.isEmpty());
        // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
        // Load context and print loaded beans
        // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
        appContext = new ClassPathXmlApplicationContext("classpath:/spring.cfg.xml");
        assertTrue("No context loaded", appContext != null);
        String[] beanDefinitionNames = appContext.getBeanDefinitionNames();
        System.out.println("Loaded bean definition names: ");
        for (String name : beanDefinitionNames) {
            if (!name.startsWith("org.springframework")) {
                System.out.println("\t" + name);
            }
        }
        // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
        // Get MatchInfo service dao
        // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
        service = (MatchInfoService) appContext.getBean("matchInfoService");
        assertTrue("Service not loaded", service != null);
        System.out.println("Service bean retrieved");
        // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
        // Create sample matches
        // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
        newIds = new ArrayList();
        for (MatchInfo match : sampleMatches) {
            Long id = service.save(match);
            newIds.add(id);
            System.out.println("Saved match: " + match.toString());
        }
        assertEquals(sampleMatches.size(), newIds.size());
    }

    /*
     mysql> select * from MATCH_DATA;
     +----+-----------+----------+------------+-----------+----------+--------+--------------+-------------+
     | id | match_day | division | localGoals | localTeam | match_id | season | visitorGoals | visitorTeam |
     +----+-----------+----------+------------+-----------+----------+--------+--------------+-------------+
     |  1 |         1 |        1 |          3 | TEST-1    |        0 |      0 |            2 | TEST-2      |
     +----+-----------+----------+------------+-----------+----------+--------+--------------+-------------+
     */
    public List<MatchInfo> createSampleMatches() {
        List<MatchInfo> list = new ArrayList<>();
//        String [] teams = {"A-TEAM","B-TEAM","C-TEAM","D-TEAM"};
        int[] goals = {1, 3, 2, 4};
        for (int i = 0; i < teams.length; i++) {
            MatchInfo match = new MatchInfo();
            match.setDivision(1);
            match.setDay(sampleMatchesDay);
            match.setSeason(sampleMatchesSeason);
            match.setMatch(i);
            int rivalTeamIndex = i == (teams.length - 1) ? 0 : i + 1;
            match.setLocalTeam(teams[i]);
            match.setVisitorTeam(teams[rivalTeamIndex]);
            match.setLocalGoals(goals[i]);
            match.setVisitorGoals(goals[rivalTeamIndex]);
            list.add(match);
        }
        return list;
    }

    @Test
    public void getMatchList() {
        // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
        // Retrieve matches
        // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
        List<MatchInfo> retrievedMatches = service.getMatchInfoList();
        int retrievedMatchesCount = retrievedMatches.size();
        assertTrue("There should be at least one match retrieved, but there is none.", retrievedMatchesCount > 0);
    }

    @Test
    public void getMatchByID() {
        // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
        // Retrieve a match by id
        // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
        Long requestedId = newIds.get(0);
        MatchInfo match = service.getById(requestedId);
        assertTrue("Retrieved match is null.", match != null);
        System.out.println("Retrieved by id:" + requestedId + " :: " + match.toString());
        assertTrue(requestedId == match.getId());
    }

    @Test
    public void getMatchBySeasonAndID() {
        // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
        // Retrieve a match by season and day
        // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
        List<MatchInfo> dayMatches = service.getByDate(sampleMatchesSeason, sampleMatchesDay);
        System.out.println("Retrieved matches by date:");
        for (MatchInfo m : dayMatches) {
            System.out.println("Day match : " + m.toString());
        }
        assertEquals(sampleMatches.size(), dayMatches.size());
    }

    @Test
    public void getSeasonParticipatingTeamStats() {
        // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
        // Retrieve participating teams
        // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
        String[] teamsFound = service.getSeasonTeams(sampleMatchesSeason);
        assertEquals(teams.length, teamsFound.length);
        for (String t : teams) {
            int foundIndex = Arrays.binarySearch(teamsFound, t);
            assertTrue(foundIndex >= 0);
            System.out.println("Found team name: " + teamsFound[foundIndex]);
        }
        // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
        // Stats for each team
        // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
        for (String team : teamsFound) {
            TeamStats stats = service.getTeamStats(team, sampleMatchesSeason, sampleMatchesDay);
            System.out.println(stats.toString());
        }
        // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
        // Stats for first team rivals
        // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
        for (int i = 1; i < teamsFound.length; i++) {
            TeamStats stats = service.getTeamStatsAgainstRival(teamsFound[0], teamsFound[i], sampleMatchesSeason, sampleMatchesDay);
            System.out.println(stats.toString());
        }
    }    
    
    @After
    public void removeTestMatches() {
        for (long id : newIds) {
            try {
                MatchInfo match = service.getById(id);
                if (match != null) {
                    System.out.println("Removing match: " + match.toString());
                    service.delete(match);
                }
            } catch (Exception e) {
                System.err.println("Can't remove ID: " + id + ". Reason: " + e.getMessage());
            }
        }
    }


//    public void createMatchWithHibernate() {
//
//        // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
//        // Retrieve matches
//        // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
//        List<MatchInfo> retrievedMatches = service.getMatchInfoList();
////        for (MatchInfo m : retrievedMatches) {
////            System.out.println("Read match: " + m.toString());
////        }
//
//        int retrievedMatchesCount = retrievedMatches.size();
//        System.out.println("Retrieved matches : " + retrievedMatchesCount);
//
//        System.out.println("Listed matches");
//        assertTrue("There should be at least one match retrieved, but there is none.", retrievedMatchesCount > 0);
//
//        // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
//        // Retrieve a match by id
//        // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
//        Long requestedId = newIds.get(0);
//        MatchInfo match = service.getById(requestedId);
//        assertTrue("Retrieved match is null.", match != null);
//        System.out.println("Retrieved by id:" + requestedId + " :: " + match.toString());
//        assertTrue(requestedId == match.getId());
//
//        // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
//        // Retrieve a match by season and day
//        // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
//        List<MatchInfo> dayMatches = service.getByDate(sampleMatchesSeason, sampleMatchesDay);
//        System.out.println("Retrieved matches by date:");
//        for (MatchInfo m : dayMatches) {
//            System.out.println("Day match : " + m.toString());
//        }
//        assertEquals(sampleMatches.size(), dayMatches.size());
//
//        // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
//        // Retrieve participating teams
//        // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
//        String[] teamsFound = service.getSeasonTeams(sampleMatchesSeason);
//        assertEquals(teams.length, teamsFound.length);
//        for (String t : teams) {
//            int foundIndex = Arrays.binarySearch(teamsFound, t);
//            assertTrue(foundIndex >= 0);
//            System.out.println("Found team name: " + teamsFound[foundIndex]);
//        }
//
//        // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
//        // Stats for each team
//        // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
//        for (String team : teamsFound) {
//            TeamStats stats = service.getTeamStats(team, sampleMatchesSeason, sampleMatchesDay);
//            System.out.println(stats.toString());
//        }
//
//        // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
//        // Stats for first team rivals
//        // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
//        for (int i = 1; i < teamsFound.length; i++) {
//            TeamStats stats = service.getTeamStatsAgainstRival(teamsFound[0], teamsFound[i], sampleMatchesSeason, sampleMatchesDay);
//            System.out.println(stats.toString());
//        }
//        // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
//        // Delete match
//        // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
//        System.out.println("Deleting match: " + match.toString());
//        service.delete(match);
//        int remainingCount = service.getMatchInfoList().size();
//        assertEquals(retrievedMatchesCount - 1, remainingCount);
//
//        // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
//        // Delete the rest of the test generated values
//        // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
//        for (int i = 1; i < newIds.size(); i++) {
//            MatchInfo matchToDelete = service.getById(newIds.get(i));
//            System.out.println("Deleting match: " + matchToDelete.toString());
//            service.delete(matchToDelete);
//        }
//
//        System.out.println("Done");
//    }

    
}