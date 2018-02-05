import com.google.gson.Gson;
import org.junit.Before;
import org.junit.Rule;
import org.junit.*;
import org.junit.rules.ExpectedException;

import java.lang.reflect.Array;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class AdventureTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();


    private Adventure roomInAdventure;
    private Adventure[] roomsInAdventure;

    @Before
    public void setUp() throws Exception {
        Gson gson = new Gson();
        roomInAdventure = gson.fromJson(ROOM_JSON, Adventure.class);
        //classesArray = gson.fromJson(CLASSES_JSON_ARRAY, Adventure[].class);
    }

    private static final String ROOM_JSON = "  {\n" +
            "      \"name\": \"MatthewsStreet\",\n" +
            "      \"description\": \"You are on Matthews, outside the Siebel Center\",\n" +
            "      \"items\": [\"coin\"],\n" +
            "      \"directions\": [\n" +
            "        {\n" +
            "          \"directionName\": \"East\",\n" +
            "          \"room\": \"SiebelEntry\"\n" +
            "        }\n" +
            "      ]\n" +
            "    }\n";

    @Test
    public void getNameTest() {
        assertEquals("MatthewsStreet", roomInAdventure.getName());
    }

    @Test
    public void getDescriptionTest() {
        assertEquals("You are on Matthews, outside the Siebel Center", roomInAdventure.getDescription());
    }

    @Test
    public void getItemsTest() {
        String[] itemInRoom = {"coin"};
        assertArrayEquals(itemInRoom, roomInAdventure.getItems());
    }

    @Test
    public void getDirectionNameTest() {
        assertEquals("East", roomInAdventure.getDirectionName());
    }

    @Test
    public void getRoomTest() {
        assertEquals("SiebelEntry", roomInAdventure.getRoom());
    }

    @Test
    public void getDirectionsTest() {
        String[] directionsExpected = {"East", "SiebelEntry"};
        assertArrayEquals(directionsExpected, roomInAdventure.getDirections());
    }

}
