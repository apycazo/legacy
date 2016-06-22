package nox.apycazo.knightsandminstrels;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {
    public static void main( String[] args ) {
        System.out.println( "Hello World!" );
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:/META-INF/config.xml");
        BraveKnight knight = (BraveKnight) context.getBean("knight");
        System.out.println("========================================================================");
        knight.embarkOnQuest();
        System.out.println("========================================================================");
    }
}
