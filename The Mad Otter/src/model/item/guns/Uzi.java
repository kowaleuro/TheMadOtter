package model.item.guns;

import javafx.scene.layout.Pane;

public class Uzi extends Gun {

    public Uzi(double x, double y, Pane layer) {
        super(x, y, "graphics/items/guns/uzi.png", "graphics/items/bullets/uziBullet.png", layer);
        setBulletVel(10);
        setCooldownShot(100);
        setDmg(5);
        setPriceStandard(15);
        setPriceHealth(6);
        setGunName("Uzi");
    }

}