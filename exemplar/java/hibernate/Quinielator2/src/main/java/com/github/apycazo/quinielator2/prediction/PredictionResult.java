
package com.github.apycazo.quinielator2.prediction;

import java.util.ArrayList;

public class PredictionResult {
    
    public static enum Mark { 
        win("1"), draw("X"), loss("2"), unset("U");
        private String str;
        
        Mark (String str) {
            this.str = str;
        }
        
        String getStringValue () {
            return this.str;
        }
    }
    
    public class MatchResult {
        public String local, visitor;
        public Mark mark;
        
        public MatchResult () {
            local = "unset";
            visitor = "unset";
            mark = Mark.unset;
        }
    }
    
    private int season, day;    
    private ArrayList<MatchResult> results;
    
    public PredictionResult (int season, int day) {
        this.season = season;
        this.day = day;
        results = new ArrayList<>();
    }
    
    public void addMatchResult (String local, String visitor, String winner) {
        MatchResult mr = new MatchResult();
        mr.local = local;
        mr.visitor = visitor;
        if (winner.equals(local)) mr.mark = Mark.win;
        else if (winner.equals(visitor)) mr.mark = Mark.loss;
        else mr.mark = Mark.draw;
        results.add(mr);
    }
    
    public Mark getMatchResult (String local, String visitor) {
        boolean found = false;
        int i = 0;
        while (i < results.size() && !found) {
            MatchResult current = results.get(i);
            if (current.local.equals(local) && current.visitor.equals(visitor)) {
                found = true;
            }
            else i++;
        }
        if (found) return results.get(i).mark;
        else return Mark.unset;
    }
    
    public MatchResult[] getResults () {
        return results.toArray(new MatchResult [results.size()]);
    }
    
    @Override
    public String toString () {
        StringBuilder sb = new StringBuilder("Matches (S:")
                .append(season).append(", D:").append(day).append(")\n");
        for (MatchResult mr : results) {
            sb.append(mr.local).append(" vs ").append(mr.visitor);
            sb.append(" [").append(mr.mark.getStringValue()).append("]\n");
        }        
        return sb.toString();
    }
}
