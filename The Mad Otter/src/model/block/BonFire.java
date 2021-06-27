package model.block;

import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

public class BonFire extends Block {

    private int hp = 3;
    private boolean fireOut;

    public BonFire(double x, double y, Pane layer) {
        super(x, y,"graphics/blocks/bonfire1.gif" , layer);
        setBreakable(true);
        setPrickly(true);
        setToPass(true);
        setFireOut(false);
        setDmg(4);
        setHp(3);
    }
    @Override
    public void changeImage(){
        if (getHp() == 2) {
            getImageView().setImage(new Image("graphics/blocks/bonfire2.gif"));
            setDmg(3);
        }
        if (getHp() == 1) {
            getImageView().setImage(new Image("graphics/blocks/bonfire3.gif"));
            setDmg(2);
        }
    }

    public void putOutFire() {
        setDmg(1);
        setFireOut(true);
        getImageView().setImage(new Image("graphics/blocks/bonfireOut.gif"));
    }

    @Override
    public int getHp() {
        return hp;
    }
    @Override
    public void setHp(int hp) {
        this.hp = hp;
    }

    public boolean isFireOut() {
        return fireOut;
    }
    public void setFireOut(boolean fireOut) {
        this.fireOut = fireOut;
    }
}
