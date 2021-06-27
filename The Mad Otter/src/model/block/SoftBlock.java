package model.block;

import javafx.animation.AnimationTimer;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import map.Room;

public class SoftBlock extends Block {

    private int hp;
    private Pane layer;

    public SoftBlock(double x, double y, Pane layer) {
        super(x, y,"graphics/blocks/softBlock1.png" , layer);
        hp = 3;
        setBreakable(true);
        setPrickly(false);
        setToPass(false);
        this.layer = layer;
    }

    @Override
    public void changeImage(){
        if (hp == 2) {
            getImageView().setImage(new Image("graphics/blocks/softBlock2.png"));
        }
        if (hp == 1) {
            getImageView().setImage(new Image("graphics/blocks/softBlock3.png"));
        }
    }

    public void destroyAnimation(Room room) {
        ImageView destroyed = new ImageView(new Image("graphics/blocks/softBlockAnimation.gif"));
        destroyed.relocate(this.getX(), this.getY());
        layer.getChildren().add(destroyed);
        room.getAnimations().add(destroyed);
        destroyed.toBack();

        long time = System.nanoTime();
        AnimationTimer animationTimer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                if (l - 640_000_000 > time) {
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
