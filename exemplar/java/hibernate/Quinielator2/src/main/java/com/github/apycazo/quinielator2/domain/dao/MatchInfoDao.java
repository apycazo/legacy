package com.github.apycazo.quinielator2.domain.dao;

import com.github.apycazo.quinielator2.domain.model.MatchInfo;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class MatchInfoDao implements IMatchInfoDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void saveOrUpdate (MatchInfo matchInfo) {
        sessionFactory.getCurrentSession().saveOrUpdate(matchInfo);
        //Esto es para que no guarde el insert para el final, sino no funcionan los tests (no espera al commit)
        sessionFactory.getCurrentSession().flush();
    }

    @Override
    public List<MatchInfo> getMatchInfoList() {
        Query query = sessionFactory.getCurrentSession().createQuery("from MatchInfo");
        List<MatchInfo> matches = query.list();
        return matches;
    }

    @Override
    public long save(MatchInfo matchInfo) {
        long id = (long)sessionFactory.getCurrentSession().save(matchInfo);
        sessionFactory.getCurrentSession().flush();
        return id;
    }

    @Override
    public void update(MatchInfo matchInfo) {
        sessionFactory.getCurrentSession().update(matchInfo);
        sessionFactory.getCurrentSession().flush();
    }

    @Override
    public void delete(MatchInfo matchInfo) {
        sessionFactory.getCurrentSession().delete(matchInfo);
        sessionFactory.getCurrentSession().flush();
    }

    @Override
    public MatchInfo getById(long matchInfoId) {
        Query query = sessionFactory.getCurrentSession().createQuery("from MatchInfo where id=" + matchInfoId);
        List<MatchInfo> matches = query.list();
        return matches.size() > 0 ? matches.get(0) : null;
    }
    
    @Override
    public List<MatchInfo> getByDate (int season, int day) {
        Query query = sessionFactory.getCurrentSession().createQuery("from MatchInfo where season=" + season + " and day=" + day);
        List<MatchInfo> matches = query.list();
        return matches;
    }
    
    @Override
    public int getLastSeason() {
        Query query = sessionFactory.getCurrentSession().createQuery("select max(season) from MatchInfo matchInfo");
        List<Integer> matches = query.list();
        for (Integer i : matches) System.out.println("Result : " + i);
        if (matches.get(0) == null) return 0;
        else return matches.get(0);
    }
    
    @Override
    public int getLastDay() {
        Query query = sessionFactory.getCurrentSession().createQuery("select max(day) from MatchInfo matchInfo");
        List<Integer> matches = query.list();
        for (Integer i : matches) System.out.println("Result : " + i);
        if (matches.get(0) == null) return 0;
        else return matches.get(0);
    }
    
    @Override
    public String [] getSeasonTeams (int season) {
        String queryString = "select distinct localTeam from MatchInfo matchInfo where season="+season;
        Query query = sessionFactory.getCurrentSession().createQuery(queryString);
        List<String> teams = query.list();
        return teams.toArray(new String[teams.size()]);
    }
    
    @Override
    public TeamStats getTeamStats (String team, String rival, String[] conditions) {
        
        TeamStats stats = new TeamStats();        
        
        String queryString = "select count (*) from MatchInfo matchInfo where";
        String conditionString = "";
        for (String condition : conditions) conditionString += " and " + condition;
        String local = "matchInfo.localTeam='"+team+"'";
        String visitor = "matchInfo.visitorTeam='"+team+"'";
        String rivalTeamLocal = rival.isEmpty() ? "" : String.format(" and matchInfo.localTeam='%s'", rival);
        String rivalTeamVisitor = rival.isEmpty() ? "" : String.format(" and matchInfo.visitorTeam='%s'", rival);
     
        String Q;
        // Local wins
        Q = String.format("%s %s and matchInfo.localGoals > matchInfo.visitorGoals %s%s", queryString, local, conditionString, rivalTeamVisitor).trim();
        stats.setWinsAsLocal(getLongResult(Q).intValue());
        // Visitor wins
        Q = String.format("%s %s and matchInfo.localGoals < matchInfo.visitorGoals %s%s", queryString, visitor, conditionString, rivalTeamLocal).trim();
        stats.setWinsAsVisitor(getLongResult(Q).intValue());
        // Local draws
        Q = String.format("%s %s and matchInfo.localGoals = matchInfo.visitorGoals %s%s", queryString, local, conditionString, rivalTeamVisitor).trim();
        stats.setDrawsAsLocal(getLongResult(Q).intValue());
        // Visitor draws
        Q = String.format("%s %s and matchInfo.localGoals = matchInfo.visitorGoals %s%s", queryString, visitor, conditionString, rivalTeamLocal).trim();
        stats.setDrawsAsVisitor(getLongResult(Q).intValue());
        // Local loses
        Q = String.format("%s %s and matchInfo.localGoals < matchInfo.visitorGoals %s%s", queryString, local, conditionString, rivalTeamVisitor).trim();
        stats.setLosesAsLocal(getLongResult(Q).intValue());
        // Visitor loses
        Q = String.format("%s %s and matchInfo.localGoals > matchInfo.visitorGoals %s%s", queryString, visitor, conditionString, rivalTeamLocal).trim();
        stats.setLosesAsVisitor(getLongResult(Q).intValue());
        
        return stats;
    }
  
    @Override
    public TeamStats getTeamStats (String team, int season, int day) {
        TeamStats stats = new TeamStats(team, season, day);
       
        // Local wins
        String queryString = String.format(
                "select count (*) from MatchInfo matchInfo where season<=%d and day<=%d and matchInfo.localGoals > matchInfo.visitorGoals "
                + "and matchInfo.localTeam = '%s'", 
                season, day, team);
        
        stats.setWinsAsLocal(getLongResult(queryString).intValue());
        
        // Visitor wins
        queryString = String.format(
                "select count (*) from MatchInfo matchInfo where season<=%d and day<=%d and matchInfo.localGoals < matchInfo.visitorGoals "
                + "and matchInfo.visitorTeam = '%s'", 
                season, day, team);
        
        stats.setWinsAsVisitor(getLongResult(queryString).intValue());
        
        // Local draws
        queryString = String.format(
                "select count (*) from MatchInfo matchInfo where season<=%d and day<=%d and matchInfo.localGoals = matchInfo.visitorGoals "
                + "and matchInfo.localTeam = '%s'", 
                season, day, team);
        
        stats.setDrawsAsLocal(getLongResult(queryString).intValue());
        
        // Visitor draws
        queryString = String.format(
                "select count (*) from MatchInfo matchInfo where season<=%d and day<=%d and matchInfo.localGoals = matchInfo.visitorGoals "
                + "and matchInfo.visitorTeam = '%s'", 
                season, day, team);
        
        stats.setDrawsAsVisitor(getLongResult(queryString).intValue());
        
        // Local loses
        queryString = String.format(
                "select count (*) from MatchInfo matchInfo where season<=%d and day<=%d and matchInfo.localGoals < matchInfo.visitorGoals "
                + "and matchInfo.localTeam = '%s'", 
                season, day, team);
        
        stats.setLosesAsLocal(getLongResult(queryString).intValue());
        
        // Visitor loses
        queryString = String.format(
                "select count (*) from MatchInfo matchInfo where season<=%d and day<=%d and matchInfo.localGoals > matchInfo.visitorGoals "
                + "and matchInfo.visitorTeam = '%s'", 
                season, day, team);
        
        stats.setLosesAsVisitor(getLongResult(queryString).intValue());        
        
        return stats;
    }   
    
    @Override
    public TeamStats getTeamStatsAgainstRival (String team, String rival, int season, int day) {
        TeamStats stats = new TeamStats(team, season, day);
        stats.setRival(rival);
       
        // Local wins
        String queryString = String.format(
                "select count (*) from MatchInfo matchInfo where season<=%d and day<=%d and matchInfo.localGoals > matchInfo.visitorGoals "
                + "and matchInfo.localTeam = '%s' and matchInfo.visitorTeam = '%s'", 
                season, day, team, rival);
        
        stats.setWinsAsLocal(getLongResult(queryString).intValue());
        
        // Visitor wins
        queryString = String.format(
                "select count (*) from MatchInfo matchInfo where season<=%d and day<=%d and matchInfo.localGoals < matchInfo.visitorGoals "
                + "and matchInfo.visitorTeam = '%s' and matchInfo.localTeam = '%s'", 
                season, day, team, rival);
        
        stats.setWinsAsVisitor(getLongResult(queryString).intValue());
        
        // Local draws
        queryString = String.format(
                "select count (*) from MatchInfo matchInfo where season<=%d and day<=%d and matchInfo.localGoals = matchInfo.visitorGoals "
                + "and matchInfo.localTeam = '%s' and matchInfo.visitorTeam = '%s'", 
                season, day, team, rival);
        
        stats.setDrawsAsLocal(getLongResult(queryString).intValue());
        
        // Visitor draws
        queryString = String.format(
                "select count (*) from MatchInfo matchInfo where season<=%d and day<=%d and matchInfo.localGoals = matchInfo.visitorGoals "
                + "and matchInfo.visitorTeam = '%s' and matchInfo.localTeam = '%s'", 
                season, day, team, rival);
        
        stats.setDrawsAsVisitor(getLongResult(queryString).intValue());
        
        // Local loses
        queryString = String.format(
                "select count (*) from MatchInfo matchInfo where season<=%d and day<=%d and matchInfo.localGoals < matchInfo.visitorGoals "
                + "and matchInfo.localTeam = '%s' and matchInfo.visitorTeam = '%s'", 
                season, day, team, rival);
        
        stats.setLosesAsLocal(getLongResult(queryString).intValue());
        
        // Visitor loses
        queryString = String.format(
                "select count (*) from MatchInfo matchInfo where season<=%d and day<=%d and matchInfo.localGoals > matchInfo.visitorGoals "
                + "and matchInfo.visitorTeam = '%s' and matchInfo.localTeam = '%s'", 
                season, day, team, rival);
        
        stats.setLosesAsVisitor(getLongResult(queryString).intValue());        
        
        return stats;
    }
    
    @Override
    public List<MatchInfo> getTeamMatches (String team, int season, int day) {
        String queryString = String.format(
                "from MatchInfo matchInfo where season<=%d and day<= %d and (matchInfo.localTeam='%s' or matchInfo.visitorTeam='%d')", 
                season,day,team,team);
        
        Query query = sessionFactory.getCurrentSession().createQuery(queryString);
        List<MatchInfo> matches = query.list();
        return matches;
    }
    
    private Long getLongResult (String queryString) {
        Query query = sessionFactory.getCurrentSession().createQuery(queryString);
        return (Long) query.uniqueResult();
    }
}
