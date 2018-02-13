import com.google.gson.Gson;
import org.junit.Before;
import org.junit.Rule;
import org.junit.*;

import static org.junit.Assert.assertEquals;

public class MonsterTest {
    @Before
    public void setUp() throws Exception {
        Gson gson = new Gson();
        LinkParse.parse("https://courses.engr.illinois.edu/cs126/adventure/siebel.json");
    }

    @Test
    public void getNameTest() {
        assertEquals("Yagami Light", LinkParse.adventure.getMonsters()[0].getName());
        assertEquals("Vegeta", LinkParse.adventure.getMonsters()[1].getName());
        assertEquals("Inuyasha", LinkParse.adventure.getMonsters()[2].getName());
        assertEquals("Uchiha Itaachi", LinkParse.adventure.getMonsters()[3].getName());
    }

    @Test
    public void getAttackTest() {
        assertEquals(30, LinkParse.adventure.getMonsters()[0].getAttack(), 0);
        assertEquals(67, LinkParse.adventure.getMonsters()[1].getAttack(), 0);
        assertEquals(80, LinkParse.adventure.getMonsters()[2].getAttack(), 0);
        assertEquals(90, LinkParse.adventure.getMonsters()[3].getAttack(), 0);
    }

    @Test
    public void getDefenseTest() {
        assertEquals(15, LinkParse.adventure.getMonsters()[0].getDefense(), 0);
        assertEquals(30, LinkParse.adventure.getMonsters()[1].getDefense(), 0);
        assertEquals(20, LinkParse.adventure.getMonsters()[2].getDefense(), 0);
        assertEquals(90, LinkParse.adventure.getMonsters()[3].getDefense(), 0);
    }

    @Test
    public void getHealthTest() {
        assertEquals(10, LinkParse.adventure.getMonsters()[0].getHealth(), 0);
        assertEquals(5, LinkParse.adventure.getMonsters()[1].getHealth(), 0);
        assertEquals(30, LinkParse.adventure.getMonsters()[2].getHealth(), 0);
        assertEquals(90, LinkParse.adventure.getMonsters()[3].getHealth(), 0);
    }
}
