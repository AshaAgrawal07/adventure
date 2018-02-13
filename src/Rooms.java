import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Objects;

public class Rooms {

    private String name;
    private String description;
    private ArrayList<Item> items;
    //private HashMap<Integer, Item> items;
    private Directions[] directions;
    private String[] monstersInRoom;

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

    public String[] getMonstersInRoom () { return monstersInRoom;}

    public ArrayList<Item> getItems() {
        return items;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Rooms)) return false;
        Rooms rooms = (Rooms) o;
        return Objects.equals(getName(), rooms.getName()) &&
                Objects.equals(getDescription(), rooms.getDescription()) &&
                Objects.equals(getItems(), rooms.getItems()) &&
                Arrays.equals(getDirections(), rooms.getDirections()) &&
                Arrays.equals(getMonstersInRoom(), rooms.getMonstersInRoom());
    }

    /*@Override
    public int hashCode() {

        int result = Objects.hash(getName(), getDescription(), getItems());
        result = 31 * result + Arrays.hashCode(getDirections());
        result = 31 * result + Arrays.hashCode(getMonstersInRoom());
        return result;
    }*/
}
