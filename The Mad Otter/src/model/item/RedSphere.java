package model.item;

import javafx.animation.AnimationTimer;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.layout.Pane;
import model.hero.Hero;

// Double damage - 15 s
public class RedSphere extends Item {

    private ColorAdjust powerUpHue = new ColorAdjust();
    private ColorAdjust normalHue = new ColorAdjust();

    public RedSphere(double x, double y, Pane layer) {
        super(x, y, "graphics/items/redSphere.gif", layer);
        setPoints(500);
        powerUpHue.setHue(-0.1);
        normalHue.setHue(0);
    }

    @Override
    public boolean onTouch(Hero hero) {
        if (hero.getDmgFactor() == 1) {
            AnimationTimer animationTimer = new AnimationTimer() {
                private long time = System.nanoTime();
                private long cooldown = 15_000; // czas trwania: 15s
                private long handleL;
                private boolean rewriteDone = false;
                @Override
                public void stop() {
                    if (!hero.isPaused()) {
                        hero.setDmgFactor(1);
                        hero.getImageView().setEffect(normalHue);
                        hero.getPowerUpTimers().remove(this);
                        super.stop();
                    } else if (hero.isPaused() && !rewriteDone) {
                        cooldown = cooldown - (handleL/1_000_000 - time/1_000_000);
                        rewriteDone = true;
                    }
                }

                @Override
                public void start() {
                    rewriteDone = false;
                    time = System.nanoTime();
                    super.start();
                }

                @Override
                public void handle(long l) {
                    if (hero.getDmgFactor() == 1) {
                        this.time = System.nanoTime();
                        hero.getImageView().setEffect(powerUpHue);
                        hero.setDmgFactor(2);
                        hero.getPowerUpTimers().add(this);
                    }
                    this.handleL = l;
                    if (l/1_000_000 - cooldown > time/1_000_000) {
                        this.stop();
                    }
                }

            };
            animationTimer.start();
            return true;
        } else {
            return false;
        }
    }
}
