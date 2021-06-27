package model.item.guns;

import javafx.scene.layout.Pane;

public class Deagle extends Gun {

    public Deagle(double x, double y, Pane layer) {
        super(x, y, "graphics/items/guns/deagle.png", "graphics/items/bullets/deagleBullet.png", layer);
        setBulletVel(14);
        setCooldownShot(750);
        setDmg(25);
        setPriceStandard(25);
        setPriceHealth(9);
        setGunName("Deagle");
    }

}