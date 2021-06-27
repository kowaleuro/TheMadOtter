package model;

import javafx.scene.layout.Pane;
import map.Room;

public class BombFired extends StaticObjects{

    public BombFired(double x, double y, Pane layer) {
        super(x, y, "graphics/items/bombFired.gif", layer);
    }
}
