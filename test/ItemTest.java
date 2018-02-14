import com.google.gson.Gson;
import org.junit.Before;
import org.junit.Rule;
import org.junit.*;

import static org.junit.Assert.assertEquals;

public class ItemTest {
    private static Adventure adventure;

    @Before
    public void setUp() throws Exception {
        Gson gson = new Gson();
        adventure = LinkParse.parse("siebel.json");
    }

    @Test
    public void getItemNameTest() {
        assertEquals("coin", adventure.getRooms()[0].getItems().get(0).getName());
    }

    @Test
    public void getItemDamageTest() {
        assertEquals(40.3, adventure.getRooms()[0].getItems().get(0).getDamage(), 0);
    }

    @Test
    public void getToStringTest() {
        assertEquals("Item: coin, Damage: 40.3", adventure.getRooms()[0].getItems().get(0).toString());
    }
}
