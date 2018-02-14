import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Objects;

public class Rooms {

    private String name;
    private String description;
    private ArrayList<Item> items;
    private Directions[] directions;
    private ArrayList<String> monstersInRoom;

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    //public ArrayList<Item> getItems() {return items; }

    public Directions[] getDirections() {
        return directions;
    }

    public ArrayList<String> getMonstersInRoom () { return monstersInRoom;}

    public ArrayList<Item> getItems() {
        return items;
    }
}
