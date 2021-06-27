package map;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import model.StaticObjects;

public class Door extends StaticObjects {

    private int doorId;
    private boolean closedDoors;
    private Rectangle doorBounds;

    public Door(double x, double y, String pathStatic, Pane layer, int doorId) {
        super(x, y, pathStatic, layer);
        this.closedDoors = false;
        this.doorId = doorId;
    }


    public void setDoorBounds(String direction) {
        switch (direction) {
            case "left" -> doorBounds = new Rectangle(getX(), getY(), 22, 80);
            case "right" -> doorBounds = new Rectangle(getX() + 10, getY(), 22, 80);
            case "up" -> doorBounds = new Rectangle(getX(), getY(), 80, 16);
            case "down" -> doorBounds = new Rectangle(getX(), getY(), 80, 32);
        }
    }

    public Rectangle getDoorBounds() {
        return doorBounds;
    }

    public int getDoorId() {
        return doorId;
    }
    public void setDoorId(int doorId) {
        this.doorId = doorId;
    }

    public boolean isClosedDoors() {
        return closedDoors;
    }
    public void setClosedDoors(boolean closedDoors) {
        this.closedDoors = closedDoors;
    }

}
