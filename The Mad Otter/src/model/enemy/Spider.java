package model.enemy;

import javafx.scene.layout.Pane;

import java.util.Random;

public class Spider extends Enemy{

    private Random random = new Random();

    public Spider(double x, double y, Pane layer) {
        super(x, y, "/graphics/enemies/spider.gif", "/graphics/enemies/spider.gif", "/graphics/enemies/spider.gif", "/graphics/enemies/spider.gif", layer);
        setFollowing(true);
        setFlying(false);
        setShooting(true);
        setExplosive(false);
        setFollowingVel(3);
        setBulletVelFactor(6);
        setCooldownShot(random.nextInt(1000) + 1000);
        setRemainingHealth(20);
        setBulletPath("graphics/items/bullets/enemyBullets/spiderBullet.png");
        setDmg(1);
    }

    @Override
    public void specificBehaviour() {
        setCooldownShot(random.nextInt(2000) + 500);
    }

}