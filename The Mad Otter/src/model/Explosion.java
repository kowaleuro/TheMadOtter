package model;

import javafx.animation.AnimationTimer;
import javafx.scene.layout.Pane;
import map.Room;
import model.enemy.Enemy;

public class Explosion extends StaticObjects {

    Room actualRoom;

    public Explosion(double x, double y, Pane layer, Room room) {
        super(x, y, "graphics/explosion64x64.gif", layer);
        actualRoom = room;
        generateAndRemove();
    }

    public void generateAndRemove() {
        Explosion thisExplosion = this;
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
                endOfCollision();
                actualRoom.getExplosions().remove(thisExplosion);
                removeFromLayer();
                super.stop();
            }
        };
        animationTimer.start();
    }

    private void endOfCollision() {
        if (actualRoom.getEnemies().size() != 0) {
            for (Enemy enemy : actualRoom.getEnemies()) {
                enemy.setExplosionCollision(false);
            }
        }
    }


}
