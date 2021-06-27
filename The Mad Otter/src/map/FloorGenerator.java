package map;

import dev.Main;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.block.Trapdoor;
import model.item.Item;
import model.item.Sign;
import model.item.guns.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;
import java.util.Random;

public class FloorGenerator {

    private ArrayList<int[]> bitMapCoord = new ArrayList<>();
    private ArrayList<Room> roomList = new ArrayList<>();
    private int nrOfRooms;  //pierwiastek z liczby pokoi
    private int floorId;
    private Door door1;
    private Door door2;
    private Door door3;
    private Door door4;
    private Trapdoor trapdoor;
    private Pane layer;
    private final Random random = new Random();

    private int mapLoading = 0;

    public FloorGenerator(int nrOfRooms, Pane layer, int floorId) throws IOException {
        this.nrOfRooms = nrOfRooms;
        this.layer = layer;
        this.floorId = floorId;
        fillBitMapCoord();
        trapdoor = new Trapdoor(368, 368, layer);
        door1 = new Door(360, 0, "/graphics/map/floor1/doorH1.png", layer, 1);           // góra
        door2 = new Door(0, 360, "/graphics/map/floor1/doorV1.png", layer, 2);           // lewo
        door3 = new Door(360, 768, "/graphics/map/floor1/doorH1.png", layer, 3);         // dół
        door4 = new Door(768, 360, "/graphics/map/floor1/doorV1.png", layer, 4);         // prawo
        door1.setDoorBounds("up");
        door2.setDoorBounds("left");
        door3.setDoorBounds("down");
        door4.setDoorBounds("right");
        door3.getImageView().setRotate(180);
        door4.getImageView().setRotate(180);
        generateMap();
        generateShop();
        updateBoard();
    }

    private void generateMap() {
        int roomId = 0;
        for(int i=0; i < nrOfRooms; i++) {
            for(int j=0; j < nrOfRooms; j++) {
                RNG rng = new RNG((nrOfRooms * nrOfRooms - 1) / 2, floorId, roomId, layer, bitMapCoord);
                ArrayList<Door> doors = new ArrayList<>();
                if((i == 0 || i == nrOfRooms-1) || (j == 0 || j == nrOfRooms-1)) {
                    if((i == 0 || i == nrOfRooms-1) && (j == 0 || j == nrOfRooms-1)) {
                        if(i == 0 && j == 0) {
                            doors.add(door3);
                            doors.add(door4);
                            roomList.add(new Room(doors, roomId, rng.enemiesGenerator(roomId), null, rng.blockGenerator(roomId), this));
                        }
                        else if(i == nrOfRooms-1 && j == 0) {
                            doors.add(door2);
                            doors.add(door3);
                            roomList.add(new Room(doors, roomId, rng.enemiesGenerator(roomId), null, rng.blockGenerator(roomId), this));
                        }
                        else if(i == 0 && j == nrOfRooms-1) {
                            doors.add(door1);
                            doors.add(door4);
                            roomList.add(new Room(doors, roomId, rng.enemiesGenerator(roomId), null, rng.blockGenerator(roomId), this));
                        }
                        else if(i == nrOfRooms-1 && j == nrOfRooms-1) {
                            doors.add(door1);
                            doors.add(door2);
                            roomList.add(new Room(doors, roomId, rng.enemiesGenerator(roomId), null, rng.blockGenerator(roomId), this));
                        }
                    } else {
                        if(i == 0) {
                            doors.add(door1);
                            doors.add(door3);
                            doors.add(door4);
                            roomList.add(new Room(doors, roomId, rng.enemiesGenerator(roomId), null, rng.blockGenerator(roomId), this));
                        }
                        else if(i == nrOfRooms - 1) {
                            doors.add(door1);
                            doors.add(door2);
                            doors.add(door3);
                            roomList.add(new Room(doors, roomId, rng.enemiesGenerator(roomId), null, rng.blockGenerator(roomId), this));
                        }
                        else if(j == 0) {
                            doors.add(door2);
                            doors.add(door3);
                            doors.add(door4);
                            roomList.add(new Room(doors, roomId, rng.enemiesGenerator(roomId), null, rng.blockGenerator(roomId), this));
                        }
                        else if(j == nrOfRooms-1) {
                            doors.add(door1);
                            doors.add(door2);
                            doors.add(door4);
                            roomList.add(new Room(doors, roomId, rng.enemiesGenerator(roomId), null, rng.blockGenerator(roomId), this));
                        }
                    }
                } else {
                    doors.add(door1);
                    doors.add(door2);
                    doors.add(door3);
                    doors.add(door4);
                    if (roomId == (nrOfRooms*nrOfRooms-1)/2) {
                        roomList.add(new Room(doors, roomId, null, rng.itemsGenerator(roomId), null, this));

                    } else {
                        roomList.add(new Room(doors, roomId, rng.enemiesGenerator(roomId), null, rng.blockGenerator(roomId), this));
                    }
                }
                if (roomList.get(roomId).getEnemies().size() != 0) {
                    roomList.get(roomId).setClean(false);
                } else {
                    roomList.get(roomId).setClean(true);
                }
                if(roomId == (nrOfRooms*nrOfRooms-1)/2) {
                    roomList.get(roomId).getBlocks().add(trapdoor);
                }
                roomId++;

                mapLoading += 4;
            }
        }
    }

