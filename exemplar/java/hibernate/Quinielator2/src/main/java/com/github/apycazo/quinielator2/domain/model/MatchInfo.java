
package com.github.apycazo.quinielator2.domain.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/*
mysql> select * from MATCH_DATA;
+----+-----------+----------+------------+-----------+----------+--------+--------------+-------------+
| id | match_day | division | localGoals | localTeam | match_id | season | visitorGoals | visitorTeam |
+----+-----------+----------+------------+-----------+----------+--------+--------------+-------------+
|  1 |         1 |        1 |          3 | TEST-1    |        0 |      0 |            2 | TEST-2      |
+----+-----------+----------+------------+-----------+----------+--------+--------------+-------------+
* 
* Extra fields: wins, draws, loses, position
*/

@Entity @Table(name = "MATCH_DATA")
public class MatchInfo implements Serializable {
    
    private long id;
    private int season;
    private int division;
    
    private int day;
    private int match;
    private String localTeam;
    private String visitorTeam;
    private int localGoals;
    private int visitorGoals;
    
    public MatchInfo () {
        id = 0;
    }

    @Id
    //@GeneratedValue
    public long getId() {
        if (this.id == 0) {
            String s = String.format("%d%d%02d%02d", division,season,day,match);
            this.id = Long.parseLong(s);
        }        
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    public int getSeason() {
        return season;
    }

    public void setSeason(int season) {
        this.season = season;
    }
    
    @Basic
    public int getDivision() {
        return division;
    }

    public void setDivision(int division) {
        this.division = division;
    }

    @Basic @Column(name="match_day")
    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    @Basic @Column(name="match_id")
    public int getMatch() {
        return match;
    }

    public void setMatch(int match) {
        this.match = match;
    }
    
    @Basic
    public String getLocalTeam() {
        return localTeam;
    }

    public void setLocalTeam(String localTeam) {
        this.localTeam = localTeam;
    }
    
    @Basic
    public String getVisitorTeam() {
        return visitorTeam;
    }

    public void setVisitorTeam(String visitorTeam) {
        this.visitorTeam = visitorTeam;
    }
    
    @Basic
    public int getLocalGoals() {
        return localGoals;
    }

    public void setLocalGoals(int localGoals) {
        this.localGoals = localGoals;
    }
    
    @Basic
    public int getVisitorGoals() {
        return visitorGoals;
    }

    public void setVisitorGoals(int visitorGoals) {
        this.visitorGoals = visitorGoals;
    }
    
    @Override
    public String toString () {
        String s = String.format("{%d} [%d] Match %d : %s vs %s. Result: %d-%d", 
                id, season, day, localTeam, visitorTeam, localGoals, visitorGoals);
        return s;
    }

}
