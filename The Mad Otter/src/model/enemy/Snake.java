package model.enemy;

import javafx.scene.layout.Pane;

public class Snake extends Enemy{

    public Snake(double x, double y, Pane layer) {
        super(x, y, "/graphics/enemies/snake.gif", "/graphics/enemies/snake.gif", null, null, layer);
        setFollowing(true);
        setFlying(false);
        setShooting(false);
        setExplosive(false);
        setFollowingVel(2);
        setRemainingHealth(20);
        setDmg(1);
    }

}
