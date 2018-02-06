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
   // private Adventure[] roomsInAdventure;

    @Before
    public void setUp() throws Exception {
        Gson gson = new Gson();
        LinkParse.parse("https://courses.engr.illinois.edu/cs126/adventure/siebel.json");
        //roomInAdventure = gson.fromJson(ROOM_JSON, Adventure.class);
       // roomsInAdventure = gson.fromJson(ROOMS_JSON_ARRAY, Adventure[].class);
    }


    @Test
    public void getNameTest() {
        assertEquals("MatthewsStreet", LinkParse.adventure.getRooms()[0].getName());
    }

    @Test
    public void getDescriptionTest() {
        assertEquals("You are on Matthews, outside the Siebel Center",
                LinkParse.adventure.getRooms()[0].getDescription());
    }

    @Test
    public void getItemsTest() {
        String[] itemInRoom = {"coin"};
        assertArrayEquals(itemInRoom, LinkParse.adventure.getRooms()[0].getItems());
    }

    @Test
    public void getDirectionNameTest() {
        assertEquals("East", LinkParse.adventure.getRooms()[0].getDirections()[0].getDirectionName());
    }

    @Test
    public void getRoomTest() {
        assertEquals("SiebelEntry", LinkParse.adventure.getRooms()[0].getDirections()[1].getRoom());
    }

    @Test
    public void getDirectionsTest() {
        String[] directionsExpected = {"East", "SiebelEntry"};
        assertArrayEquals(directionsExpected, LinkParse.adventure.getRooms()[0].getDirections());
    }

    @Test
    public void getNameArrayTest() {
        assertEquals("MatthewsStreet", roomsInAdventure[0].getName());
    }

    @Test
    public void getDescriptionArrayTest() {
        assertEquals("You are on Matthews, outside the Siebel Center", roomsInAdventure[0].getDescription());
    }

    @Test
    public void getItemsArrayTest() {
        String[] itemInRoom = {"coin"};
        assertArrayEquals(itemInRoom, roomsInAdventure[0].getItems());
    }

    @Test
    public void getDirectionNameArrayTest() {
        assertEquals("East", roomsInAdventure[0].getDirectionName());
    }

    @Test
    public void getRoomArrayTest() {
        assertEquals("SiebelEntry", roomsInAdventure[0].getRoom());
    }

    @Test
    public void getDirectionsArrayTest() {
        String[] directionsExpected = {"East", "SiebelEntry"};
        assertArrayEquals(directionsExpected, roomsInAdventure[0].getDirections());
    }

    @Test
    public void getStartingRoomTest() {
      //  assertEquals("MatthewStreet", roomsInAdventure.getStartingRoom());
    }
}
