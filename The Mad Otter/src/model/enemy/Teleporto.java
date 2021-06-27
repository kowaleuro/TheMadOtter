package model.enemy;

import javafx.scene.layout.Pane;
import model.Bullet;
import model.hero.Hero;

import java.util.ArrayList;
import java.util.Random;

public class Teleporto extends Enemy{

    private long lastEnemyShot = 0;
    private long lastChange = 0;
    private final Random random = new Random();
    private boolean up = false;
    private final ArrayList<int[]> spotLoc;

    public Teleporto(double x, double y, Pane layer, ArrayList<int[]> spotLoc) {
        super(x, y, "graphics/enemies/teleporto.gif", "graphics/enemies/teleporto.gif", "/graphics/enemies/teleporto.gif", "/graphics/enemies/teleporto.gif", layer);
        this.spotLoc = spotLoc;
        setFollowing(false);
        setFlying(false);
        setShooting(true);
        setExplosive(false);
        setBulletVelFactor(8);
        setCooldownShot(100);
        setRemainingHealth(20);
        setBulletPath("graphics/items/bullets/enemyBullets/teleportoBullet.png");
        setDmg(1);
    }

    @Override
    public void specificBehaviour() {
        long time = System.currentTimeMillis();
        if (time > lastChange + random.nextInt(3000) + 2000) {
            lastChange = time;
            int[] loc = spotLoc.get(random.nextInt(spotLoc.size()));
            setLocation(loc[0], loc[1]);
            up = true;
        }
    }

    @Override
    public void shot(Hero hero, int velFactor) {
        long time = System.currentTimeMillis();
        if (up) {
            if (time > lastEnemyShot + getCooldownShot()) {
                lastEnemyShot = time;
                double vecLength;
                vecLength = Math.hypot(hero.getX() - getX(), hero.getY() - getY());
                setBulletVelX(velFactor * (hero.getX() - getX()) / vecLength);
                setBulletVelY(velFactor * (hero.getY() - getY()) / vecLength);
                hero.getActualRoom().getEnemyBullets().add(new Bullet(getX() + getDimension().getWidth() / 2, getY() + getDimension().getHeight() / 2, getBulletVelX(), getBulletVelY(), getDmg(), getBulletPath(), getBulletPath(), getLayer()));
            }
            up = false;
        }
    }

}