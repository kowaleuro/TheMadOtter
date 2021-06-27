package dev;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.Objects;

public class Menu extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Pane root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/menu.fxml")));
        Scene scene = new Scene(root);
        stage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/graphics/otterIcon.png"))));
        stage.setScene(scene);
        stage.setTitle("The Mad Otter");
        stage.show();
        stage.setResizable(false);
    }

}
