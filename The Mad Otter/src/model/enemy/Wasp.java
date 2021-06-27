package model.enemy;

import javafx.scene.layout.Pane;

public class Wasp extends Enemy{

    public Wasp(double x, double y, Pane layer) {
        super(x, y, "/graphics/enemies/wasp.gif", "/graphics/enemies/wasp.gif", null, null, layer);
        setFollowing(true);
        setFlying(true);
        setShooting(false);
        setExplosive(false);
        setFollowingVel(3);
        setRemainingHealth(30);
        setDmg(2);
    }

}
