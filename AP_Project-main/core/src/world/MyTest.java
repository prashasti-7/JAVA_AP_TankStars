package world;


import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class MyTest {
    @Test
    public void testZero(){
        Tank tank = new Tank();
        int health = tank.getHealth();
        assertEquals(0,health);
    }
}
