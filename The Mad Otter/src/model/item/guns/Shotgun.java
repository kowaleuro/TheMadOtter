package model.item.guns;

import javafx.scene.layout.Pane;

public class Shotgun extends Gun {

    public Shotgun(double x, double y, Pane layer) {
        super(x, y, "graphics/items/guns/shotgun.png", "graphics/items/bullets/shotgunBullet.png", layer);
        setBulletVel(14);
        setCooldownShot(1000);
        setDmg(3);
        setPriceStandard(8);
        setPriceHealth(4);
        setGunName("Shotgun");
    }

}