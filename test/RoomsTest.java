import com.google.gson.Gson;
import org.junit.Before;
import org.junit.Rule;
import org.junit.*;
import org.junit.rules.ExpectedException;


import java.lang.reflect.Array;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class RoomsTest {

    @Before
    public void setUp() throws Exception {
        Gson gson = new Gson();
        LinkParse.parse("https://courses.engr.illinois.edu/cs126/adventure/siebel.json");
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
        ArrayList<String> itemInRoom = new ArrayList<>();
        itemInRoom.add("coin");
        assertEquals(itemInRoom, LinkParse.adventure.getRooms()[0].getItems());
    }

    @Test
    public void getDirectionsTest() {
        String[] directionsExpected = {"East", "SiebelEntry"};
        String[] directionActual = {LinkParse.adventure.getRooms()[0].getDirections()[0].getDirectionName(),
                LinkParse.adventure.getRooms()[0].getDirections()[0].getRoom()};
        assertArrayEquals(directionsExpected, directionActual);
    }
}
