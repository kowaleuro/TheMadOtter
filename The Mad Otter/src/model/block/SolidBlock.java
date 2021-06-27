package model.block;

import javafx.animation.AnimationTimer;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import map.Room;

public class SolidBlock extends Block{

    private final Pane layer;

    public SolidBlock(double x, double y, Pane layer) {
        super(x, y, "graphics/blocks/solidBlock.png", layer);
        setBreakable(false);
        setPrickly(false);
        setToPass(false);
        this.layer = layer;
    }

    public void destroyAnimation(Room room) {
        ImageView destroyed = new ImageView(new Image("graphics/blocks/solidBlockAnimation.gif"));
        destroyed.relocate(this.getX(), this.getY());
        layer.getChildren().add(destroyed);
        room.getAnimations().add(destroyed);
        destroyed.toBack();

        long time = System.nanoTime();
        AnimationTimer animationTimer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                if (l - 800_000_000 > time) {
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
}
