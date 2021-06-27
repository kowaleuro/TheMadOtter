package model.block;

import javafx.animation.AnimationTimer;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import map.Room;


public class BushBlock extends Block{

    private boolean ifInBush;
    private final Pane layer;

    public BushBlock(double x, double y, Pane layer) {
        super(x, y, "graphics/blocks/bush.png", layer);
        setBreakable(false);
        setPrickly(false);
        setToPass(true);
        setIfInBush(false);
        this.layer = layer;
    }

    public void destroyAnimation(Room room) {
        ImageView destroyed = new ImageView(new Image("graphics/blocks/bushAnimation.gif"));
        destroyed.relocate(this.getX(), this.getY());
        layer.getChildren().add(destroyed);
        room.getAnimations().add(destroyed);
        destroyed.toBack();

        long time = System.nanoTime();
        AnimationTimer animationTimer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                if (l - 500_000_000 > time) {
                    this.stop();
                }
            }
            @Override
            public void stop() {
                layer.getChildren().remove(destroyed);
                super.stop();
            }
        };
        animationTimer.start();
    }


    public boolean isIfInBush() {
        return ifInBush;
    }
    public void setIfInBush(boolean ifInBush) {
        this.ifInBush = ifInBush;
    }
}