package model.enemy;

import javafx.animation.AnimationTimer;
import javafx.scene.layout.Pane;
import model.Bullet;
import model.MovingObjects;
import model.hero.Hero;

public abstract class Enemy extends MovingObjects {

    private int points = 200;
    private int remainingHealth; // MUST HAVE
    private int dmg; // MUST HAVE
    private boolean following; // MUST HAVE
    private boolean flying; // MUST HAVE
    private boolean shooting; // MUST HAVE
    private boolean explosive; // MUST HAVE
    private boolean isPoisoned;
    private boolean explosionCollision;

    private int followingVel = 0; // tylko dla przeciwników podążających za graczem - nie więcej niż 4!
    private String bulletPath = null; // tylko dla przeciwników strzelających, ścieżka do grafiki pocisku
    private int bulletVelFactor = 0; // prędkość pocisków przeciwników strzelających
    private int cooldownShot = 2000; // szybkostrzelność przeciwników strzelających (domyślnie 2000, żeby nie było syfu jak się zapomni ustawić)

    private double bulletVelX = 0; // atrybut pomocniczy, nigdzie nie ustawiać
    private double bulletVelY = 0; // atrybut pomocniczy, nigdzie nie ustawiać
    private long lastEnemyShot = 0; // atrybut pomocniczy, nigdzie nie ustawiać

    public Enemy(double x, double y, String pathStatic, String pathMoving, String pathStaticShot, String pathMovingShot, Pane layer) {
        super(x, y, pathStatic, pathMoving, pathStaticShot, pathMovingShot, layer);
        this.removeFromLayer();
        setPoisoned(false);
    }

    public void shot(Hero hero, int velFactor) {
        long time = System.currentTimeMillis();
        if (this.shooting) {
            if (time > lastEnemyShot + cooldownShot) {
                lastEnemyShot = time;
                double vecLength;
                vecLength = Math.hypot(hero.getX() + 16 - getX(), hero.getY() + 26 - getY());
                bulletVelX = velFactor * (hero.getX() + 16 - getX()) / vecLength;
                bulletVelY = velFactor * (hero.getY() + 26 - getY()) / vecLength;
                hero.getActualRoom().getEnemyBullets().add(new Bullet(getX() + getDimension().getWidth() / 2, getY() + getDimension().getHeight() / 2, bulletVelX, bulletVelY, dmg, bulletPath, bulletPath, getLayer()));
            }
        }
    }

    public void poisonDamage(int damage) {
        AnimationTimer animationTimer = new AnimationTimer() {
            long lastUpdate = 0;
            int calc = 0;
            @Override
            public void handle(long l) {
                if ((l - lastUpdate) >= 500_000_000) {
                    if (getImageView().getOpacity() == 1) {
                        getImageView().setOpacity(0.3);
                    }
                    else if (getImageView().getOpacity() == 0.3) {
                        getImageView().setOpacity(1);
                    }
                    setRemainingHealth(Math.max(getRemainingHealth() - damage, 0));
                    calc++;
                    lastUpdate = l;
                    System.out.println(getRemainingHealth());
                    if (getRemainingHealth() == 0){
                        this.stop();
                    }
                }
                if (calc == 10) {
                    this.stop();
                }
            }

            @Override
            public void stop() {
                getImageView().setOpacity(1);
                super.stop();
                setPoisoned(false);
            }
        };
        animationTimer.start();
        setPoisoned(true);
    }

    public void specificBehaviour() {} // funkcja do przeciążeń (np Enemy5, Enemy6)

    public int getRemainingHealth() {
        return remainingHealth;
    }
    public void setRemainingHealth(int remainingHealth) {
        this.remainingHealth = remainingHealth;
    }

    public boolean isFollowing() {
        return following;
    }
    public void setFollowing(boolean following) {
        this.following = following;
    }

    public boolean isFlying() {
        return flying;
    }
    public void setFlying(boolean flying) {
        this.flying = flying;
    }

    public int getFollowingVel() {
        return followingVel;
    }
    public void setFollowingVel(int followingVel) {
        this.followingVel = followingVel;
    }

    public String getBulletPath() {
        return bulletPath;
    }
    public void setBulletPath(String bulletPath) {
        this.bulletPath = bulletPath;
    }

    public double getBulletVelX() {
        return bulletVelX;
    }
    public void setBulletVelX(double bulletVelX) {
        this.bulletVelX = bulletVelX;
    }

    public double getBulletVelY() {
        return bulletVelY;
    }
    public void setBulletVelY(double bulletVelY) {
        this.bulletVelY = bulletVelY;
    }

    public int getDmg() {
        return dmg;
    }
    public void setDmg(int dmg) {
        this.dmg = dmg;
    }

    public boolean isShooting() {
        return shooting;
    }
    public void setShooting(boolean shooting) {
        this.shooting = shooting;
    }

    public int getBulletVelFactor() {
        return bulletVelFactor;
    }
    public void setBulletVelFactor(int bulletVelFactor) {
        this.bulletVelFactor = bulletVelFactor;
    }

    public boolean isExplosive() {
        return explosive;
    }
    public void setExplosive(boolean explosive) {
        this.explosive = explosive;
    }

    public int getCooldownShot() {
        return cooldownShot;
    }
    public void setCooldownShot(int cooldownShot) {
        this.cooldownShot = cooldownShot;
    }

    public int getPoints() {
        return points;
    }
    public void setPoints(int points) {
        this.points = points;
    }

    public boolean isPoisoned() {
        return isPoisoned;
    }
    public void setPoisoned(boolean poisoned) {
        isPoisoned = poisoned;
    }

    public boolean isExplosionCollision() {
        return explosionCollision;
    }
    public void setExplosionCollision(boolean explosionCollision) {
        this.explosionCollision = explosionCollision;
    }
}
