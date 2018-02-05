import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Adventure {

    private String name;
    private String description;
    private String[] items;
    private String directionName;
    private String room;
    private String[] directions;
    private String[] rooms;

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String[] getItems() {
        return items;
    }

    public String getDirectionName() {
        return directionName;
    }

    public String getRoom() {
        return room;
    }

    public String[] getDirections() {
        return directions;
    }

    public String[] getRooms() {
        return rooms;
    }



    public static ArrayList<Adventure> convertToList() {
        List<String> files = Data.getJsonFilesAsList();
        ArrayList<Adventure> filesIntoObjects = new ArrayList<>();

        for (String filename : files) {
            Gson gson = new Gson();
            String str = Data.getFileContentsAsString(filename);
            filesIntoObjects.addAll(Arrays.asList(gson.fromJson(str, Adventure[].class)));
        }
        return filesIntoObjects;
    }

}
