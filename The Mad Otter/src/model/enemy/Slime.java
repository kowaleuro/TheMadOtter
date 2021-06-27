package model.enemy;

import javafx.scene.layout.Pane;

import java.util.ArrayList;

public class Slime extends Enemy {

    private boolean slimeKing = false;
    private boolean medium = false;
    private boolean small = false;
    private double parentPositionX;
    private double parentPositionY;

    public Slime(double x, double y, Pane layer, String name) {
        super(x, y, "/graphics/enemies/slime.gif", "/graphics/enemies/slime.gif", null, null, layer);
        setPoints(50);
        if(name.equals("SlimeKing")) {
            setFlying(false);
            setExplosive(false);
            setShooting(false);
            setRemainingHealth(60);
            setFollowing(true);
            setFollowingVel(2);
            setDmg(2);
            setSlimeKing(true);
        }
        if(name.equals("Medium")) {
            setFlying(false);
            setExplosive(false);
            setShooting(false);
            setRemainingHealth(30);
            setFollowing(false);
            setDmg(1);
            setMedium(true);
            addToLayer();
            setParentPositionX(parentPositionX);
            setParentPositionY(parentPositionY);
        }
        if(name.equals("Small")) {
            setFlying(false);
            setExplosive(false);
            setShooting(false);
            setRemainingHealth(15);
            setDmg(1);
            setSmall(true);
            addToLayer();
            setParentPositionX(parentPositionX);
            setParentPositionY(parentPositionY);
        }
    }

    public ArrayList<Slime> createMedium(double x, double y){
        ArrayList<Slime> mediumSlimes = new ArrayList<>();
        for (int i = 0; i<=3; i++){
            Slime medium = new Slime(x,y,getLayer(),"Medium");
            if (i == 0){
                medium.setVelY(0);
                medium.setVelX(4);
            } else if (i == 1){
                medium.setVelY(0);
                medium.setVelX(-4);
            } else if (i == 2){
                medium.setVelY(4);
                medium.setVelX(0);
            } else if (i == 3){
                medium.setVelY(-4);
                medium.setVelX(0);
            }
            mediumSlimes.add(medium);
        }

        return mediumSlimes;
    }

    public ArrayList<Slime> createSmall(double x, double y){
        ArrayList<Slime> mediumSlimes = new ArrayList<>();
        for (int i = 0; i<=3; i++){
            Slime medium = new Slime(x,y,getLayer(),"Small");
            if (i == 0){
                medium.setVelY(6);
                medium.setVelX(6);
            } else if(i == 1){
                medium.setVelY(-6);
                medium.setVelX(-6);
            } else if(i == 2){
                medium.setVelY(6);
                medium.setVelX(-6);
            } else if(i == 3){
                medium.setVelY(-6);
                medium.setVelX(6);
            }
            mediumSlimes.add(medium);
        }

        return mediumSlimes;
    }


    public boolean isSlimeKing() {
        return slimeKing;
    }
    public void setSlimeKing(boolean slimeKing) {
        this.slimeKing = slimeKing;
    }

    public boolean isMedium() {
        return medium;
    }
    public void setMedium(boolean medium) {
        this.medium = medium;
    }

    public boolean isSmall() {
        return small;
    }
    public void setSmall(boolean small) {
        this.small = small;
    }

    public double getParentPositionX() {
        return parentPositionX;
    }
    public void setParentPositionX(double parentPositionX) {
        this.parentPositionX = parentPositionX;
    }

    public double getParentPositionY() {
        return parentPositionY;
    }
    public void setParentPositionY(double parentPositionY) {
        this.parentPositionY = parentPositionY;
    }
}
