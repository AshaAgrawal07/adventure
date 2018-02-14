import com.google.gson.Gson;
import org.junit.Before;
import org.junit.Rule;
import org.junit.*;

import static org.junit.Assert.assertEquals;

public class PlayerTest {

    private static Adventure adventure;

    @Before
    public void setUp() throws Exception {
        Gson gson = new Gson();
        adventure = LinkParse.parse("siebel.json");
    }

    @Test
    public void getNameTest() {
        assertEquals("Zuko", adventure.getPlayer().getName());
    }

    @Test
    public void getAttackTest() {
        assertEquals(50, adventure.getPlayer().getAttack(), 0);
    }

    @Test
    public void getDefenseTest() {
        assertEquals(50, adventure.getPlayer().getDefense(), 0);
    }

    @Test
    public void getHealthTest() {
        assertEquals(100, adventure.getPlayer().getHealth(), 0);
    }

    @Test
    public void getLevelTest() {
        assertEquals(1, adventure.getPlayer().getLevel(), 0);
    }

    @Test
    public void getItemsTest() {
        assertEquals("frying pan", adventure.getPlayer().getItems().get(0).getName());
        assertEquals(9001.0, adventure.getPlayer().getItems().get(0).getDamage(), 0);
        assertEquals("honor", adventure.getPlayer().getItems().get(1).getName());
        assertEquals(0, adventure.getPlayer().getItems().get(1).getDamage(), 0);
    }
}
