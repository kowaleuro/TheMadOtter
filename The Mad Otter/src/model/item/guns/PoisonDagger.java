package model.item.guns;

import javafx.scene.layout.Pane;

public class PoisonDagger extends Gun{

    public PoisonDagger(double x, double y, Pane layer) {
        super(x, y, "graphics/items/guns/poisonDagger.png", "graphics/items/bullets/daggerBullets.png", layer);
        setBulletVel(10);
        setCooldownShot(3000);
        setDmg(5);
        setPriceStandard(20);
        setPriceHealth(6);
        setGunName("Poisoned Daggers");
    }


}
