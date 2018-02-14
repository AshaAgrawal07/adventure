import com.google.gson.Gson;
import org.junit.Before;
import org.junit.Rule;
import org.junit.*;

import static org.junit.Assert.assertEquals;

public class PlayerTest {
    @Before
    public void setUp() throws Exception {
        Gson gson = new Gson();
        LinkParse.parse("https://courses.engr.illinois.edu/cs126/adventure/siebel.json");
    }

    @Test
    public void getNameTest() {
        assertEquals("Zuko", LinkParse.adventure.getPlayer().getName());
    }

    @Test
    public void getAttackTest() {
        assertEquals(500, LinkParse.adventure.getPlayer().getAttack(), 0);
    }

    @Test
    public void getDefenseTest() {
        assertEquals(500, LinkParse.adventure.getPlayer().getDefense(), 0);
    }

    @Test
    public void getHealthTest() {
        assertEquals(100, LinkParse.adventure.getPlayer().getHealth(), 0);
    }

    @Test
    public void getLevelTest() {
        assertEquals(1, LinkParse.adventure.getPlayer().getLevel(), 0);
    }

    @Test
    public void getItemsTest() {
        assertEquals("frying pan", LinkParse.adventure.getPlayer().getItems().get(0).getName());
        assertEquals(9001.0, LinkParse.adventure.getPlayer().getItems().get(0).getDamage(), 0);
        assertEquals("honor", LinkParse.adventure.getPlayer().getItems().get(1).getName());
        assertEquals(0, LinkParse.adventure.getPlayer().getItems().get(1).getDamage(), 0);
    }
}
