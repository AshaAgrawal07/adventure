import com.google.gson.Gson;
import org.junit.Before;
import org.junit.Rule;
import org.junit.*;
import org.junit.rules.ExpectedException;


import java.util.ArrayList;

import static org.junit.Assert.*;

public class AdventureTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

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
    public void getDirectionNameTest() {
        assertEquals("East", LinkParse.adventure.getRooms()[0].getDirections()[0].getDirectionName());
    }

    @Test
    public void getRoomTest() {
        assertEquals("SiebelEntry", LinkParse.adventure.getRooms()[0].getDirections()[0].getRoom());
    }

    @Test
    public void getDirectionsTest() {
        String[] directionsExpected = {"East", "SiebelEntry"};
        String[] directionActual = {LinkParse.adventure.getRooms()[0].getDirections()[0].getDirectionName(),
                LinkParse.adventure.getRooms()[0].getDirections()[0].getRoom()};
        assertArrayEquals(directionsExpected, directionActual);
    }

    @Test
    public void getStartingRoomTest() {
        assertEquals("MatthewsStreet", LinkParse.adventure.getStartingRoom());
    }

    @Test
    public void getEndingRoomTest() {
        assertEquals("Siebel1314", LinkParse.adventure.getEndingRoom());
    }

    @Test
    public void getGoOnExit() {
        assertEquals(false, Main.goOn("exit", "MatthewsStreet"));
    }

    @Test
    public void getGoOnQuit() {
        assertEquals(false, Main.goOn("quit", "MatthewsStreet"));
    }

    @Test
    public void getGoOnFinalRoom() {
        assertEquals(false, Main.goOn("south", "Siebel1314"));
    }

    @Test
    public void getGoesOn() {
        assertEquals(true, Main.goOn("south", "MatthewsStreet"));
    }

    @Test
    public void getSpecialRoomStart() {
        assertEquals("Your journey begins here", Main.specialRoom("MatthewsStreet"));
    }

    @Test
    public void getSpecialRoomEnd() {
        assertEquals("You have reached your final destination", Main.specialRoom("Siebel1314"));
    }

    @Test
    public void getNonSpecialRoom() {
        assertEquals("", Main.specialRoom("ISR"));
    }

}
