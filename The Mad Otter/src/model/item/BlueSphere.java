package model.item;

import javafx.animation.AnimationTimer;
import javafx.scene.effect.Glow;
import javafx.scene.layout.Pane;
import model.hero.Hero;

// GodMode - 10 s
public class BlueSphere extends Item {

    private Glow godGlow = new Glow(0.5);
    private Glow noGlow = new Glow(0);

    public BlueSphere(double x, double y, Pane layer) {
        super(x, y, "graphics/items/blueSphere.gif", layer);
        setPoints(500);
    }

    @Override
    public boolean onTouch(Hero hero) {
        if (!hero.isGodmode()) {
            AnimationTimer animationTimer = new AnimationTimer() {
                private long time = System.nanoTime();
                private long cooldown = 10_000; // czas trwania: 10s
                private long handleL;
                private boolean rewriteDone = false;
                @Override
                public void stop() {
                    if (!hero.isPaused()) {
                        hero.setGodmode(false);
                        hero.getImageView().setEffect(noGlow);
                        hero.getPowerUpTimers().remove(this);
                        super.stop();
                    } else if (hero.isPaused() && !rewriteDone) {
                        cooldown = cooldown - (handleL /1_000_000 - time/1_000_000);
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
                    if (!hero.isGodmode()) {
                        time = System.nanoTime();
                        hero.setGodmode(true);
                        hero.getImageView().setEffect(godGlow);
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
