import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Adventure {

    private Rooms[] rooms;
    private String startingRoom;
    private String endingRoom;
    private Player player;
    private Monster[] monsters;

    public Player getPlayer() { return player; }

    public String getStartingRoom() {
        return startingRoom;
    }

    public String getEndingRoom() {
        return endingRoom;
    }

    public Rooms[] getRooms() {
        return rooms;
    }

    public Monster[] getMonsters() { return monsters; }

}
