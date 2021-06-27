package model.item;

import javafx.scene.layout.Pane;
import model.hero.Hero;

public class BombPack extends Item {

    public BombPack(double x, double y, Pane layer) {
        super(x, y, "graphics/items/bombPackSafe.gif", layer);
        setPoints(100);
    }

    @Override
    public boolean onTouch(Hero hero) {
        hero.setBombs(hero.getBombs() + 3);
        return true;
    }
}
