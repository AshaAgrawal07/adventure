import com.google.gson.Gson;
import org.junit.Before;
import org.junit.Rule;
import org.junit.*;
import org.junit.rules.ExpectedException;


import java.lang.reflect.Array;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class RoomsTest {

    private static Adventure adventure;

    @Before
    public void setUp() throws Exception {
        Gson gson = new Gson();
        adventure = LinkParse.parse("siebel.json");
    }


    @Test
    public void getNameTest() {
        assertEquals("MatthewsStreet", adventure.getRooms()[0].getName());
    }

    @Test
    public void getDescriptionTest() {
        assertEquals("You are on Matthews, outside the Siebel Center",
                adventure.getRooms()[0].getDescription());
    }

    @Test
    public void getItemsTest() {
        ArrayList<Item> itemInRoom = new ArrayList<>();
        Item toAdd = new Item ("coin", 40.3);
        itemInRoom.add(toAdd);
        assertEquals(itemInRoom, adventure.getRooms()[0].getItems());
    }

    @Test
    public void getDirectionsTest() {
        String[] directionsExpected = {"East", "SiebelEntry"};
        String[] directionActual = {adventure.getRooms()[0].getDirections()[0].getDirectionName(),
                adventure.getRooms()[0].getDirections()[0].getRoom()};
        assertArrayEquals(directionsExpected, directionActual);
    }
}
