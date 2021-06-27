package model.item.guns;

import javafx.scene.layout.Pane;

public class M16 extends Gun {

    public M16(double x, double y, Pane layer) {
        super(x, y, "graphics/items/guns/m16.png", "graphics/items/bullets/m16Bullet.png", layer);
        setBulletVel(16);
        setCooldownShot(700);
        setDmg(8);
        setPriceStandard(30);
        setPriceHealth(12);
        setGunName("M16");
    }

}