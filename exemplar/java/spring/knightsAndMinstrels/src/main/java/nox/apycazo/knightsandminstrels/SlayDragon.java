
package nox.apycazo.knightsandminstrels;


public class SlayDragon implements Quest {
    
    private String questName;
    private String dragonName;
    
    public SlayDragon () {
        this.questName = "slay ";
        this.dragonName = "a dragon!";
    }
    
    public void setDragonName (String dragonName) {
        this.dragonName = dragonName;
    }

    @Override
    public String getQuest() {
        return this.questName + this.dragonName;
    }
}
