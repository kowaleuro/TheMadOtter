package model.enemy;

import javafx.scene.layout.Pane;
import model.Bullet;
import model.hero.Hero;

import java.util.ArrayList;
import java.util.Random;

public class Diglet extends Enemy{

    private long lastEnemyShot = 0;
    private long lastChange = 0;
    private final Random random = new Random();
    private boolean up = false;
    private final double[] bulletVelTabX = new double[] {5, 2.5, 0, -2.5, -5, -2.5, 0, 2.5};
    private final double[] bulletVelTabY = new double[] {0, -2.5, -5, -2.5, 0, 2.5, 5, 2.5};
    private final ArrayList<int[]> spotLoc;

    public Diglet(double x, double y, Pane layer, ArrayList<int[]> spotLoc) {
        super(x, y, "graphics/enemies/digletMoving.gif", "graphics/enemies/digletMoving.gif", "/graphics/enemies/diglet.png", "/graphics/enemies/diglet.png", layer);
        this.spotLoc = spotLoc;
        setFollowing(false);
        setFlying(false);
        setShooting(true);
        setExplosive(false);
        setBulletVelFactor(6);
        setCooldownShot(300);
        setRemainingHealth(20);
        setBulletPath("graphics/items/bullets/enemyBullets/digletBall.png");
        setDmg(1);
    }

    @Override
    public void specificBehaviour() {
        long time = System.currentTimeMillis();
        if (time > lastChange + random.nextInt(3000)+2000) {
            lastChange = time;
            int[] loc = spotLoc.get(random.nextInt(spotLoc.size()));
            setLocation(loc[0], loc[1]);
            up = true;
        }
    }

    @Override
    public void shot(Hero hero, int velFactor) {
        long time = System.currentTimeMillis();
        if(up) {
            if (time > lastEnemyShot + getCooldownShot()) {
                lastEnemyShot = time;
                for (int j = 0; j < bulletVelTabX.length; j++) {
                    setBulletVelX(bulletVelTabX[j]);
                    setBulletVelY(bulletVelTabY[j]);
                    hero.getActualRoom().getEnemyBullets().add(new Bullet(getX() + getDimension().getWidth() / 2, getY() + getDimension().getHeight() / 2, getBulletVelX(), getBulletVelY(), getDmg(), getBulletPath(), getBulletPath(), getLayer()));
                }
                up = false;
            }
        }
    }
}