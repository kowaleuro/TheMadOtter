package model.block;

import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

public class Trapdoor extends Block{

    private boolean open = false;
    private Image openedTrapdoorImage;

    public Trapdoor(double x, double y, Pane layer) {
        super(x, y, "graphics/map/floor1/trapDoorClose1.png", layer);
        setBreakable(false);
        setPrickly(false);
        setToPass(true);
        getImageView().toBack();
        openedTrapdoorImage = new Image("graphics/map/floor1/trapDoorOpen1.png");
    }


    public void open() {
        open = true;
        getImageView().setImage(openedTrapdoorImage);
    }

    public boolean isOpen() {
        return open;
    }
    public void setOpen(boolean open) {
        this.open = open;
    }

    public Image getOpenedTrapdoorImage() {
        return openedTrapdoorImage;
    }
    public void setOpenedTrapdoorImage(Image openedTrapdoorImage) {
        this.openedTrapdoorImage = openedTrapdoorImage;
    }
}
