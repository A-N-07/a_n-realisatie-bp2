package com.adinf.bdsm;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.Random;

public class SceneGenerator {

    private BookListManager bookListManager;
    private SceneChanger sceneChanger;

    public SceneGenerator(Stage stage) {

        stage.setFullScreen(true);
        this.sceneChanger = new SceneChanger(stage);
        this.bookListManager = new BookListManager();
        getSceneChanger().changeScene(generateAllbooksScene());
        //addBook();
        //deleteBook();
    }

    public void addBook(){
        getBookListManager().addBook(Book.BookType.AUDIO_BOOK, Book.BookStatus.READ, "Moby Dicks", "1234567890123", "John Sjaak", "C:\\Users\\User_2\\Desktop", true, 12, "C:\\Users\\User_2\\Desktop", Book.FileType.MP3, "Yur Mom", null, null );
    }

    public void deleteBook(){
        getBookListManager().deleteBookInDatabase("1234567890123");
    }

    public Scene generateAllbooksScene(){
        // Top HBox (full width)
        HBox topHbox = new HBox();
        topHbox.setStyle("-fx-background-color: lightblue;"); // Just for visualization
        topHbox.setMaxHeight(Double.MAX_VALUE);
        topHbox.setPadding(new javafx.geometry.Insets(10));
        topHbox.setAlignment(Pos.CENTER);
        topHbox.setSpacing(10);

        for (int i = 1; i <= 6; i++) {
            Button button = new Button("Button " + i);
            button.setMaxWidth(Double.MAX_VALUE); // Allow button to expand
            button.prefWidthProperty().bind(button.heightProperty());
            button.setPrefHeight(100);
            button.setMinHeight(50);
            HBox.setHgrow(button, Priority.ALWAYS); // Allow even growth
            topHbox.getChildren().add(button); // Add button to HBox
        }

        HBox bottomHBox = new HBox(10); // 10px spacing between elements
        bottomHBox.setStyle("-fx-background-color: lightgray;");

        Region left = new Region();
        left.setStyle("-fx-background-color: lightcoral;");
        Region center = new Region();
        center.setStyle("-fx-background-color: lightgreen;");
        Region right = new Region();
        right.setStyle("-fx-background-color: lightgoldenrodyellow;");

        HBox.setHgrow(left, Priority.ALWAYS);
        HBox.setHgrow(center, Priority.ALWAYS);
        HBox.setHgrow(right, Priority.ALWAYS);
        left.setMaxWidth(Double.MAX_VALUE);
        center.setMaxWidth(Double.MAX_VALUE);
        right.setMaxWidth(Double.MAX_VALUE);
        left.prefWidthProperty().bind(bottomHBox.widthProperty().divide(4));
        center.prefWidthProperty().bind(bottomHBox.widthProperty().divide(2));
        right.prefWidthProperty().bind(bottomHBox.widthProperty().divide(4));


        // Add regions to the bottom HBox
        bottomHBox.getChildren().addAll(left, center, right);

        // Root VBox
        VBox root = new VBox(10); // 10px spacing
        root.getChildren().addAll(topHbox, bottomHBox);
        VBox.setVgrow(bottomHBox, Priority.ALWAYS);
        VBox.setVgrow(topHbox, Priority.ALWAYS);
        topHbox.prefHeightProperty().bind(root.heightProperty().multiply(0.15));
        bottomHBox.prefHeightProperty().bind(root.heightProperty().multiply(0.85));


        // Return the scene
        return new Scene(root, 600, 400);
    }

    public Scene generateExampleScene(){

        // Create a label
        Label randomLabel = new Label("Press the button!");

        // Create a button
        Button randomButton = new Button("Change Text and Position");

        // Random number generator
        Random random = new Random();

        // Button action: change label text and position
        randomButton.setOnAction(e -> {
            // Change label text to a random number
            randomLabel.setText("Random Number: " + random.nextInt(100));

            // Change label position randomly on the scene
            randomLabel.setTranslateX(random.nextInt(400)); // Random X position
            randomLabel.setTranslateY(random.nextInt(300)); // Random Y position
        });

        // Set up the layout (VBox) and add the button and label
        VBox vbox = new VBox(20);
        vbox.getChildren().addAll(randomButton, randomLabel);

        // StackPane for center alignment
        StackPane root = new StackPane();
        root.getChildren().add(vbox);

        // Return the scene
        return new Scene(root, 600, 400);
    }

    public BookListManager getBookListManager() {
        return bookListManager;
    }

    public void setBookListManager(BookListManager bookListManager) {
        this.bookListManager = bookListManager;
    }

    public SceneChanger getSceneChanger() {
        return sceneChanger;
    }

    public void setSceneChanger(SceneChanger sceneChanger) {
        this.sceneChanger = sceneChanger;
    }
}
