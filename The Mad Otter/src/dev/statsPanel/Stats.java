package dev.statsPanel;

import javafx.animation.AnimationTimer;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import model.hero.Hero;
import model.item.guns.M16;
import model.item.guns.Shotgun;

import java.util.ArrayList;

public class Stats {

    private int actualFloor;
    private final Hero hero;
    private final Label infoLabel = new Label();
    private final Label moneyLabel = new Label();
    private final Label bombLabel = new Label();
    private final Label floorLabel = new Label();
    private final Label dmgLabel = new Label();
    private final Label rofLabel = new Label();
    private final Label timeLabel = new Label();
    private final Label pointLabel = new Label();
    private final ArrayList<Label> labels = new ArrayList<>();

    public Stats(Hero hero) {
        this.hero = hero;
        actualFloor = hero.getFloor().getFloorId();
        createBasicStats();
        createAdditionalStats();
        stopWatch();
    }

    public void createBasicStats() {
        labels.add(moneyLabel);
        labels.add(bombLabel);
        labels.add(floorLabel);
        labels.add(dmgLabel);
        labels.add(rofLabel);
        labels.add(timeLabel);
        labels.add(pointLabel);
        int y = 0;
        for (Label label : labels) {
            label.setTextFill(Color.rgb(131, 82, 73));
            label.setStyle("-fx-font: 25 forte");
            label.relocate(875, 295 + y);
            label.setTextAlignment(TextAlignment.LEFT);
            hero.getLayer().getChildren().add(label);
            y += 45;
        }
    }

    public void updateBasicStats() {
        moneyLabel.setText(hero.getMoney() + " $");
        bombLabel.setText(String.valueOf(hero.getBombs()));
        floorLabel.setText(String.valueOf(hero.getFloor().getFloorId()));
        dmgLabel.setText(String.valueOf(hero.getActualGun().getDmg()*hero.getDmgFactor()));
        rofLabel.setText(String.valueOf(getRof()));
        pointLabel.setText(String.valueOf(hero.getPoints()));

        changeFontColor();
    }

    public void createAdditionalStats() {
        infoLabel.setStyle("-fx-font: 16 arial");
        infoLabel.setTextFill(Color.WHITE);
        infoLabel.relocate(32, 32);
        hero.getLayer().getChildren().add(infoLabel);
    }

    public void updateAdditionalStats() {
        infoLabel.setVisible(hero.isAdditionalData());
        infoLabel.setText("Gun: " + hero.getActualGun().getGunName() +
                "\nGun Dmg: " + (hero.getActualGun().getDmg()*hero.getDmgFactor()) +
                "\nHow many guns: " + hero.getEquipment().size() +
                "\nGun coold.: " + hero.getActualGun().getCooldownShot() + " [ms]" +
                "\nBullet Vel: " + hero.getActualGun().getBulletVel() +
                "\nHP: " + hero.getRemainingHealth() +
                "\nMoney: " + hero.getMoney() + " $" +
                "\nBombs: " + hero.getBombs() +
                "\nClean Room: " + hero.getActualRoom().isClean() +
                "\nFloorID: " + hero.getFloor().getFloorId() +
                "\nRoomID: " + hero.getActualRoom().getRoomId() +
                "\nShooting: " + hero.isShooting() +
                "\nCurrent Action: " + hero.getCurrentAction().name() +
                "\nTrapdoor Open: " + hero.getFloor().getTrapdoor().isOpen() +
                "\nShop: " + hero.getActualRoom().isShop() +
                "\nHiding: " + hero.isHiding() +
                "\nPaused game: " + hero.isPaused() +
                "\nGodMode: " + hero.isGodmode() +
                "\nDmgFactor: " + hero.getDmgFactor());
    }

    private int getRof() {
        float rof;
        if (hero.getActualGun() instanceof Shotgun) {
            rof = (60/((float) hero.getActualGun().getCooldownShot()/1000))*((float) 10*hero.getActualGun().getDmg()*hero.getDmgFactor());
        } else if (hero.getActualGun() instanceof M16) {
            rof = (60/((float) hero.getActualGun().getCooldownShot()/1000))*((float) 3*hero.getActualGun().getDmg()*hero.getDmgFactor());
        } else {
            rof = (60/((float) hero.getActualGun().getCooldownShot()/1000))*((float) hero.getActualGun().getDmg()*hero.getDmgFactor());
        }
        rof = Math.round(rof);
        return (int) rof;
    }

    private void stopWatch() {
        timeLabel.setText("0:00");
        AnimationTimer timer = new AnimationTimer() {
            private long timestamp;
            private long time = 0;
            private long fraction = 0;

            @Override
            public void start() {
                timestamp = System.currentTimeMillis() - fraction;
                super.start();
            }

            @Override
            public void stop() {
                super.stop();
                fraction = System.currentTimeMillis() - timestamp;
            }

            @Override
            public void handle(long now) {
                long newTime = System.currentTimeMillis();
                if (timestamp + 1000 <= newTime) {
                    long deltaT = (newTime - timestamp) / 1000;
                    time += deltaT;
                    timestamp += 1000 * deltaT;
                    timeLabel.setText(String.format("%d:%02d", time / 60, time % 60));
                }
            }
        };
        timer.start();
        hero.setStopWatch(timer);
    }

    private void changeFontColor() {
        // zabezpieczenie, żeby to się wykonało tylko raz
        if (hero.getActualRoom().getActualFloor().getFloorId() == 2 && actualFloor != hero.getActualRoom().getActualFloor().getFloorId()) {
            for (Label label : labels) {
                label.setTextFill(Color.rgb(144, 144, 144));
            }
            actualFloor = hero.getActualRoom().getActualFloor().getFloorId();
        }
        if (hero.getActualRoom().getActualFloor().getFloorId() == 3 && actualFloor != hero.getActualRoom().getActualFloor().getFloorId()) {
            for (Label label : labels) {
                label.setTextFill(Color.rgb(167, 254, 255));
            }
            actualFloor = hero.getActualRoom().getActualFloor().getFloorId();
        }
        if (hero.getActualRoom().getActualFloor().getFloorId() == 4 && actualFloor != hero.getActualRoom().getActualFloor().getFloorId()) {
            for (Label label : labels) {
                label.setTextFill(Color.rgb(129, 72, 72));
            }
            actualFloor = hero.getActualRoom().getActualFloor().getFloorId();
        }
    }

}
