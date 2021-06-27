package map;

import javafx.scene.layout.Pane;
import model.block.*;
import model.enemy.*;
import model.item.*;

import java.util.ArrayList;
import java.util.Random;

public class RNG {

    private final Pane layer;
    private BitMapLoader bitMap;
    private final int floorId;
    private final int centerRoom; // id pomiszczenia środkowego
    private final Random random;

    public RNG(int centerRoom, int floorId, int roomId, Pane layer, ArrayList<int[]> bitMapCoord) {
        this.layer = layer;
        this.floorId = floorId;
        this.centerRoom = centerRoom;
        this.random = new Random();
        loadBitMap(roomId, bitMapCoord);
    }

    private void loadBitMap(int roomId, ArrayList<int[]> bitMapCoord) {
        if (roomId != centerRoom) {
            int randCol = bitMapCoord.get(roomId)[0];
            int randRow = bitMapCoord.get(roomId)[1];
//            int randCol = 0;
//            int randRow = 0; <---- mapa testowa (0, 0)
            bitMap = new BitMapLoader(randCol, randRow); // nie podajemy współrzędnych bitmapy, tylko w której kolumnie i rzędzie występuje (liczone od 0)
        }
    }

    public ArrayList<Item> itemsGenerator(int roomId) {
        ArrayList<Item> items = new ArrayList<>();
        if (floorId == 1) {
            items.add(new Coin(100,100, layer));
            items.add(new Dollar(100,200, layer));
            items.add(new SmallFish(100,300, layer));
            items.add(new Fish(100,400, layer));
            items.add(new Bomb(100,500, layer));
            items.add(new BombPack(100,600, layer));
            items.add(new Star(100,700, layer));
            items.add(new BlueSphere(200,100, layer));
            items.add(new GreenSphere(200,200, layer));
            items.add(new RedSphere(200,600, layer));
            items.add(new Heart(200,700, layer));
        }

        if (roomId != (centerRoom)) {
            for (Item item : items) {
                item.removeFromLayer();
            }
        }
        return items;
    }


    public ArrayList<Block> blockGenerator(int roomId) {
        ArrayList<Block> blocks = new ArrayList<>();

        for (int i = 0; i < bitMap.getSolidBlocksLoc().size(); i++) {
            blocks.add(new SolidBlock(bitMap.getSolidBlocksLoc().get(i)[0], bitMap.getSolidBlocksLoc().get(i)[1], layer));
        }

        for (int i = 0; i < bitMap.getSoftBlocksLoc().size(); i++) {
            blocks.add(new SoftBlock(bitMap.getSoftBlocksLoc().get(i)[0], bitMap.getSoftBlocksLoc().get(i)[1], layer));
        }

        for (int i = 0; i < bitMap.getSpikeBlockLoc().size(); i++) {
            blocks.add(new SpikeBlock(bitMap.getSpikeBlockLoc().get(i)[0], bitMap.getSpikeBlockLoc().get(i)[1], layer));
        }

        for (int i = 0; i < bitMap.getBarrelLoc().size(); i++) {
            blocks.add(new Barrel(bitMap.getBarrelLoc().get(i)[0], bitMap.getBarrelLoc().get(i)[1], layer));
        }

        for (int i = 0; i < bitMap.getTempSpikeBlockLoc().size(); i++) {
            blocks.add(new TempSpikeBlock(bitMap.getTempSpikeBlockLoc().get(i)[0], bitMap.getTempSpikeBlockLoc().get(i)[1], layer));
        }

        for (int i = 0; i < bitMap.getBoxLoc().size(); i++) {
            blocks.add(new Box(bitMap.getBoxLoc().get(i)[0], bitMap.getBoxLoc().get(i)[1], layer));
        }

        for (int i = 0; i < bitMap.getBushLoc().size(); i++) {
            blocks.add(new BushBlock(bitMap.getBushLoc().get(i)[0], bitMap.getBushLoc().get(i)[1], layer));
        }

        for (int i = 0; i < bitMap.getBonfireLoc().size(); i++) {
            blocks.add(new BonFire(bitMap.getBonfireLoc().get(i)[0], bitMap.getBonfireLoc().get(i)[1], layer));
        }

        if (roomId != (centerRoom)) {
            for (Block block : blocks) {
                block.removeFromLayer();
            }
        }
        return blocks;
    }

    public ArrayList<Enemy> enemiesGenerator(int roomId) {
        if (bitMap.getEnemySpotLoc().size() == 0) {
            return null;
        }
        ArrayList<Enemy> enemies = new ArrayList<>();
        int howMany = bitMap.getEnemySpotLoc().size();

        for (int i = 0; i < howMany; i++) {
            enemies.add(getRandomEnemy(i));
        }

        return enemies;
    }

    private Enemy getRandomEnemy(int nextLoc) {
        int value = random.nextInt(13);
        int x = bitMap.getEnemySpotLoc().get(nextLoc)[0];
        int y = bitMap.getEnemySpotLoc().get(nextLoc)[1];
        return switch (value) {
            case 0 -> new Crab(x, y, layer);
            case 1 -> new Turret(x, y, layer);
            case 2 -> new Wasp(x, y, layer);
            case 3 -> new Snake(x, y, layer);
            case 4 -> new Spider(x, y, layer);
            case 5 -> new Fly(x, y, layer);
            case 6 -> new BombOwl(x, y, layer);
            case 7 -> new Boomer(x, y, layer);
            case 8 -> new Slime(x, y, layer, "SlimeKing");
            case 9 -> new Bat(x, y, layer);
            case 10 -> new DevilFly(x, y, layer);
            case 11 -> new Diglet(x, y, layer, bitMap.getEnemySpotLoc());
            case 12 -> new Teleporto(x, y, layer, bitMap.getEnemySpotLoc());
            default -> throw new IllegalStateException("Can't find enemy with number: " + value);
        };
    }

    public static int getShopRoomId(int centerRoom) {
        Random random = new Random();
        if(random.nextBoolean()) {
            return random.nextInt(centerRoom);
        } else {
            return (random.nextInt(centerRoom) + centerRoom + 1);
        }
    }

    public static Item getRandomItem(double x, double y, double itemChance, Pane layer) { // itemChance -> szansa, że wyleci jakiś item: (zakres <0, 1>)
        Random random = new Random();
        int rng = random.nextInt((int) (100/itemChance)) + 1;
        if (rng < 20) {
            return new Bomb(x+5, y, layer);
        } else if (rng >= 21 && rng < 30) {
            return new BombPack(x+5, y, layer);
        } else if (rng >= 31 && rng < 50) {
            return new SmallFish(x, y, layer);
        } else if (rng >= 51 && rng < 60) {
            return new Fish(x, y, layer);
        } else if (rng >= 61 && rng < 80) {
            return new Coin(x+4, y, layer);
        } else if (rng >= 81 && rng < 90) {
            return new Dollar(x+4, y, layer);
        } else if (rng == 90) {
            return new Heart(x, y, layer);
        } else if (rng == 91) {
            return new Star(x, y, layer);
        } else if (rng == 92) {
            return new RedSphere(x, y, layer);
        } else if (rng == 93) {
            return new GreenSphere(x, y, layer);
        } else if (rng == 94) {
            return new BlueSphere(x, y, layer);
        } else {
            return null;
        }
    }

}
