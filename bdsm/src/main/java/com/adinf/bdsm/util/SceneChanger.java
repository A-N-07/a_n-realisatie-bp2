package com.adinf.bdsm.util;

import javafx.scene.Scene;
import javafx.stage.Stage;

public class SceneChanger {
    private StageManager stageManager;

    public SceneChanger(Stage stage) {
        this.stageManager = new StageManager(stage);
    }

    public void changeScene(Scene newScene) {
        getStageManager().changeScene(newScene);
    }

    public StageManager getStageManager() {
        return stageManager;
    }

    public void setStageManager(StageManager stageManager) {
        this.stageManager = stageManager;
    }
}
