package model.item.guns;

import javafx.scene.layout.Pane;
import model.hero.Hero;
import model.item.Item;

public abstract class Gun extends Item {

    private int priceStandard; // cena w $
    private int priceHealth; // cena w hp
    private boolean buyStandard; // można kupić za $
    private boolean buyHealth; // można kupić za hp

    private int bulletVel; // prędkość pocisku
    private long cooldownShot; // cooldown między strzałami
    private int dmg; // dmg per shot
    private String gunName; // nazwa broni
    private String pathBullet; // ścieżka do grafiki pocisku

    public Gun(double x, double y, String pathStatic, String pathBullet, Pane layer) {
        super(x, y, pathStatic, layer);
        this.pathBullet = pathBullet;
        setPoints(200);
    }

    @Override
    public boolean onTouch(Hero hero) {
        if (!isOwnedGun(hero)) {
            if (buyStandard && hero.getMoney() >= priceStandard) {
                hero.setMoney(hero.getMoney() - priceStandard);
                hero.addNewGun(this);
                return true;
            } else if (buyHealth && hero.getRemainingHealth() >= priceHealth) {
                hero.healthDown(priceHealth, "NotTheKiller");
                hero.addNewGun(this);
                return true;
            } else if (!buyStandard && !buyHealth) {
                hero.addNewGun(this);
                return true;
            }
        }
        return false;
    }

    private boolean isOwnedGun(Hero hero) {
        for (Gun gun : hero.getEquipment()) {
            if (gun.getClass().equals(this.getClass())) {
                return true;
            }
        }
        return false;
    }

    public String getPrice() {
        if (buyStandard) {
            return (priceStandard + " $");
        } else if (buyHealth) {
            return (priceHealth + " HP");
        } else {
            return "";
        }
    }

    public int getBulletVel() {
        return bulletVel;
    }
    public void setBulletVel(int bulletVel) {
        this.bulletVel = bulletVel;
    }

    public long getCooldownShot() {
        return cooldownShot;
    }
    public void setCooldownShot(long cooldownShot) {
        this.cooldownShot = cooldownShot;
    }

    public int getDmg() {
        return dmg;
    }
    public void setDmg(int dmg) {
        this.dmg = dmg;
    }

    public String getGunName() {
        return gunName;
    }
    public void setGunName(String gunName) {
        this.gunName = gunName;
    }

    public String getPathBullet() {
        return pathBullet;
    }
    public void setPathBullet(String pathBullet) {
        this.pathBullet = pathBullet;
    }

    public int getPriceStandard() {
        return priceStandard;
    }
    public void setPriceStandard(int priceStandard) {
        this.priceStandard = priceStandard;
    }

    public int getPriceHealth() {
        return priceHealth;
    }
    public void setPriceHealth(int priceHealth) {
        this.priceHealth = priceHealth;
    }

    public boolean isBuyStandard() {
        return buyStandard;
    }
    public void setBuyStandard(boolean buyStandard) {
        this.buyStandard = buyStandard;
    }

    public boolean isBuyHealth() {
        return buyHealth;
    }
    public void setBuyHealth(boolean buyHealth) {
        this.buyHealth = buyHealth;
    }
}
