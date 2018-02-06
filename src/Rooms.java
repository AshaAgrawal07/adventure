import java.util.ArrayList;

public class Rooms {

    private String name;
    private String description;
    private ArrayList<String> items;
    private Directions[] directions;

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public ArrayList<String> getItems() {
        return items;
    }

    public Directions[] getDirections() {
        return directions;
    }

}
