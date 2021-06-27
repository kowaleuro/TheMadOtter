package dev.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

import static dev.controller.MenuController.highScores;

public class HighScoreController {


    @FXML
    private Label scoreTitle;

    @FXML
    private Label nick1;

    @FXML
    private Label points1;

    @FXML
    private Label floor1;

    @FXML
    private Label nick2;

    @FXML
    private Label nick3;

    @FXML
    private Label nick4;

    @FXML
    private Label nick5;

    @FXML
    private Label nick6;

    @FXML
    private Label nick7;

    @FXML
    private Label nick8;

    @FXML
    private Label nick9;

    @FXML
    private Label nick10;

    @FXML
    private Label points2;

    @FXML
    private Label points3;

    @FXML
    private Label points4;

    @FXML
    private Label points5;

    @FXML
    private Label points6;

    @FXML
    private Label points7;

    @FXML
    private Label points8;

    @FXML
    private Label points9;

    @FXML
    private Label points10;

    @FXML
    private Label floor2;

    @FXML
    private Label floor3;

    @FXML
    private Label floor4;

    @FXML
    private Label floor5;

    @FXML
    private Label floor6;

    @FXML
    private Label floor7;

    @FXML
    private Label floor8;

    @FXML
    private Label floor9;

    @FXML
    private Label floor10;

    @FXML
    private Button cancelButton;

    @FXML
    private Button clearButton;

    ArrayList<Label> nicknames = new ArrayList<>();
    ArrayList<Label> points = new ArrayList<>();
    ArrayList<Label> floors = new ArrayList<>();


    public void initialize() {
        cancelButton.setOnAction(event -> {
            Stage stage = (Stage) cancelButton.getScene().getWindow();
            try {
                Pane root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/menu.fxml")));
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        clearButton.setOnAction(event -> clearHighScore());
        fillHighScore();
    }

    private void clearHighScore() {
        FileWriter writer;
        try {
            writer = new FileWriter("src/dev/controller/HighScore.txt");
            for (int i = 0; i < 10; i++) {
                writer.write("0:0:0" + System.lineSeparator());
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < highScores.size(); i++) {
            nicknames.get(i).setText("----------");
            points.get(i).setText("----------");
            floors.get(i).setText("--");
        }
    }

    private void fillHighScore() {
        nicknames.add(nick1);
        nicknames.add(nick2);
        nicknames.add(nick3);
        nicknames.add(nick4);
        nicknames.add(nick5);
        nicknames.add(nick6);
        nicknames.add(nick7);
        nicknames.add(nick8);
        nicknames.add(nick9);
        nicknames.add(nick10);

        points.add(points1);
        points.add(points2);
        points.add(points3);
        points.add(points4);
        points.add(points5);
        points.add(points6);
        points.add(points7);
        points.add(points8);
        points.add(points9);
        points.add(points10);

        floors.add(floor1);
        floors.add(floor2);
        floors.add(floor3);
        floors.add(floor4);
        floors.add(floor5);
        floors.add(floor6);
        floors.add(floor7);
        floors.add(floor8);
        floors.add(floor9);
        floors.add(floor10);

        for (int i = 0; i < highScores.size(); i++) {
            String[] row = highScores.get(i).split(":");
            if (!row[0].equals("0")) {
                nicknames.get(i).setText(row[0]);
                points.get(i).setText(row[1]);
                floors.get(i).setText(row[2]);
            }
        }
    }
}