    private void generateShop() {
        int shopId = RNG.getShopRoomId((nrOfRooms*nrOfRooms-1)/2);

        roomList.get(shopId).setShop(true);
        roomList.get(shopId).setClean(true);
        roomList.get(shopId).getEnemies().clear();
        roomList.get(shopId).getBlocks().clear();
        roomList.get(shopId).getItems().clear();
        roomList.get(shopId).eraseEnemies();
        roomList.get(shopId).eraseBlocks();
        roomList.get(shopId).eraseItems();

        int gunA;
        int gunB;

        ArrayList<Item> gunList = new ArrayList<>();
        ArrayList<Item> choosedGun = new ArrayList<>();
        gunList.add(new Ak47(2000, 2000, layer));
        gunList.add(new Deagle(2000, 2000, layer));
        gunList.add(new Glock(2000, 2000, layer));
        gunList.add(new LaserGun(2000, 2000, layer));
        gunList.add(new M16(2000, 2000, layer));
        gunList.add(new Mp5(2000, 2000, layer));
        gunList.add(new PlasmaGun(2000, 2000, layer));
        gunList.add(new RocketLauncher(2000, 2000, layer));
        gunList.add(new Scar(2000, 2000, layer));
        gunList.add(new Shotgun(2000, 2000, layer));
        gunList.add(new SniperRifle(2000, 2000, layer));
        gunList.add(new Stg44(2000, 2000, layer));
        gunList.add(new Uzi(2000, 2000, layer));
        gunList.add(new PoisonDagger(2000, 2000, layer));

        do {
            gunA = random.nextInt(gunList.size());
            gunB = random.nextInt(gunList.size());
        } while (gunA == gunB);

        choosedGun.add(gunList.get(gunA));
        choosedGun.add(gunList.get(gunB));
        choosedGun.get(0).setLocation(300 - (choosedGun.get(0).getImageStatic().getWidth() - 32)/2,300);
        choosedGun.get(1).setLocation(500 - (choosedGun.get(1).getImageStatic().getWidth() - 32)/2,300);
        ((Gun) choosedGun.get(0)).setBuyStandard(true);
        ((Gun) choosedGun.get(1)).setBuyHealth(true);

        String textGunA = ((Gun) choosedGun.get(0)).getGunName() + "\n" +
                            "Dmg: " + ((Gun) choosedGun.get(0)).getDmg() + '\n' +
                            "Price: " + ((Gun) choosedGun.get(0)).getPrice();
        String textGunB = ((Gun) choosedGun.get(1)).getGunName() + "\n" +
                            "Dmg: " + ((Gun) choosedGun.get(1)).getDmg() + '\n' +
                            "Price: " + ((Gun) choosedGun.get(1)).getPrice();

        choosedGun.add(new Sign(300, 400, layer, textGunA));
        choosedGun.add(new Sign(500, 400, layer, textGunB));

        gunList.clear();

        for(Item item : choosedGun) {
            item.removeFromLayer();
            roomList.get(shopId).getItems().add(item);
        }
    }

    private void updateBoard() {
        if (floorId == 2) {
            layer.getScene().getRoot().setStyle("-fx-background-image:url('/graphics/map/floor2/board2.png')");
        }
        if (floorId == 3) {
            layer.getScene().getRoot().setStyle("-fx-background-image:url('/graphics/map/floor3/board3.png')");
        }
        if (floorId == 4) {
            layer.getScene().getRoot().setStyle("-fx-background-image:url('/graphics/map/floor4/board4.png')");
        }

        getDoor1().getImageView().setImage(new Image("/graphics/map/floor" + floorId + "/doorH" + floorId + ".png"));
        getDoor2().getImageView().setImage(new Image("/graphics/map/floor" + floorId + "/doorV" + floorId + ".png"));
        getDoor3().getImageView().setImage(new Image("/graphics/map/floor" + floorId + "/doorH" + floorId + ".png"));
        getDoor4().getImageView().setImage(new Image("/graphics/map/floor" + floorId + "/doorV" + floorId + ".png"));
        getTrapdoor().getImageView().setImage(new Image("/graphics/map/floor" + floorId + "/trapDoorClose" + floorId + ".png"));
        getTrapdoor().setOpenedTrapdoorImage(new Image("/graphics/map/floor" + floorId + "/trapDoorOpen" + floorId + ".png"));

    }

    private void fillBitMapCoord() {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                int[] coord = new int[2];
                coord[0] = i;
                coord[1] = j;
                bitMapCoord.add(coord);
            }
        }
        Collections.shuffle(bitMapCoord);
    }

    public ArrayList<Room> getRoomList() {
        return roomList;
    }
    public void setRoomList(ArrayList<Room> roomList) {
        this.roomList = roomList;
    }

    public int getNrOfRooms() {
        return nrOfRooms;
    }
    public void setNrOfRooms(int nrOfRooms) {
        this.nrOfRooms = nrOfRooms;
    }

    public Door getDoor1() {
        return door1;
    }
    public void setDoor1(Door door1) {
        this.door1 = door1;
    }

    public Door getDoor2() {
        return door2;
    }
    public void setDoor2(Door door2) {
        this.door2 = door2;
    }

    public Door getDoor3() {
        return door3;
    }
    public void setDoor3(Door door3) {
        this.door3 = door3;
    }

    public Door getDoor4() {
        return door4;
    }
    public void setDoor4(Door door4) {
        this.door4 = door4;
    }

    public Pane getLayer() {
        return layer;
    }
    public void setLayer(Pane layer) {
        this.layer = layer;
    }

    public Trapdoor getTrapdoor() {
        return trapdoor;
    }
    public void setTrapdoor(Trapdoor trapdoor) {
        this.trapdoor = trapdoor;
    }

    public int getFloorId() {
        return floorId;
    }
    public void setFloorId(int floorId) {
        this.floorId = floorId;
    }

    public int getMapLoading() {
        return mapLoading;
    }

    public void setMapLoading(int mapLoading) {
        this.mapLoading = mapLoading;
    }
}