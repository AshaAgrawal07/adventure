import java.util.ArrayList;

public class Player {
    private String name;
    private ArrayList<Item> items;
    private double defense;
    private double health;
    private double attack;
    private int level;

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
