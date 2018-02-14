import com.google.gson.Gson;
import org.junit.Before;
import org.junit.Rule;
import org.junit.*;
import org.junit.rules.ExpectedException;


import java.lang.reflect.Array;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class DirectionsTest {

    private static Adventure adventure;

    @Before
    public void setUp() throws Exception {
        Gson gson = new Gson();
        adventure = LinkParse.parse("siebel.json");
    }

    @Test
    public void getDirectionNameTest() {
        assertEquals("East", adventure.getRooms()[0].getDirections()[0].getDirectionName());
    }

    @Test
    public void getRoomTest() {
        assertEquals("SiebelEntry", adventure.getRooms()[0].getDirections()[0].getRoom());
    }
}