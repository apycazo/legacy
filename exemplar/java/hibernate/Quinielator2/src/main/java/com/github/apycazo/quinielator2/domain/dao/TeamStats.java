package com.github.apycazo.quinielator2.domain.dao;

public class TeamStats {
    
    public TeamStats () {
        this.wins = new int[2];
        this.draws = new int[2];
        this.loses = new int[2];
        this.team = "undefined";
        this.rival = null;
    }
    
    public TeamStats (String team , int season, int day) {
        this();
        this.team = team;
        this.season = season;
        this.day = day;
    }
    
    public void setWinsAsLocal (int v) {
        this.wins[0] = v;
    }
    
    public void setWinsAsVisitor (int v) {
        this.wins[1] = v;
    }
    
    public void setDrawsAsLocal (int v) {
        this.draws[0] = v;
    }
    
    public void setDrawsAsVisitor (int v) {
        this.draws[1] = v;
    }
    
    public void setLosesAsLocal (int v) {
        this.loses[0] = v;
    }
    
    public void setLosesAsVisitor (int v) {
        this.loses[1] = v;
    }
    
    public int getWinsAsLocal () {
        return this.wins[0];
    }
    
    public int getWinsAsVisitor () {
        return this.wins[1];
    }
    
    public int getWins () {
        return this.wins[0] + this.wins[1];
    }
    
    public int getDrawsAsLocal () {
        return this.draws[0];
    }
    
    public int getDrawsAsVisitor () {
        return this.draws[1];
    }
    
    public int getDraws () {
        return this.draws[0] + this.draws[1];
    }
    
    public int getLosesAsLocal () {
        return this.loses[0];
    }
    
    public int getLosesAsVisitor () {
        return this.loses[1];
    }
    
    public int getLoses () {
        return this.loses[0] + this.loses[1];
    }
    
    public int getSeason () {
        return this.season;
    }
    
    public int getDay () {
        return this.day;
    }
    
    public String getTeam () {
        return this.team;
    }
    
    public String getRival () {
        return this.rival;
    }
    
    public void setTeam (String team) {
        this.team = team;
    }
    
    public void setRival (String rival) {
        this.rival = rival;
    }
    
    @Override
    public String toString () {
        String vs = getRival() == null ? "" : " vs " + getRival();
        return String.format(
                "Team stats for: %s%s. Wins: %d, Draws: %d, Loses: %d (S:%d, D:%d)\n"
                + "\tLocal wins: %d, Visitor wins: %d\n"
                + "\tLocal draws: %d, Visitor draws: %d\n"
                + "\tLocal loses: %d, Visitor loses: %d", 
                getTeam(), vs, getWins(), getDraws(), getLoses(), getSeason(), getDay(),
                getWinsAsLocal(), getWinsAsVisitor(),
                getDrawsAsLocal(), getDrawsAsVisitor(),
                getLosesAsLocal(), getLosesAsVisitor());
    }

    private String team, rival;
    private int season, day;
    private int [] wins, draws, loses;
}
