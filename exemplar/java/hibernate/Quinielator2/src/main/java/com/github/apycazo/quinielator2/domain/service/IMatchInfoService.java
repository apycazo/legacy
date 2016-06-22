
package com.github.apycazo.quinielator2.domain.service;

import com.github.apycazo.quinielator2.domain.dao.TeamStats;
import com.github.apycazo.quinielator2.domain.model.MatchInfo;
import java.util.List;

public interface IMatchInfoService {
    
    public void saveOrUpdate (MatchInfo matchInfo);
    public List<MatchInfo> getMatchInfoList();
    
    public long save (MatchInfo matchInfo);
    public void update (MatchInfo matchInfo);
    public void delete (MatchInfo matchInfo);
    public MatchInfo getById (long matchInfoId);
    public List<MatchInfo> getByDate (int season, int day);
    public int getLastSeason();
    public int getLastDay();
    public String [] getSeasonTeams (int season);
    public TeamStats getTeamStats (String team, int season, int day);
    public List<MatchInfo> getTeamMatches (String team, int season, int day);
    public TeamStats getTeamStatsAgainstRival (String team, String rival, int season, int day);
    public TeamStats getTeamStats (String team, String rival, String[] conditions);
}
