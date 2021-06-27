package model.item.guns;

import javafx.scene.layout.Pane;

public class RocketLauncher extends Gun {

    public RocketLauncher(double x, double y, Pane layer) {
        super(x, y, "graphics/items/guns/rocketLauncher.png", "graphics/items/bullets/rocketBullet.gif", layer);
        setBulletVel(12);
        setCooldownShot(2500);
        setDmg(60);
        setPriceStandard(28);
        setPriceHealth(10);
        setGunName("Rocket Launcher");
    }


}