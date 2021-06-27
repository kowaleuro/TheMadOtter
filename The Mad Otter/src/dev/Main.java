package dev;

import dev.controller.LoseController;
import dev.controller.WinController;
import dev.statsPanel.HealthBar;
import dev.statsPanel.MiniMap;
import dev.statsPanel.Stats;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.hero.Hero;
import model.hero.HeroActions;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

import static dev.controller.MenuController.highScores;

public class Main extends Application {

    private static Hero hero;
    private InputManager inputManager;
    private ImageView gunReview;
    private MiniMap miniMap;
    private HealthBar healthBar;
    private Stats stats;
    private Label pauseLabel;
    private Stage stage;
    private Timeline timeline;
    public static String nick;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Pane root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/game.fxml")));
        primaryStage.setTitle("The Mad Otter");
        ImageView imageView = new ImageView(new Image("graphics/loading.png"));
        root.getChildren().add(imageView);

        primaryStage.setScene(new Scene(root));
        primaryStage.show();
        primaryStage.getScene().getStylesheets().addAll(Objects.requireNonNull(this.getClass().getResource("/fxml/stylesheet/game.css")).toExternalForm());
        primaryStage.setResizable(false);
        this.stage = primaryStage;
        new Thread(new Runnable() {
            @Override
            public void run() {
                    Platform.runLater(() -> {
                        try {
                            hero = new Hero(368, 568, root);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });




                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        inputManager = new InputManager(hero);
                        gunReview = new ImageView(hero.getActualGun().getImageView().getImage());
                        gunReview.relocate(900 - gunReview.getImage().getWidth()/2,700 + (62 - gunReview.getImage().getHeight())/2);
                        root.getChildren().add(gunReview);

                        pauseLabel = new Label();
                        pauseLabel.setText("Game Paused");
                        pauseLabel.setStyle("-fx-font: 25 forte");
                        pauseLabel.relocate(35, 35);
                        pauseLabel.setVisible(false);
                        root.getChildren().add(pauseLabel);
                        System.out.println("Done");
                        root.getChildren().remove(imageView);
                        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(25), e -> gameLoop()));
                        timeline.setCycleCount(Timeline.INDEFINITE);
                        timeline.play();
                        EventHandling.addEventHandlers(primaryStage.getScene());
                        EventHandling.getInputList().clear();
                        setTimeline(timeline);
                        setMiniMap(new MiniMap(hero));
                        setHealthBar(new HealthBar(hero));
                        setStats(new Stats(hero));
                        hero.setNickname(nick);
                    }
            });
            }
        }).start();


    }

    private void gameLoop() {
        inputManager.handlePause(pauseLabel);
        if (!hero.isPaused()) {
            inputManager.handlePlayerActions();
            inputManager.hero.updateHero();
            updateGun();
            miniMap.updateMiniMap();
            healthBar.updateHealthBar();
            stats.updateBasicStats();
            stats.updateAdditionalStats();
            ifGameEnded();
        }
    }

    private void updateGun() {
        if (hero.getActualGun().getImageView().getImage() != gunReview.getImage()) {
            gunReview.setImage(hero.getActualGun().getImageView().getImage());
            gunReview.relocate(900 - gunReview.getImage().getWidth()/2, 700 + (62 - gunReview.getImage().getHeight())/2);
        }
    }

    private void ifGameEnded() {
        if (!hero.isAlive())  {
            timeline.stop();
            typeScore(hero.getPoints());
            LoseController.pointsRef = hero.getPoints();
            LoseController.floorRef = hero.getFloor().getFloorId();
            LoseController.killsRef = hero.getKills();
            LoseController.killedByRef = hero.getWhoKills();
            try {
                Pane root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/lose.fxml")));
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else if (hero.isGameWin()) {
            hero.getFloor().setFloorId(5);
            timeline.stop();
            typeScore(hero.getPoints());
            WinController.pointsRef = hero.getPoints();
            WinController.floorRef = hero.getFloor().getFloorId();
            WinController.killsRef = hero.getKills();
            try {
                Pane root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/win.fxml")));
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void typeScore(int points){
        ArrayList<String> tempScoreList = new ArrayList<>();
        int max = 0;
        String tmp = null;
        for (String score : highScores) {
            if (max > 0) {
                tempScoreList.add(tmp);
                tmp = score;
            } else if (Integer.parseInt(score.split(":")[1]) < points){
                max = points;
                tempScoreList.add(hero.getNickname() + ":" + points + ":" + hero.getFloor().getFloorId());
                tmp = score;
            } else {
                tempScoreList.add(score);
            }
        }
        FileWriter writer;
        try {
            writer = new FileWriter("src/dev/controller/HighScore.txt");
            for (String str : tempScoreList) {
                writer.write(str + System.lineSeparator());
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public static void main(String[] args) {
        launch(args);
    }

    public void setGunReview(ImageView gunReview) {
        this.gunReview = gunReview;
    }

    public void setMiniMap(MiniMap miniMap) {
        this.miniMap = miniMap;
    }

    public void setHealthBar(HealthBar healthBar) {
        this.healthBar = healthBar;
    }

    public void setStats(Stats stats) {
        this.stats = stats;
    }

    public void setPauseLabel(Label pauseLabel) {
        this.pauseLabel = pauseLabel;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setTimeline(Timeline timeline) {
        this.timeline = timeline;
    }
}
