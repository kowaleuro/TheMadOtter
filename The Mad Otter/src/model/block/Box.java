package model.block;

import javafx.animation.AnimationTimer;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import map.Room;

public class Box extends Block {

    private int hp = 1;
    private final Pane layer;

    public Box (double x, double y, Pane layer) {
        super(x, y, "graphics/blocks/box.png", layer);
        setBreakable(true);
        setPrickly(false);
        setToPass(false);
        this.layer = layer;
    }

    public void destroyAnimation(Room room) {
        ImageView destroyed = new ImageView(new Image("graphics/blocks/boxAnimation.gif"));
        destroyed.relocate(this.getX(), this.getY());
        layer.getChildren().add(destroyed);
        room.getAnimations().add(destroyed);
        destroyed.toBack();

        long time = System.nanoTime();
        AnimationTimer animationTimer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                if (l - 720_000_000 > time) {
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

    @Override
    public int getHp() {
        return hp;
    }
    @Override
    public void setHp(int hp) {
        this.hp = hp;
    }
}