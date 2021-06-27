package model.item;

import javafx.scene.layout.Pane;
import model.hero.Hero;

public class Coin extends Item {

    public Coin(double x, double y, Pane layer) {
        super(x, y, "graphics/items/coin.gif", layer);
    }

    @Override
    public boolean onTouch(Hero hero) {
        hero.setMoney(hero.getMoney()+1);
        return true;
    }
}
