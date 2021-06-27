package model.item.guns;

import javafx.scene.layout.Pane;

public class LaserGun extends Gun {

    public LaserGun(double x, double y, Pane layer) {
        super(x, y, "graphics/items/guns/laserGun.gif", "graphics/items/bullets/laserBullet.gif", layer);
        setBulletVel(18);
        setCooldownShot(300);
        setDmg(30);
        setPriceStandard(60);
        setPriceHealth(18);
        setGunName("Laser Gun");
    }

}