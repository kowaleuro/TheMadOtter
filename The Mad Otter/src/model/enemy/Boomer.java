package model.enemy;

import javafx.scene.layout.Pane;

public class Boomer extends Enemy{

    public Boomer(double x, double y, Pane layer) {
        super(x, y, "/graphics/enemies/boomer.gif", "/graphics/enemies/boomer.gif", "/graphics/enemies/boomer.gif", "/graphics/enemies/boomer.gif", layer);
        setFollowing(true);
        setFlying(false);
        setShooting(false);
        setExplosive(true);
        setFollowingVel(3);
        setRemainingHealth(15);
        setDmg(2);
    }

}