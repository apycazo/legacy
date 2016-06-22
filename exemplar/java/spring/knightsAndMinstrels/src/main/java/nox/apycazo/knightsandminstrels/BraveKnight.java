
package nox.apycazo.knightsandminstrels;


public class BraveKnight {
    
    private Quest quest;
    private String name;
    
    public BraveKnight () {}
    
    public BraveKnight (String name, Quest quest) {
        this.name = name;
        this.quest = quest;
    }
    
    public void embarkOnQuest () {
        System.out.println(this.name + " embarks on a quest to " + this.quest.getQuest());
    }
}
