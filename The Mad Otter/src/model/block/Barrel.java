package model.block;

import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

public class Barrel extends Block{
    private int hp;

    public Barrel(double x, double y, Pane layer) {
        super(x, y,"graphics/blocks/barrel1.png" , layer);
        hp = 3;
        setBreakable(true);
        setPrickly(false);
        setToPass(false);
    }

    @Override
    public void changeImage(){
        if (hp == 2) {
            getImageView().setImage(new Image("graphics/blocks/barrel2.png"));
        }
        if (hp == 1) {
            getImageView().setImage(new Image("graphics/blocks/barrel3.png"));
        }
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