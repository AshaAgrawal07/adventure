import java.util.ArrayList;

public class Player {
    private String name;
    private ArrayList<Item> items;
    private double defense;
    private double health;
    private double attack;
    private int level;

    public void setHealth (double health) {
        this.health = health;
    }

    public void setDefense(double def) {
        defense = def;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setAttack (double attac) {
        attack = attac;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public double getDefense() {
        return defense;
    }

    public double getHealth() {
        return health;
    }

    public double getAttack() {
        return attack;
    }

    public int getLevel() {
        return level;
    }

}
