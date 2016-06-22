
package com.github.apycazo.quinielator2.prediction.algorithms;

import com.github.apycazo.quinielator2.domain.model.MatchInfo;
import com.github.apycazo.quinielator2.domain.service.MatchInfoService;
import com.github.apycazo.quinielator2.prediction.IPredictor;
import com.github.apycazo.quinielator2.prediction.PredictionResult;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

public class Basic1 implements IPredictor {
    
    @Autowired
    private MatchInfoService db;
    
    private List<MatchInfo>matches;
    
    // Saves statistics
    private class Stats {
        
    }
    
    public Basic1 () {
        matches = null;
    }

    @Override
    public PredictionResult predict(int season, int day) {
        
        if (matches == null) {
            // retrieve all matches
            matches = db.getMatchInfoList();
        }
        
        throw new UnsupportedOperationException("Not supported yet."); 
    }
    
}
