import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Adventure {

    private Rooms[] rooms;
    private String startingRoom;
    private String endingRoom;

    public String getStartingRoom() {
        return startingRoom;
    }

    public String getEndingRoom() {
        return endingRoom;
    }

    public Rooms[] getRooms() {
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
