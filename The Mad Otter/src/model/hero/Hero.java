package model.hero;

import com.sun.javafx.tk.Toolkit;
import dev.EventHandling;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import map.Door;
import map.FloorGenerator;
import map.Room;
import model.BombFired;
import model.Bullet;
import model.Explosion;
import model.MovingObjects;
import model.item.guns.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class Hero extends MovingObjects {

    private boolean shooting;
    private boolean isDamaged;
    private boolean isHiding;
    private boolean additionalData;
    private boolean paused;
    private boolean godmode;
    private boolean gameWin;
    private boolean inBush;

    private int remainingHealth;
    private int money;
    private int bombs;
    private int points;
    private int dmgFactor;
    private int kills;

    private long cooldownShot;
    private long lastShot = 0;
    private long lastChange = 0;
    private long lastEnemyTouch = 0;
    private long lastBomb = 0;
    private long lastF1 = 0;
    private long lastF2 = 0;
    private long lastF3 = 0;
    private long lastPause = 0;

    private String nickname = " ";
    private String whoKills;
    private ArrayList<Gun> equipment = new ArrayList<>();
    private final ArrayList<AnimationTimer> powerUpTimers = new ArrayList<>();
    private HeroActions currentAction;
    private Room actualRoom;
    private FloorGenerator floor;
    private Gun actualGun;
    private final Random random = new Random();
    private final Pane layer;
    private AnimationTimer stopWatch;
    private boolean toLoading;


    public Hero(double x, double y, Pane layer) throws IOException {
        super(x, y, "/graphics/hero/otterStatic.gif", "/graphics/hero/otterMoving.gif", "graphics/hero/otterStaticShoting.gif", "graphics/hero/otterMovingShoting.gif",  layer);
        floor = new FloorGenerator(5, layer, 1);  //nrOfRooms musi być nieparzyste (!!!)
        actualRoom = floor.getRoomList().get((floor.getNrOfRooms()* floor.getNrOfRooms()-1)/2);
        currentAction = HeroActions.UP;
        actualRoom.makeHeroBulletList();
        equipment.add(new Pistol(layer));
        actualGun = equipment.get(0);
        cooldownShot = actualGun.getCooldownShot();
        remainingHealth = 20;
        getImageView().toFront();
        this.layer = layer;
        setDamaged(false);
        setHiding(false);
        dmgFactor = 1;
    }

    public void updateHero() {
        isAlive();
        setShootingStatus(shooting);
        actualRoom.checkCollision(this);
        updateBullets();
        updateLocation();
        doorCollision();
        try {
            goToNextFloor();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void shot() {
        double x = 0;
        double y = 0;
        double velX = 0;
        double velY = 0;
        cooldownShot = actualGun.getCooldownShot();

        if (currentAction == HeroActions.SHOTUP) {
            getImageView().setViewport(getFrame().get(1));
            x = getX() + 32;
            y = getY() + 25;
            velX = 0;
            velY = -1;
        }
        else if (currentAction == HeroActions.SHOTDOWN) {
            getImageView().setViewport(getFrame().get(0));
            x = getX() + 27;
            y = getY() + 28;
            velX = 0;
            velY = 1;
        }
        else if (currentAction == HeroActions.SHOTLEFT) {
            getImageView().setViewport(getFrame().get(3));
            x = getX() + 2;
            y = getY() + 21;
            velX = -1;
            velY = 0;
        }
        else if (currentAction == HeroActions.SHOTRIGHT) {
            getImageView().setViewport(getFrame().get(2));
            x = getX() + 61;
            y = getY() + 21;
            velX = 1;
            velY = 0;
        }

        long time = System.currentTimeMillis();

        if (time > lastShot + cooldownShot) {
            lastShot = time;
            if (actualGun instanceof Shotgun) {
                double newVelX = 0;
                double newVelY = 0;
                for(double i = -0.1; i<0.1;) {
                    if (velX != 0) {
                        newVelY = i + (random.nextDouble() - 0.5)/5;
                        newVelX = velX + (random.nextDouble() - 0.5)/10;
                        i = i + 0.02;
                    }
                    if (velY != 0) {
                        newVelY = velY + (random.nextDouble() - 0.5)/10;
                        newVelX = i + (random.nextDouble() - 0.5)/5;
                        i = i + 0.02;
                    }
                    actualRoom.getHeroBullets().add(new Bullet(x, y, newVelX*actualGun.getBulletVel(), newVelY*actualGun.getBulletVel(), dmgFactor*actualGun.getDmg(), actualGun.getPathBullet(), actualGun.getPathBullet(), getLayer()));
                }
            }
            else if (actualGun instanceof M16) {
                for (double i=1; i<1.3; i = i+0.1) {
                    actualRoom.getHeroBullets().add(new Bullet(x, y, i*velX*actualGun.getBulletVel(), i*velY*actualGun.getBulletVel(), dmgFactor*actualGun.getDmg(), actualGun.getPathBullet(), actualGun.getPathBullet(), getLayer()));
                }
            }
            else {
                actualRoom.getHeroBullets().add(new Bullet(x, y, velX*actualGun.getBulletVel(), velY*actualGun.getBulletVel(), dmgFactor*actualGun.getDmg(), actualGun.getPathBullet(), actualGun.getPathBullet(), getLayer()));
            }
        }
    }

    public void move() {
        int vel = 5;
        if (currentAction == HeroActions.UP) {
            setVelY(-vel);
        }
        if (currentAction == HeroActions.DOWN) {
            setVelY(vel);
        }
        if (currentAction == HeroActions.RIGHT) {
            setVelX(vel);
        }
        if (currentAction == HeroActions.LEFT) {
            setVelX(-vel);
        }
    }

    private void updateBullets() {
        ArrayList<MovingObjects> toBeRemoved = new ArrayList<>();
        if (actualRoom.getHeroBullets() != null) {
            for (Bullet bullet : actualRoom.getHeroBullets()) {
                bullet.updateLocation();
                if (bullet.removeBullets()) {
                    toBeRemoved.add(bullet);
                }
            }
            actualRoom.removeMovingObjects(toBeRemoved, this);
        }
        if (actualRoom.getEnemyBullets() != null) {
            for (Bullet bullet : actualRoom.getEnemyBullets()) {
                bullet.updateLocation();
                if (bullet.removeBullets()) {
                    toBeRemoved.add(bullet);
                }
            }
            actualRoom.removeMovingObjects(toBeRemoved, this);
        }
    }

    public void doorCollision() {
        Rectangle heroBounds = getBounds();
        ArrayList<Door> doors = actualRoom.getDoors();
        for (Door door : doors) {
            Rectangle doorBounds = door.getDoorBounds();
            if (heroBounds.intersects(doorBounds.getBoundsInParent())) {
                if (door.getDoorId() == 1 && !door.isClosedDoors()) {
                    goToNextRoom(floor.getRoomList().get(actualRoom.getRoomId() - 1), actualRoom);
                    actualRoom = floor.getRoomList().get(actualRoom.getRoomId() - 1);
                    setX(360);
                    setY(700);
                }
                if (door.getDoorId() == 2 && !door.isClosedDoors()) {
                    goToNextRoom(floor.getRoomList().get(actualRoom.getRoomId() - floor.getNrOfRooms()), actualRoom);
                    actualRoom = floor.getRoomList().get(actualRoom.getRoomId() - floor.getNrOfRooms());
                    setX(700);
                    setY(360);
                }
                if (door.getDoorId() == 3 && !door.isClosedDoors()) {
                    goToNextRoom(floor.getRoomList().get(actualRoom.getRoomId() + 1), actualRoom);
                    actualRoom = floor.getRoomList().get(actualRoom.getRoomId() + 1);
                    setX(360);
                    setY(30);
                }
                if (door.getDoorId() == 4 && !door.isClosedDoors()) {
                    goToNextRoom(floor.getRoomList().get(actualRoom.getRoomId() + floor.getNrOfRooms()), actualRoom);
                    actualRoom = floor.getRoomList().get(actualRoom.getRoomId() + floor.getNrOfRooms());
                    setX(40);
                    setY(360);
                }
            }
        }
    }

    private void goToNextFloor() throws IOException {
        if (floor.getTrapdoor().isOpen() && actualRoom.getRoomId() == 12) {
            if (this.getSmallerBounds().intersects(floor.getTrapdoor().getBounds().getBoundsInParent())) {
                if (floor.getFloorId() + 1 == 5) {
                    gameWin = true;
                    return;
                }

                actualRoom.eraseEnemies(); // pokój ze starego piętra
                actualRoom.eraseItems();
                actualRoom.eraseBlocks();
                actualRoom.eraseBullets();
                actualRoom.eraseExplosions();
                for (Door door : getActualRoom().getDoors()) {
                    door.removeFromLayer();
                }

                ImageView l = new ImageView(new Image("graphics/loading.png"));
                setVelX(0);
                setVelY(0);
                EventHandling.getInputList().clear();
                layer.getChildren().add(l);
                this.setPaused(true);
                    Platform.runLater(()-> {
                        try {
                            floor = new FloorGenerator(5, layer, floor.getFloorId() + 1);
                        } catch (IOException e) {
                            System.out.println('y');
                            e.printStackTrace();
                        }
                    });
                    Platform.runLater(()-> {
                        actualRoom = floor.getRoomList().get((floor.getNrOfRooms() * floor.getNrOfRooms() - 1) / 2); // pokój z nowego piętra
                        for (Door door : actualRoom.getDoors()) {
                            door.setClosedDoors(true);
                        }
                        currentAction = HeroActions.UP;
                        actualRoom.makeHeroBulletList();
                        this.getImageView().toFront();
                        points += 1000;
                        isHiding = false;
                        layer.getChildren().remove(l);
                    });
                    Platform.runLater(() -> {
                        this.setPaused(false);
                        setLocation(250, 250);
                        setVelX(0);
                        setVelY(0);
                        EventHandling.getInputList().clear();
                        for (Door door : actualRoom.getDoors()) {
                            door.setClosedDoors(false);
                        }
                    });
            }
        }
    }

    private void goToNextRoom(Room nextRoom, Room actualRoom) {
        ArrayList<Integer> tempNextRemove = new ArrayList<>();
        ArrayList<Integer> tempNextAdd = new ArrayList<>();
        for (int i = 0; i < nextRoom.getDoors().size(); i++) {
            tempNextRemove.add(nextRoom.getDoors().get(i).getDoorId());
        }
        if (nextRoom.getDoors().size() < actualRoom.getDoors().size()) {
            if (!tempNextRemove.contains(1)) {
                floor.getDoor1().removeFromLayer();
                floor.getDoor1().setClosedDoors(true);
            }
            if (!tempNextRemove.contains(2)) {
                floor.getDoor2().removeFromLayer();
                floor.getDoor2().setClosedDoors(true);
            }
            if (!tempNextRemove.contains(3)) {
                floor.getDoor3().removeFromLayer();
                floor.getDoor3().setClosedDoors(true);
            }
            if (!tempNextRemove.contains(4)) {
                floor.getDoor4().removeFromLayer();
                floor.getDoor4().setClosedDoors(true);
            }
        } else {
            for (int i = 0; i < actualRoom.getDoors().size(); i++) {
                tempNextAdd.add(actualRoom.getDoors().get(i).getDoorId());
            }
            if (tempNextRemove.contains(1) && !tempNextAdd.contains(1)) {
                floor.getDoor1().addToLayer();
                floor.getDoor1().setClosedDoors(false);
            }
            if (tempNextRemove.contains(2) && !tempNextAdd.contains(2)) {
                floor.getDoor2().addToLayer();
                floor.getDoor2().setClosedDoors(false);
            }
            if (tempNextRemove.contains(3) && !tempNextAdd.contains(3)) {
                floor.getDoor3().addToLayer();
                floor.getDoor3().setClosedDoors(false);
            }
            if (tempNextRemove.contains(4) && !tempNextAdd.contains(4)) {
                floor.getDoor4().addToLayer();
                floor.getDoor4().setClosedDoors(false);
            }
        }
        actualRoom.eraseEnemies();
        actualRoom.eraseItems();
        actualRoom.eraseBlocks();
        actualRoom.eraseBullets();
        actualRoom.eraseExplosions();
        actualRoom.erasePutBombs();
        actualRoom.eraseAnimations(this);

        isHiding = false;
        nextRoom.newRoomTimer();

        nextRoom.drawEnemies();
        nextRoom.drawBlocks();
        nextRoom.drawItems();
        nextRoom.makeHeroBulletList();
        nextRoom.makeEnemyBulletList();
        if (!nextRoom.getEnemies().isEmpty()) {
            for (Door door : nextRoom.getDoors()) {
                door.removeFromLayer();
                door.setClosedDoors(true);
            }
        }
    }

    public void addNewGun(Gun gun) {
        boolean isOwned = false;
        for (Gun ownedGun : equipment) {
            if (ownedGun.getClass().equals(gun.getClass())) {
                isOwned = true;
                break;
            }
        }
        if (!isOwned) {
            equipment.add(gun);
            actualGun = gun;
        }
    }

    public void changeWeapon(boolean forward) {
        int length = equipment.size();
        if (length == 1) {
            return;
        }
        int selectedGun = equipment.indexOf(actualGun);
        long time = System.currentTimeMillis();
        if (time > lastChange + 250) {
            lastChange = time;
            if (forward) {
                if (selectedGun + 1 == length) {
                    actualGun = equipment.get(0);
                } else {
                    actualGun = equipment.get(equipment.indexOf(actualGun) + 1);
                }
            } else {
                if (selectedGun - 1 == -1) {
                    actualGun = equipment.get(length-1);
                } else {
                    actualGun = equipment.get(equipment.indexOf(actualGun) - 1);
                }
            }
        }
    }

    public void putBomb() {
        int roomWithPuttedBomb = actualRoom.getRoomId();
        long time = System.currentTimeMillis();
        if (time > lastBomb + 250) {
            lastBomb = time;
            if (bombs > 0) {
                BombFired newBomb = new BombFired(getX() + 16, getY() + 32, layer);
                actualRoom.getPutBombs().add(newBomb);
                bombs--;

                AnimationTimer animationTimer = new AnimationTimer() {
                    @Override
                    public void handle(long l) {
                        if (roomWithPuttedBomb != actualRoom.getRoomId()) {
                            super.stop();
                        }
                        if (System.currentTimeMillis() - time > 2200) {
                            this.stop();
                        }
                    }

                    @Override
                    public void stop() {
                        newBomb.removeFromLayer();
                        actualRoom.getExplosions().add(new Explosion(newBomb.getX() - 20, newBomb.getY() - 16, layer, actualRoom));
                        super.stop();
                    }
                };
                animationTimer.start();
            }
        }
    }

    public void healthDown(int minus, Object dmgDealer) {
        if (!godmode) {
            long time = System.currentTimeMillis();
            if (time > lastEnemyTouch + 2000) {
                setRemainingHealth(Math.max(getRemainingHealth() - minus, 0));
                lastEnemyTouch = time;
                long timeInNano = System.nanoTime();
                damageAnimation(timeInNano);
            }
            whoKills = dmgDealer.getClass().getSimpleName();
        }
    }

    private void damageAnimation(long timeInNano) {
        AnimationTimer animationTimer = new AnimationTimer(){
            long lastUpdate = 0;
            @Override
            public void handle(long l) {
                if ((l - lastUpdate) >= 200_000_000) {
                    if (getImageView().getOpacity() == 1) {
                        getImageView().setOpacity(0.3);
                    } else if (getImageView().getOpacity() == 0.3) {
                        getImageView().setOpacity(1);

                    }
                    lastUpdate = l;
                }
                if (l - timeInNano >= 1_800_000_000) {
                    this.stop();
                }
            }

            @Override
            public void stop() {
                getImageView().setOpacity(1);
                super.stop();
                setDamaged(false);
            }
        };
        animationTimer.start();
        setDamaged(true);
    }

    public void bushEffect() {
        if (!isDamaged && (getImageView().getOpacity() != 0.49)) {
            setHiding(true);
            getImageView().setOpacity(0.5);
        }

    }

    // F1
    public void turnOnAdditionalData() {
        long time = System.currentTimeMillis();
        if (time > lastF1 + 250) {
            lastF1 = time;
            additionalData = !additionalData;
        }
    }

    // F2
    public void addAllGunsAndMoney() {
        long time = System.currentTimeMillis();
        if (time > lastF2 + 250) {
            lastF2 = time;
            equipment = new ArrayList<>();
            equipment.add(new Ak47(2000, 2000, layer));
            equipment.add(new Deagle(2000, 2000, layer));
            equipment.add(new Glock(2000, 2000, layer));
            equipment.add(new LaserGun(2000, 2000, layer));
            equipment.add(new M16(2000, 2000, layer));
            equipment.add(new Mp5(2000, 2000, layer));
            equipment.add(new Pistol(layer));
            equipment.add(new PlasmaGun(2000, 2000, layer));
            equipment.add(new RocketLauncher(2000, 2000, layer));
            equipment.add(new Scar(2000, 2000, layer));
            equipment.add(new Shotgun(2000, 2000, layer));
            equipment.add(new SniperRifle(2000, 2000, layer));
            equipment.add(new Stg44(2000, 2000, layer));
            equipment.add(new Uzi(2000, 2000, layer));
            equipment.add(new PoisonDagger(2000, 2000, layer));
            money += 100;
            bombs += 100;
            remainingHealth = 20;
            actualGun = equipment.get(0);
        }
    }

    // F3
    public void killAllEnemies() {
        long time = System.currentTimeMillis();
        if (time > lastF3 + 250) {
            lastF3 = time;
            for (Room room : floor.getRoomList()) {
                if (!room.isClean()) {
                    room.eraseEnemies();
                    room.getEnemies().clear();
                    room.setClean(true);
                }
            }
        }
    }

    public void pauseGame() {
        long time = System.currentTimeMillis();
        if (time > lastPause + 250) {
            lastPause = time;
            paused = !paused;
        }
        if (paused) {
            stopWatch.stop();
            for (AnimationTimer timer : powerUpTimers) {
                timer.stop();
            }
        } else {
            stopWatch.start();
            for (AnimationTimer timer : powerUpTimers) {
                timer.start();
            }
        }
    }


    public boolean isAlive() {
        return remainingHealth != 0;
    }

    public boolean isGameWin() {
        return gameWin;
    }
    public void setGameWin(boolean gameWin) {
        this.gameWin = gameWin;
    }

    public int getRemainingHealth() {
        return remainingHealth;
    }
    public void setRemainingHealth(int remainingHealth) {
        this.remainingHealth = remainingHealth;
    }

    public HeroActions getCurrentAction() {
        return currentAction;
    }
    public void setCurrentAction(HeroActions currentAction) {
        this.currentAction = currentAction;
    }

    public Room getActualRoom() {
        return actualRoom;
    }
    public void setActualRoom(Room actualRoom) {
        this.actualRoom = actualRoom;
    }

    public FloorGenerator getFloor() {
        return floor;
    }
    public void setFloor(FloorGenerator floor) {
        this.floor = floor;
    }

    public boolean isShooting() {
        return shooting;
    }
    public void setShooting(boolean shooting) {
        this.shooting = shooting;
    }

    public Gun getActualGun() {
        return actualGun;
    }
    public void setActualGun(Gun actualGun) {
        this.actualGun = actualGun;
    }

    public int getMoney() {
        return money;
    }
    public void setMoney(int money) {
        this.money = money;
    }

    public int getBombs() {
        return bombs;
    }
    public void setBombs(int bombs) {
        this.bombs = bombs;
    }

    public boolean isAdditionalData() {
        return additionalData;
    }
    public void setAdditionalData(boolean additionalData) {
        this.additionalData = additionalData;
    }

    public boolean isDamaged() {
        return isDamaged;
    }
    public void setDamaged(boolean damaged) {
        isDamaged = damaged;
    }

    public boolean isHiding() {
        return isHiding;
    }
    public void setHiding(boolean hiding) {
        isHiding = hiding;
    }

    public int getPoints() {
        return points;
    }
    public void setPoints(int points) {
        this.points = points;
    }

    public ArrayList<Gun> getEquipment() {
        return equipment;
    }
    public void setEquipment(ArrayList<Gun> equipment) {
        this.equipment = equipment;
    }

    public boolean isPaused() {
        return paused;
    }
    public void setPaused(boolean paused) {
        this.paused = paused;
    }

    public boolean isGodmode() {
        return godmode;
    }
    public void setGodmode(boolean godmode) {
        this.godmode = godmode;
    }

    public int getDmgFactor() {
        return dmgFactor;
    }
    public void setDmgFactor(int dmgFactor) {
        this.dmgFactor = dmgFactor;
    }

    public AnimationTimer getStopWatch() {
        return stopWatch;
    }
    public void setStopWatch(AnimationTimer stopWatch) {
        this.stopWatch = stopWatch;
    }

    public ArrayList<AnimationTimer> getPowerUpTimers() {
        return powerUpTimers;
    }

    public String getNickname() {
        return nickname;
    }
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public boolean isInBush() {
        return inBush;
    }
    public void setInBush(boolean inBush) {
        this.inBush = inBush;
    }

    public int getKills() {
        return kills;
    }
    public void setKills(int kills) {
        this.kills = kills;
    }

    public String getWhoKills() {
        return whoKills;
    }
    public void setWhoKills(String whoKills) {
        this.whoKills = whoKills;
    }
}
