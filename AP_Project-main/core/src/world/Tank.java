package world;
import java.util.*;
//games class for no reason
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Tank extends Rectangle {

    public SpriteBatch batch;
    public BitmapFont font;
    private int health=200;
    private int level;
    private int fuel;
    private Map<String, Bullet> attackList = new HashMap<>();

    public Tank(int health, int level){
        this.health = health;
        this.level = level;
    }

    public Tank() {

    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int power) {
        this.health-=power;
    }
}
