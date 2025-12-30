package com.adinf.bdsm;

import com.adinf.bdsm.view.SceneGenerator;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {
    @Override
    public void start(Stage stage) throws IOException {

        SceneGenerator sceneGenerator = new SceneGenerator(stage);

        stage.setTitle("BDSM");
        //stage.setScene(sceneGenerator.generateScene("All Books"));
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}