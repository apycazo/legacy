
package com.github.apycazo.quinielator2.test;

import com.github.apycazo.quinielator2.prediction.PredictionResult;
import com.github.apycazo.quinielator2.prediction.PredictionResult.Mark;
import com.github.apycazo.quinielator2.prediction.PredictionResult.MatchResult;
import org.junit.Test;
import static org.junit.Assert.*;

public class PredictionResultTest {
    


    /**
     * Test of addMatchResult method, of class PredictionResult.
     */
    @Test
    public void testAddAndGetMatchResult() {
        System.out.println("addMatchResult");
        String local = "A";
        String visitor = "B";
        String winner = "A";
        int season = 1800;
        int day = 1;
        PredictionResult instance = new PredictionResult(season, day);
        instance.addMatchResult(local, visitor, winner);
        MatchResult [] results = instance.getResults();
        assertEquals(1, results.length);
        assertEquals(local, results[0].local);
        assertEquals(visitor, results[0].visitor);
        assertEquals(PredictionResult.Mark.win, results[0].mark);
    }

    /**
     * Test of toString method, of class PredictionResult.
     */
    @Test
    public void testGetMatch() {
        System.out.println("addMatchResult");
        int season = 1800;
        int day = 1;
        PredictionResult instance = new PredictionResult(season, day);
        String [] teams = {"A","B","C","D","E","F"};
        
        for (int i = 0; i < teams.length; i = i + 2) {
            instance.addMatchResult(teams[i], teams[i+1], teams[i]);
        }
        
        Mark mark = instance.getMatchResult("C", "D");
        assertEquals(Mark.win, mark);
        
        mark = instance.getMatchResult("C", "Y");
        assertEquals(Mark.unset, mark);
        
        System.out.println(instance.toString());

    }
}