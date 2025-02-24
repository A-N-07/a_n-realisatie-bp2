package com.adinf.bdsm;

import javafx.scene.Scene;
import javafx.stage.Stage;

public class StageManager {

    private Stage stage;

    public StageManager(Stage stage) {
        this.stage = stage;
    }

    public void changeScene(Scene scene) {
        this.stage.setScene(scene);
    }
}
