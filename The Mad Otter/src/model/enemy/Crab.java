package model.enemy;

import javafx.scene.layout.Pane;

import java.util.Random;

public class Crab extends Enemy{

    public Crab(double x, double y, Pane layer) {
        super(x, y, "graphics/enemies/crab.gif", "graphics/enemies/crab.gif", null, null, layer);
        setFollowing(false);
        setFlying(false);
        setShooting(false);
        setExplosive(false);
        randomVel();
        setRemainingHealth(30);
        setDmg(1);
    }

    private void randomVel() {
        Random random = new Random();
        if(random.nextBoolean()) {
            setVelX(6);
            setVelY(0);
        } else {
            setVelX(0);
            setVelY(6);
        }
    }

}
