
package com.github.apycazo.quinielator2.test;

import com.github.apycazo.quinielator2.domain.service.MatchInfoService;
import com.github.apycazo.quinielator2.network.MatchDataDownloader;
import org.junit.Test;
import static org.junit.Assert.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class HttpTest {
    
    @Test
    public void CheckHttpInjection () {
        ApplicationContext appContext = new ClassPathXmlApplicationContext("classpath:/spring.cfg.xml");
        System.out.println("Context loaded");
        
        String beanName = "downloader";
        MatchDataDownloader downloader = (MatchDataDownloader) appContext.getBean(beanName);
        assertTrue ("No bean named " + beanName + " found.", downloader.getHttpWrapper() != null);        
        
//        int from = 2000;
//        int to = 2013;
//        int size = to-from;
//        int [] seasons = new int[size];
//        for (int i = 0; i < size; i++) {
//            seasons[i] = from + i;
//        }
//        
//        downloader.setVerbose(true);
//        System.out.println("Downloading data");
//        boolean result = downloader.updateDatabase(seasons,1);
//        assertTrue("Update database returned an error", result);
        
        // Check last values return something
        System.out.println("Getting match info service bean");
        MatchInfoService service = (MatchInfoService) appContext.getBean("matchInfoService");
        assertTrue (service != null);
        System.out.println("Got it!");
        
        System.out.println("Getting last season value");
        int lastSeasonValue = service.getLastSeason();
        assertTrue (lastSeasonValue != 0);
        System.out.println("==> Last season : " + lastSeasonValue);
        
        int lastDayValue = service.getLastDay();
        assertTrue (lastDayValue != 0);
        System.out.println("==> Last day: " + lastDayValue);
        
        // Updating (not really a test!)
//        downloader.updateDatabaseLast();
//        lastSeasonValue = service.getLastSeason();
//        System.out.println("==> Last season : " + lastSeasonValue);
//        
//        lastDayValue = service.getLastDay();
//        System.out.println("==> Last day: " + lastDayValue);
    }
    
}
