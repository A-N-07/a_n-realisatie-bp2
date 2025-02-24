package com.adinf.bdsm;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {

        SceneGenerator sceneGenerator = new SceneGenerator(stage);

        stage.setTitle("BDSM");
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}