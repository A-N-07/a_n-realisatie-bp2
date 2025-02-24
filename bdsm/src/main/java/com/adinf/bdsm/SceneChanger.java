package com.adinf.bdsm;

import javafx.scene.Scene;
import javafx.stage.Stage;

public class SceneChanger {
    private StageManager stageManager;

    public SceneChanger(Stage stage) {
        this.stageManager = new StageManager(stage);
    }

    public void changeScene(Scene newScene) {
        this.stageManager.changeScene(newScene);  // Change the scene using StageManager
    }

    public StageManager getStageManager() {
        return stageManager;
    }

    public void setStageManager(StageManager stageManager) {
        this.stageManager = stageManager;
    }
}
