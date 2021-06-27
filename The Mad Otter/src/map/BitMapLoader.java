package map;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

public class BitMapLoader {

    private ArrayList<int[]> solidBlocksLoc = new ArrayList<>();
    private ArrayList<int[]> softBlocksLoc = new ArrayList<>();
    private ArrayList<int[]> spikeBlockLoc = new ArrayList<>();
    private ArrayList<int[]> tempSpikeBlockLoc = new ArrayList<>();
    private ArrayList<int[]> barrelLoc = new ArrayList<>();
    private ArrayList<int[]> boxLoc = new ArrayList<>();
    private ArrayList<int[]> bushLoc = new ArrayList<>();
    private ArrayList<int[]> bonfireLoc = new ArrayList<>();
    private ArrayList<int[]> enemySpotLoc = new ArrayList<>();
    private BufferedImage mapImage;

    public BitMapLoader(int col, int row) {
        readMap(col, row);
    }

    public void readMap(int col, int row) {
        mapImage = loadImage("src/graphics/bitMapFinal.png");
        mapImage = mapImage.getSubimage(col*25, row*25, 25, 25); // jedna bitmapa ma rozmiar 25x25, pobierając bitmapę, podajemy tylko kolumne i wiersz
        if (mapImage == null) {
            System.out.println("Given path is invalid...");
            return;
        }

        int solidBlock = new Color(0, 0, 255).getRGB();
        int softBlock = new Color(0, 100, 255).getRGB();
        int spikeBlock = new Color(0, 200, 255).getRGB();
        int barrel = new Color(75, 0, 0).getRGB();
        int tempSpikeBlock = new Color(100, 0, 255).getRGB();
        int box = new Color(200, 200, 0).getRGB();
        int bush = new Color(0, 255, 0).getRGB();
        int bonfire = new Color(255, 100, 0).getRGB();
        int enemySpot = new Color(255, 0, 0).getRGB();

        for (int x = 0; x < mapImage.getWidth(); x++) {
            for (int y = 0; y < mapImage.getHeight(); y++) {

                int currentPixel = mapImage.getRGB(x, y);
                int xLoc = x*32;
                int yLoc = y*32;
                int[] locXY = new int[2];

                if(currentPixel == solidBlock) {
                    locXY[0] = xLoc;
                    locXY[1] = yLoc;
                    solidBlocksLoc.add(locXY);
                }
                if(currentPixel == softBlock) {
                    locXY[0] = xLoc;
                    locXY[1] = yLoc;
                    softBlocksLoc.add(locXY);
                }
                if(currentPixel == spikeBlock) {
                    locXY[0] = xLoc;
                    locXY[1] = yLoc;
                    spikeBlockLoc.add(locXY);
                }
                if(currentPixel == barrel) {
                    locXY[0] = xLoc;
                    locXY[1] = yLoc;
                    barrelLoc.add(locXY);
                }
                if(currentPixel == enemySpot) {
                    locXY[0] = xLoc;
                    locXY[1] = yLoc;
                    enemySpotLoc.add(locXY);
                }
                if(currentPixel == tempSpikeBlock) {
                    locXY[0] = xLoc;
                    locXY[1] = yLoc;
                    tempSpikeBlockLoc.add(locXY);
                }
                if(currentPixel == box) {
                    locXY[0] = xLoc;
                    locXY[1] = yLoc;
                    boxLoc.add(locXY);
                }
                if(currentPixel == bush) {
                    locXY[0] = xLoc;
                    locXY[1] = yLoc;
                    bushLoc.add(locXY);
                }
                if(currentPixel == bonfire) {
                    locXY[0] = xLoc;
                    locXY[1] = yLoc;
                    bonfireLoc.add(locXY);
                }
            }
        }
    }


    public BufferedImage loadImage(String path) {
        BufferedImage imageToReturn = null;
        try {
            imageToReturn = ImageIO.read(new FileInputStream(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return imageToReturn;
    }


    public BufferedImage getMapImage() {
        return mapImage;
    }
    public void setMapImage(BufferedImage mapImage) {
        this.mapImage = mapImage;
    }

    public ArrayList<int[]> getSolidBlocksLoc() {
        return solidBlocksLoc;
    }
    public void setSolidBlocksLoc(ArrayList<int[]> blockLocations) {
        this.solidBlocksLoc = blockLocations;
    }

    public ArrayList<int[]> getEnemySpotLoc() {
        return enemySpotLoc;
    }
    public void setEnemySpotLoc(ArrayList<int[]> enemySpotLoc) {
        this.enemySpotLoc = enemySpotLoc;
    }

    public ArrayList<int[]> getSoftBlocksLoc() {
        return softBlocksLoc;
    }
    public void setSoftBlocksLoc(ArrayList<int[]> softBlocksLoc) {
        this.softBlocksLoc = softBlocksLoc;
    }

    public ArrayList<int[]> getSpikeBlockLoc() {
        return spikeBlockLoc;
    }
    public void setSpikeBlockLoc(ArrayList<int[]> spikeBlockLoc) {
        this.spikeBlockLoc = spikeBlockLoc;
    }

    public ArrayList<int[]> getBarrelLoc() {
        return barrelLoc;
    }
    public void setBarrelLoc(ArrayList<int[]> barrelLoc) {
        this.barrelLoc = barrelLoc;
    }

    public ArrayList<int[]> getTempSpikeBlockLoc() {
        return tempSpikeBlockLoc;
    }
    public void setTempSpikeBlockLoc(ArrayList<int[]> tempSpikeBlockLoc) {
        this.tempSpikeBlockLoc = tempSpikeBlockLoc;
    }

    public ArrayList<int[]> getBoxLoc() {
        return boxLoc;
    }
    public void setBoxLoc(ArrayList<int[]> boxLoc) {
        this.boxLoc = boxLoc;
    }

    public ArrayList<int[]> getBushLoc() {
        return bushLoc;
    }
    public void setBushLoc(ArrayList<int[]> bushLoc) {
        this.bushLoc = bushLoc;
    }

    public ArrayList<int[]> getBonfireLoc() {
        return bonfireLoc;
    }
    public void setBonfireLoc(ArrayList<int[]> bonfireLoc) {
        this.bonfireLoc = bonfireLoc;
    }
}
