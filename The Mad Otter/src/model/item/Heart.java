package model.item;

import javafx.scene.layout.Pane;
import model.hero.Hero;

public class Heart extends Item {

    public Heart(double x, double y, Pane layer) {
        super(x, y, "graphics/items/magicApple.gif", layer);
        setPoints(500);
    }

    @Override
    public boolean onTouch(Hero hero) {
        if (hero.getRemainingHealth() != 20) {
            hero.setRemainingHealth(20);
            return true;
        } else {
            return false;
        }
    }
}
