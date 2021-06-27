package model.item;

import javafx.animation.AnimationTimer;
import javafx.scene.layout.Pane;
import model.hero.Hero;

// Invisibility - 15 s
public class GreenSphere extends Item {

    public GreenSphere(double x, double y, Pane layer) {
        super(x, y, "graphics/items/greenSphere.gif", layer);
        setPoints(500);
    }

    @Override
    public boolean onTouch(Hero hero) {
        if (hero.getImageView().getOpacity() != 0.49) {
            AnimationTimer animationTimer = new AnimationTimer() {
                private long time = System.nanoTime();
                private long cooldown = 15_000; // czas trwania: 15s
                private long handleL;
                private boolean rewriteDone = false;
                @Override
                public void stop() {
                    if (!hero.isPaused()) {
                        if (hero.isInBush()) {
                            hero.setHiding(true);
                            hero.getImageView().setOpacity(0.5);
                        } else {
                            hero.setHiding(false);
                            hero.getImageView().setOpacity(1);
                        }
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
                    if (!hero.isHiding()) {
                        time = System.nanoTime();
                        hero.setHiding(true);
                        hero.getImageView().setOpacity(0.49);
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
