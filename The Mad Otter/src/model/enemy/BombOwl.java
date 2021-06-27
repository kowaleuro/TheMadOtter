package model.enemy;

import javafx.scene.layout.Pane;

public class BombOwl extends Enemy{

    public BombOwl(double x, double y, Pane layer) {
        super(x, y, "graphics/enemies/bombOwl.gif", "graphics/enemies/bombOwl.gif", "graphics/enemies/bombOwl.gif", "graphics/enemies/bombOwl.gif", layer);
        setFollowing(true);
        setFlying(true);
        setShooting(true);
        setExplosive(true);
        setFollowingVel(1);
        setBulletVelFactor(10);
        setCooldownShot(2000);
        setRemainingHealth(15);
        setBulletPath("graphics/items/bullets/enemyBullets/bombOwlBullet.png");
        setDmg(1);
    }

}