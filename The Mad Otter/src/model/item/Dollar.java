package model.item;

import javafx.scene.layout.Pane;
import model.hero.Hero;

public class Dollar extends Item {

    public Dollar(double x, double y, Pane layer) {
        super(x, y, "graphics/items/dolar.gif", layer);
        setPoints(100);
    }

    @Override
    public boolean onTouch(Hero hero) {
        hero.setMoney(hero.getMoney()+5);
        return true;
    }
}
