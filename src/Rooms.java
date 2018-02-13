import java.util.ArrayList;

public class Rooms {

    private String name;
    private String description;
    //private Item[] items;
    private ArrayList<Item> items;
    private Directions[] directions;
    private String[] monstersInRoom;

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public Directions[] getDirections() {
        return directions;
    }

    public String[] getMonstersInRoom () { return monstersInRoom;}
}
