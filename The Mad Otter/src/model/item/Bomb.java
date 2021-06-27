package model.item;

import javafx.scene.layout.Pane;
import model.hero.Hero;

public class Bomb extends Item {

    public Bomb(double x, double y, Pane layer) {
        super(x, y, "graphics/items/bombSafe.gif", layer);
    }

    @Override
    public boolean onTouch(Hero hero) {
        hero.setBombs(hero.getBombs() + 1);
        return true;
    }
}
