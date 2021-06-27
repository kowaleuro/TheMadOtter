package model.enemy;

import javafx.scene.layout.Pane;

import java.util.Random;

public class Bat extends Enemy{

    private Random random = new Random();
    private long lastChange = 0;
    private int[] randomVel = new int[] {-5, 0, 5};

    public Bat(double x, double y, Pane layer) {
        super(x, y, "/graphics/enemies/bat.gif", "/graphics/enemies/bat.gif", null, null, layer);
        setFollowing(false);
        setFlying(true);
        setShooting(false);
        setExplosive(false);
        setVelX(randomVel[random.nextInt(3)]);
        setVelY(randomVel[random.nextInt(3)]);
        setRemainingHealth(30);
        setDmg(1);
    }

    @Override
    public void specificBehaviour() {
        long time = System.currentTimeMillis();
        if (time > lastChange + random.nextInt(1000)+500) {
            lastChange = time;
            setVelX(randomVel[random.nextInt(3)]);
            setVelY(randomVel[random.nextInt(3)]);
        }
    }
}
