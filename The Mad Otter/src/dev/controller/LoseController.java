package dev.controller;

import dev.Main;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.Objects;

public class LoseController {

    public static int pointsRef;
    public static int floorRef;
    public static int killsRef;
    public static String killedByRef;

    @FXML
    private Button restartButton;

    @FXML
    private Button exitButton;

    @FXML
    private Button menuButton;

    @FXML
    private Label pointsLabel;

    @FXML
    private Label floorLabel;

    @FXML
    private Label pointsTitle;

    @FXML
    private Label floorTitle;

    @FXML
    private Label enemyKilledTitle;

    @FXML
    private Label enemyKilledLabel;

    @FXML
    private Label killedByTitle;

    @FXML
    private Label killedByLabel;

    @FXML
    private Label title;


    public void initialize() {
        pointsLabel.setText(String.valueOf(pointsRef));
        floorLabel.setText(String.valueOf(floorRef));
        enemyKilledLabel.setText(String.valueOf(killsRef));
        killedByLabel.setText(String.valueOf(killedByRef));
        exitButton.setOnAction(event -> {
            try {
                Platform.exit();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        menuButton.setOnAction(event -> {
            Stage stage = (Stage) menuButton.getScene().getWindow();
            try {
                Pane root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/menu.fxml")));
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        restartButton.setOnAction(event -> {
            Main main = new Main();
            Stage stage = (Stage) restartButton.getScene().getWindow();
            try {
                main.start(stage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
