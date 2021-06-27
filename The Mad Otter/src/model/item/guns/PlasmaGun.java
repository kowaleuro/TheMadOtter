package model.item.guns;

import javafx.scene.layout.Pane;

public class PlasmaGun extends Gun {

    public PlasmaGun(double x, double y, Pane layer) {
        super(x, y, "graphics/items/guns/plasmaGun.gif", "graphics/items/bullets/plasmaBullet.gif", layer);
        setBulletVel(15);
        setCooldownShot(300);
        setDmg(25);
        setPriceStandard(50);
        setPriceHealth(16);
        setGunName("Plasma Gun");
    }

}