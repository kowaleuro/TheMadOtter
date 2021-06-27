package model.item;

import javafx.scene.layout.Pane;
import model.hero.Hero;

public class Fish extends Item{


    public Fish(double x, double y, Pane layer) {
        super(x, y, "graphics/items/fish.gif", layer);
        setPoints(100);
    }

    @Override
    public boolean onTouch(Hero hero) {
        boolean picked = false;
        if (hero.getRemainingHealth() <= 19) {
            if (hero.getRemainingHealth() == 19){
                hero.setRemainingHealth(hero.getRemainingHealth() + 1);
            } else {
                hero.setRemainingHealth(hero.getRemainingHealth() + 2);
            }
            picked = true;
        }
        return picked;
    }

}
