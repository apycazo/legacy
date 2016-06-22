package com.github.apycazo.quinielator2.domain.service;

import com.github.apycazo.quinielator2.domain.dao.IMatchInfoDao;
import com.github.apycazo.quinielator2.domain.dao.TeamStats;
import com.github.apycazo.quinielator2.domain.model.MatchInfo;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/* Implements a DAO service */

@Service("matchInfoService")
@Transactional
public class MatchInfoService implements IMatchInfoService {

    @Autowired
    private IMatchInfoDao dao;

    @Override
    public void saveOrUpdate(MatchInfo matchInfo) {
        this.dao.saveOrUpdate(matchInfo);
    }

    @Override
    public List<MatchInfo> getMatchInfoList() {
        return this.dao.getMatchInfoList();
    }

    @Override
    public long save(MatchInfo matchInfo) {
        return this.dao.save(matchInfo);
    }

    @Override
    public void update(MatchInfo matchInfo) {
        this.dao.update(matchInfo);
    }

    @Override
    public void delete(MatchInfo matchInfo) {
        this.dao.delete(matchInfo);
    }

    @Override
    public MatchInfo getById(long matchInfoId) {
        return this.dao.getById(matchInfoId);
    }
    
    @Override
    public List<MatchInfo> getByDate (int season, int day) {
        return this.dao.getByDate(season, day);
    }
    
    @Override
    public int getLastSeason() {
        return this.dao.getLastSeason();
    }
    
    @Override
    public int getLastDay() {
        return this.dao.getLastDay();
    }
    
    @Override
    public String [] getSeasonTeams (int season) {
        return this.dao.getSeasonTeams(season);
    }    
    
    @Override
    public TeamStats getTeamStats (String team, int season, int day) {
        return this.dao.getTeamStats(team, season, day);
    }
    
    @Override
    public List<MatchInfo> getTeamMatches (String team, int season, int day) {
        return this.dao.getTeamMatches(team, season, day);
    }
    
    @Override
    public TeamStats getTeamStatsAgainstRival (String team, String rival, int season, int day) {
        return this.dao.getTeamStatsAgainstRival(team, rival, season, day);
    }
    
    @Override
    public TeamStats getTeamStats (String team, String rival, String[] conditions) {
        return this.dao.getTeamStats(team, rival, conditions);
    }
            
}
