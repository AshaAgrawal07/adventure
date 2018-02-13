import com.google.gson.Gson;
import org.junit.Before;
import org.junit.Rule;
import org.junit.*;
import org.junit.rules.ExpectedException;


import java.lang.reflect.Array;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class MainTest {

    @Before
    public void setUp() throws Exception {
        Gson gson = new Gson();
        LinkParse.parse("https://courses.engr.illinois.edu/cs126/adventure/siebel.json");
    }

    @Test
    public void getGoOnExit() {
        assertEquals(false, Game.goOn("exit", "MatthewsStreet"));
    }

    @Test
    public void getGoOnQuit() {
        assertEquals(false, Game.goOn("quit", "MatthewsStreet"));
    }

    @Test
    public void getGoOnFinalRoom() {
        assertEquals(false, Game.goOn("south", "Siebel1314"));
    }

    @Test
    public void getGoesOn() {
        assertEquals(true, Game.goOn("south", "MatthewsStreet"));
    }

    @Test
    public void getSpecialRoomStart() {
        assertEquals("Your journey to reclaim your honor begins here", Game.specialRoom("MatthewsStreet"));
    }

    @Test
    public void getSpecialRoomEnd() {
        assertEquals("You have reached your final destination and have finally reclaimed your honor", Game.specialRoom("Siebel1314"));
    }

    @Test
    public void getNonSpecialRoom() {
        assertEquals("", Game.specialRoom("ISR"));
    }

    @Test
    public void getMovesAvailable() {
        assertEquals("From here, you can go: East", Game.movesAvailable("MatthewsStreet"));
    }

    @Test
    public void getItemsTake() {
        assertEquals("You are carrying: [coin]", Game.itemTakeOrDrop("take coin", "MatthewsStreet"));
    }

    @Test
    public void getIsValidMoveTrue() {
        assertEquals(true, Game.validMove("go east", "MatthewsStreet"));
    }

    @Test
    public void getIsValidMoveFalse() {
        assertEquals(false, Game.validMove("go south", "MatthewsStreet"));
    }

    @Test
    public void getMoved() {
        assertEquals("SiebelEntry", Game.moved("go east", "MatthewsStreet"));
    }

    @Test
    public void getDescriptionMain() {
        assertEquals("You are on Matthews, outside the Siebel Center", Game.describe("MatthewsStreet"));
    }
}
