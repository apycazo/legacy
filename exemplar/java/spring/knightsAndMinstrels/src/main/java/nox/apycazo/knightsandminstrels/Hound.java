
package nox.apycazo.knightsandminstrels;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

@Aspect
public class Hound {
    
    public Hound () {}
    
    @Before ("execution(* *.embarkOnQuest(..))")
    public void woof (JoinPoint joinPoint) {
        System.out.println("Hound: Woof!");
        // joinPoint.getSignature().getName() == embarkOnQuest
    }
    
}
