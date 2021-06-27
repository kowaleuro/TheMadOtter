package model.enemy;

import javafx.scene.layout.Pane;

import java.util.Random;

public class DevilFly extends Enemy {

    private Random random = new Random();
    private long lastChange = 0;
    private int[] randomVel = new int[] {-4, 0, 4};

    public DevilFly(double x, double y, Pane layer) {
        super(x, y, "graphics/enemies/devilFly.gif", "graphics/enemies/devilFly.gif", null, null, layer);
        setFollowing(false);
        setFlying(true);
        setShooting(true);
        setExplosive(false);
        setBulletPath("graphics/items/bullets/enemyBullets/devilFlyBullet.gif");
        setBulletVelFactor(8);
        setCooldownShot(1500);
        setVelX(randomVel[random.nextInt(3)]);
        setVelY(randomVel[random.nextInt(3)]);
        setRemainingHealth(30);
        setDmg(1);
    }

    @Override
    public void specificBehaviour() {
        long time = System.currentTimeMillis();
        if (time > lastChange + random.nextInt(1500)+500) {
            lastChange = time;
            setVelX(randomVel[random.nextInt(3)]);
            setVelY(randomVel[random.nextInt(3)]);
        }
    }
}
