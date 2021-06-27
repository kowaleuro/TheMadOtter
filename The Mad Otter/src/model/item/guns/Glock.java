package model.item.guns;

import javafx.scene.layout.Pane;

public class Glock extends Gun {

    public Glock(double x, double y, Pane layer) {
        super(x, y, "graphics/items/guns/glock.png", "graphics/items/bullets/glockBullet.png", layer);
        setBulletVel(8);
        setCooldownShot(125);
        setDmg(4);
        setPriceStandard(13);
        setPriceHealth(5);
        setGunName("Glock");
    }

}