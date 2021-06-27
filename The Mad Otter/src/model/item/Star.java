package model.item;

import javafx.scene.layout.Pane;
import model.hero.Hero;

public class Star extends Item {

    public Star(double x, double y, Pane layer) {
        super(x, y, "graphics/items/star.gif", layer);
        setPoints(0);
    }

    @Override
    public boolean onTouch(Hero hero) {
        hero.setPoints(hero.getPoints() + 1000);
        return true;
    }
}
