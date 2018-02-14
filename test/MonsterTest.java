import com.google.gson.Gson;
import org.junit.Before;
import org.junit.Rule;
import org.junit.*;

import static org.junit.Assert.assertEquals;


public class MonsterTest {

    private static Adventure adventure;

    @Before
    public void setUp() throws Exception {
        Gson gson = new Gson();
        adventure = LinkParse.parse("siebel.json");
    }

    @Test
    public void getNameTest() {
        assertEquals("Yagami Light", adventure.getMonsters()[0].getName());
        assertEquals("Vegeta", adventure.getMonsters()[1].getName());
        assertEquals("Inuyasha", adventure.getMonsters()[2].getName());
        assertEquals("Uchiha Itaachi", adventure.getMonsters()[3].getName());
    }

    @Test
    public void getAttackTest() {
        assertEquals(30, adventure.getMonsters()[0].getAttack(), 0);
        assertEquals(67, adventure.getMonsters()[1].getAttack(), 0);
        assertEquals(80, adventure.getMonsters()[2].getAttack(), 0);
        assertEquals(90, adventure.getMonsters()[3].getAttack(), 0);
    }

    @Test
    public void getDefenseTest() {
        assertEquals(15, adventure.getMonsters()[0].getDefense(), 0);
        assertEquals(30, adventure.getMonsters()[1].getDefense(), 0);
        assertEquals(20, adventure.getMonsters()[2].getDefense(), 0);
        assertEquals(90, adventure.getMonsters()[3].getDefense(), 0);
    }

    @Test
    public void getHealthTest() {
        assertEquals(10, adventure.getMonsters()[0].getHealth(), 0);
        assertEquals(5, adventure.getMonsters()[1].getHealth(), 0);
        assertEquals(30, adventure.getMonsters()[2].getHealth(), 0);
        assertEquals(90, adventure.getMonsters()[3].getHealth(), 0);
    }
}
