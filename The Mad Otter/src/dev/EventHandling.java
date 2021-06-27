package dev;

import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.util.ArrayList;

public class EventHandling {

    private static char lastKeyPressed;
    private static char lastKeyReleased;
    private static ArrayList<KeyCode> inputList = new ArrayList<>();


    public static void addEventHandlers(Scene scene) {
        KeyRelease keyRelease = new KeyRelease();
        KeyPressed keyPressed = new KeyPressed();
        scene.setOnKeyReleased(keyRelease);
        scene.setOnKeyPressed(keyPressed);
    }

    static class KeyRelease implements javafx.event.EventHandler<KeyEvent>{
        @Override
        public void handle(KeyEvent evt) {
            KeyCode code = evt.getCode();
            if (EventHandling.getInputList().contains(code)) {
                EventHandling.getInputList().remove(code);
            }
        }
    }

    static class KeyPressed implements javafx.event.EventHandler<KeyEvent>{
        @Override
        public void handle(KeyEvent evt) {
            KeyCode code = evt.getCode();
            if (!EventHandling.getInputList().contains(code)) {
                EventHandling.getInputList().add(code);
            }
        }
    }

    public static char getLastKeyPressed() { return lastKeyPressed; }
    public static void setLastKeyPressed(char lastKeyPressed) { EventHandling.lastKeyPressed = lastKeyPressed; }

    public static char getLastKeyReleased() { return lastKeyReleased; }
    public static void setLastKeyReleased(char lastKeyReleased) { EventHandling.lastKeyReleased = lastKeyReleased; }

    public static ArrayList<KeyCode> getInputList() { return inputList; }
    public static void setInputList(ArrayList<KeyCode> inputList) { EventHandling.inputList = inputList; }

}
