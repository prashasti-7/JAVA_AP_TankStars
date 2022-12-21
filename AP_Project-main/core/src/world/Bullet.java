package world;

import com.badlogic.gdx.math.Rectangle;

import java.util.ArrayList;

public class Bullet extends Rectangle {
    private int resistance;
    private int power=5;
    public Bullet(){
        super();
    }

    public int getPower() {
        return power;
    }

    public int getResistance() {
        return resistance;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public void setResistance(int resistance) {
        this.resistance = resistance;
    }

}
