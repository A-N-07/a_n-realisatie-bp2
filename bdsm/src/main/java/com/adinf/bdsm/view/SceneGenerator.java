package com.adinf.bdsm.view;

import com.adinf.bdsm.controller.DatabaseController;
import com.adinf.bdsm.model.*;
import com.adinf.bdsm.util.SceneChanger;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.transformation.FilteredList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class SceneGenerator {

    private BookListManager bookListManager;
    private SceneChanger sceneChanger;
    private final String[] pageName = {"Create New Book", "All Books", "Favourite Books", "Currently Reading Books","Started Reading Books", "Unread Books", "Read Books" };
    private DatabaseController dbController;
    private String newScene;

    public SceneGenerator(Stage stage) {
        this.sceneChanger = new SceneChanger(stage);
        this.bookListManager = new BookListManager();
        this.dbController = new DatabaseController();
        this.newScene = "Create New Book";
        //addBook();
        getSceneChanger().changeScene(generateScene(getNewScene()));

//        System.out.println(getBookListManager().getTitle(0));
//        System.out.println(getBookListManager().getTitle(1));
//        System.out.println(getBookListManager().getTitle(2));
        //deleteBook();
    }

    public void addBook(){
        getBookListManager().addBook(Book.BookType.AUDIO_BOOK, Book.BookStatus.READ, "Moby Dicks", "1234567890123", "John Sjaak", "C:\\Users\\User_2\\Desktop", true, 420, "C:\\Users\\User_2\\Desktop", Book.FileType.MP3, "Yur Mom", null, null );
        getBookListManager().addBook(Book.BookType.E_BOOK, Book.BookStatus.UNREAD, "Catch These Hands", "1234567890124", "Jj Je moeder", "C:\\Users\\User_2\\Desktop", false, 69, "C:\\Users\\User_2\\Desktop", Book.FileType.PDF, null, null , null );
        getBookListManager().addBook(Book.BookType.PHYSICAL_BOOK, Book.BookStatus.STARTED_READING, "Holla @ Me", "1234567890125", "Mozart", "C:\\Users\\User_2\\Desktop", true, 40, "C:\\Users\\User_2\\Desktop", null, null, PhysicalBook.CoverType.HARDCOVER, null );
    }

    public void deleteBook(){
        getBookListManager().deleteBookInDatabase("1234567890123");
    }

    public Scene generateScene(String newScene){
        HBox topHbox = new HBox();
        topHbox.setStyle("-fx-background-color: lightblue;");
        topHbox.setMaxHeight(Double.MAX_VALUE);
        topHbox.setPadding(new Insets(10));
        topHbox.setAlignment(Pos.CENTER);
        topHbox.setSpacing(10);


        for (int i = 0; i < getPageNames().length; i++) {
            Button button = new Button(getPageName(i));
            button.setMaxWidth(Double.MAX_VALUE);
            button.prefWidthProperty().bind(button.heightProperty());
            button.setPrefHeight(100);
            button.setMinHeight(50);
            HBox.setHgrow(button, Priority.ALWAYS);

            final int index = i;
            button.setOnAction(event -> handleButtonClick(index));

            topHbox.getChildren().add(button);
        }

        HBox bottomHBox = new HBox(10);
        bottomHBox.setStyle("-fx-background-color: lightgray;");

        Region left = new Region();
        left.setStyle("-fx-background-color: lightcoral;");
        StackPane center = new StackPane();
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

        if (newScene.equals("Create New Book")) {
            center.getChildren().add(createBookPane());
        }
        else {
            center.getChildren().add(generateBookList(getNewScene()));
        }

        bottomHBox.getChildren().addAll(left, center, right);

        // Root VBox
        VBox root = new VBox(10);
        root.getChildren().addAll(topHbox, bottomHBox);
        VBox.setVgrow(bottomHBox, Priority.ALWAYS);
        VBox.setVgrow(topHbox, Priority.ALWAYS);
        topHbox.prefHeightProperty().bind(root.heightProperty().multiply(0.15));
        bottomHBox.prefHeightProperty().bind(root.heightProperty().multiply(0.85));

        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        return new Scene(root, screenBounds.getWidth(), screenBounds.getHeight());
    }

    private void handleButtonClick(int index) {
        setNewScene(getPageName(index));
        sceneChanger.changeScene(generateScene(getNewScene()));
    }


    public TableView generateBookList(String newScene){

        TableView<Book> bookList = new TableView<>();

        TableColumn<Book, String> titleColumn = new TableColumn<>("Title");
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));

        TableColumn<Book, String> isbnColumn = new TableColumn<>("ISBN-nummer");
        isbnColumn.setCellValueFactory(new PropertyValueFactory<>("isbnNumber"));

        TableColumn<Book, String> bookTypeColumn = new TableColumn<>("Boek Type");
        bookTypeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getBookType().toString()));

        bookList.getColumns().addAll(titleColumn, isbnColumn, bookTypeColumn);

        new FilteredList<>(getBookListManager().getAllBooksList(), book -> false);
        FilteredList<Book> filteredBooks = switch (newScene) {
            case "All Books" ->
                    new FilteredList<>(getBookListManager().getAllBooksList(), book -> true);
            case "Favourite Books" ->
                    //filteredBooks = new FilteredList<>(getBookListManager().getAllBooksList(), book -> book.getFavourite() == true);
                    new FilteredList<>(getBookListManager().getAllBooksList(), Book::getFavourite);
            case "Currently Reading Books" ->
                    new FilteredList<>(getBookListManager().getAllBooksList(), book -> book.getBookStatus().equals(Book.BookStatus.READING));
            case "Started Reading Books" ->
                    new FilteredList<>(getBookListManager().getAllBooksList(), book -> book.getBookStatus().equals(Book.BookStatus.STARTED_READING));
            case "Unread Books" ->
                    new FilteredList<>(getBookListManager().getAllBooksList(), book -> book.getBookStatus().equals(Book.BookStatus.UNREAD));
            case "Read Books" ->
                    new FilteredList<>(getBookListManager().getAllBooksList(), book -> book.getBookStatus().equals(Book.BookStatus.READ));
            default ->
                    new FilteredList<>(getBookListManager().getAllBooksList(), book -> false); // Show nothing if no match
        };

        bookList.setItems(filteredBooks);
        return bookList;
    }

    public GridPane createBookPane(){
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10));
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setAlignment(Pos.CENTER);

        // Form Velden
        Label titleLabel = new Label("Titel");
        TextField titleField = new TextField();

        Label isbnLabel = new Label("ISBN-nummer:");
        TextField isbnField = new TextField();

        Label bookTypeLabel = new Label("Boek Type:");
        ComboBox<Book.BookType> bookTypeField = new ComboBox<>();
        bookTypeField.getItems().addAll(Book.BookType.values());
        bookTypeField.setPromptText("Selecteer uw boektype!");

        Label bookStatusLabel = new Label("Boek Status:");
        ComboBox<Book.BookStatus> bookStatusField = new ComboBox<>();
        bookStatusField.getItems().addAll(Book.BookStatus.values());
        bookStatusField.setPromptText("Selecteer uw boek status!");

        Label locationLabel = new Label("Locatie");
        TextField locationField = new TextField();

        Label authorLabel = new Label("Auteur");
        TextField authorField = new TextField();

        // Radio button
        Label favouriteLabel = new Label("Favourite?");
        ToggleGroup group = new ToggleGroup();
        RadioButton yesButton = new RadioButton("Yes");
        yesButton.setToggleGroup(group);
        RadioButton noButton = new RadioButton("No");
        noButton.setToggleGroup(group);
        noButton.setSelected(true);
        Label yesLabel = new Label();
        Label noLabel = new Label();
        HBox favouriteField = new HBox();
        favouriteField.getChildren().addAll(yesLabel,yesButton, noLabel, noButton);

        Label pageNumberLabel = new Label("Pagina Nummer");
        TextField pageNumberField = new TextField("0");

        Label coverLocationLabel= new Label("Boekenkaft Locatie");
        TextField coverLocationField = new TextField();

        Label coverTypeLabel= new Label("Boekenkaft Type");
        ComboBox<PhysicalBook.CoverType> coverTypeField = new ComboBox<>();
        coverTypeField.getItems().addAll(PhysicalBook.CoverType.values());
        coverTypeField.setPromptText("Selecteer uw covertype!");

        Label narratorLabel = new Label("Verteller");
        TextField narratorField = new TextField();

        Label fileTypeLabel = new Label("Bestandstype");
        ComboBox<Book.FileType> fileTypeField = new ComboBox<>();
        fileTypeField.getItems().addAll(Book.FileType.values());

        Label specialFeatureLabel= new Label("Speciale Toevoeging");
        TextField specialFeatureField = new TextField();

        Button submitButton = new Button("Submit");

        submitButton.setOnAction(e -> {
            if (titleField.getText().isEmpty() || isbnField.getText().isEmpty() || locationField.getText().isEmpty() || authorField.getText().isEmpty()) {
                showAlert("Formulier Error", "De eerste vijf velden moeten allemaal ingevuld zijn om een boek aan te maken");
                //Ik moet nog zorgen dat het aangeeft welke velden er allemaal niet ingevuld zijn
            }
            else if (bookTypeField.getValue() == null || bookStatusField.getValue() == null) {
                showAlert("Formulier Error", "Het boektype of boekstatus is nog niet ingevuld. Deze twee moeten allebei ingevuld zijn om een boek aant te maken");
            }
            else if (!isbnField.getText().matches("\\d+") || isbnField.getText().length() != 13 ) {
                showAlert("Formulier Error", "Het Isbn-nummer moet alleen nummers bevatten en precies 13 nummers lang zijn. ");
            }
            else if (!pageNumberField.getText().matches("\\d+")) {
                showAlert("Formulier Error", "Het paginanummer moet een positief getal zijn tussen de 0 en 9.999.999.");
            }
            else {
                showAlert("Success", "Boek succesvol aangemaakt!");
                // put here the functions to actually add the book you bafoon
                Book.BookType bookStatus = bookTypeField.getValue();
                Book newBook = switch(bookStatus) {
                    case E_BOOK -> new Ebook(bookStatusField.getValue(), titleField.getText(), isbnField.getText(), authorField.getText(), coverLocationField.getText(), yesButton.isSelected(), Integer.valueOf(pageNumberField.getText()), locationField.getText(), fileTypeField.getValue());
                    case AUDIO_BOOK -> new AudioBook(bookStatusField.getValue(), titleField.getText(), isbnField.getText(), authorField.getText(), coverLocationField.getText(), yesButton.isSelected(), Integer.valueOf(pageNumberField.getText()), locationField.getText(), fileTypeField.getValue(), narratorField.getText());
                    case PHYSICAL_BOOK -> new PhysicalBook(bookStatusField.getValue(), titleField.getText(), isbnField.getText(), authorField.getText(), coverLocationField.getText(), yesButton.isSelected(), Integer.valueOf(pageNumberField.getText()), locationField.getText(), coverTypeField.getValue());
                    case LUXURY_EDITION_BOOK -> new LuxuryEditionBook(bookStatusField.getValue(), titleField.getText(), isbnField.getText(), authorField.getText(), coverLocationField.getText(), yesButton.isSelected(), Integer.valueOf(pageNumberField.getText()), locationField.getText(), specialFeatureField.getText());
                };

                // make function for to add narrator and other subtables in the db and check if exists and add here

                this.bookListManager.getAllBooksList().add(newBook);

                this.dbController.addBook(bookTypeField.getValue(), bookStatusField.getValue(), titleField.getText(), isbnField.getText(), authorField.getText(), coverLocationField.getText(), yesButton.isSelected(), Integer.valueOf(pageNumberField.getText()), locationField.getText(), fileTypeField.getValue(), narratorField.getText(), coverTypeField.getValue(), specialFeatureField.getText()) ;

                for (Node node : gridPane.getChildren()) {
                    if (node instanceof TextField) {
                        TextField tf = (TextField) node;
                        tf.setText("");
                    }
                }
            }
        });

        gridPane.add(titleLabel, 0, 0);
        gridPane.add(titleField, 1, 0);

        gridPane.add(isbnLabel, 0, 1);
        gridPane.add(isbnField, 1, 1);

        gridPane.add(bookTypeLabel, 0, 2);
        gridPane.add(bookTypeField, 1, 2);

        gridPane.add(bookStatusLabel, 0, 3);
        gridPane.add(bookStatusField, 1, 3);

        gridPane.add(locationLabel, 0, 4);
        gridPane.add(locationField, 1, 4);

        gridPane.add(authorLabel, 0, 5);
        gridPane.add(authorField, 1, 5);

        gridPane.add(favouriteLabel, 0, 6);
        gridPane.add(favouriteField, 1, 6);

        gridPane.add(pageNumberLabel, 0, 7);
        gridPane.add(pageNumberField, 1, 7);

        gridPane.add(coverLocationLabel, 0, 8);
        gridPane.add(coverLocationField, 1, 8);

        gridPane.add(coverTypeLabel, 0, 9);
        gridPane.add(coverTypeField, 1, 9);

        gridPane.add(narratorLabel, 0, 10);
        gridPane.add(narratorField, 1, 10);

        gridPane.add(fileTypeLabel, 0, 11);
        gridPane.add(fileTypeField, 1, 11);

        gridPane.add(specialFeatureLabel, 0, 12);
        gridPane.add(specialFeatureField, 1, 12);

        gridPane.add(submitButton, 1, 13);

        return gridPane;
    }

    private GridPane EditBookPane(){
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10));
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setAlignment(Pos.CENTER);

        // Form Velden
        Label isbnLabel = new Label("ISBN-nummer:");
        TextField isbnField = new TextField();

        Label titleLabel = new Label("Titel");
        TextField titleField = new TextField();

        Label bookTypeLabel = new Label("Boek Type:");
        ComboBox<Book.BookType> bookTypeField = new ComboBox<>();
        bookTypeField.getItems().addAll(Book.BookType.values());
        bookTypeField.setPromptText("Selecteer uw boektype!");

        Label bookStatusLabel = new Label("Boek Status:");
        ComboBox<Book.BookStatus> bookStatusField = new ComboBox<>();
        bookStatusField.getItems().addAll(Book.BookStatus.values());
        bookTypeField.setPromptText("Selecteer uw boek status!");

        Label locationLabel = new Label("Locatie");
        TextField locationField = new TextField();

        Label authorLabel = new Label("Auteur");
        TextField authorField = new TextField();

        // Radio button
        Label favouriteLabel = new Label("Favourite?");
        ToggleGroup group = new ToggleGroup();
        RadioButton yesButton = new RadioButton("Yes");
        yesButton.setToggleGroup(group);
        RadioButton noButton = new RadioButton("No");
        noButton.setToggleGroup(group);
        noButton.setSelected(true);
        Label yesLabel = new Label();
        Label noLabel = new Label();
        HBox favouriteField = new HBox();
        favouriteField.getChildren().addAll(yesLabel,yesButton, noLabel, noButton);

        Label pageNumberLabel = new Label("Pagina Nummer");
        TextField pageNumberField = new TextField("0");

        Label coverLocationLabel= new Label("Boekenkaft Locatie");
        TextField coverLocationField = new TextField();

        Label coverTypeLabel= new Label("Boekenkaft Type");
        TextField coverTypeField = new TextField();

        Label narratorLabel = new Label("Verteller");
        TextField narratorField = new TextField();

        Label fileTypeLabel = new Label("Bestandstype");
        TextField fileTypeField = new TextField();

        Label specialFeatureLabel= new Label("Speciale Toevoeging");
        TextField specialFeatureField = new TextField();

        Button submitButton = new Button("Submit Changes");

        submitButton.setOnAction(e -> {
            for (Book book : getBookListManager().getAllBooksList()) {
                if (book.getIsbnNumber().equals(isbnField.getText())) {
                    // Found the book to edit
                    // Update the book's properties based on the form fields
                    if (!titleField.getText().isEmpty()) {
                        book.setTitle(titleField.getText());
                    }

                    // Update other fields as necessary

                    showAlert("Success", "Boek succesvol bijgewerkt!");
                    for (Node node : gridPane.getChildren()) {
                        if (node instanceof TextField) {
                            TextField tf = (TextField) node;
                            tf.setText("");
                        }
                    }
                    return;
                }
            }




            //            if (titleField.getText().isEmpty() || isbnField.getText().isEmpty() || locationField.getText().isEmpty() || authorField.getText().isEmpty()) {
//                showAlert("Formulier Error", "De eerste vijf velden moeten allemaal ingevuld zijn om een boek aan te maken");
//                //Ik moet nog zorgen dat het aangeeft welke velden er allemaal niet ingevuld zijn
//            }
//            if (bookTypeField.getValue() == null || bookStatusField.getValue() == null) {
//                showAlert("Formulier Error", "Het boektype of boekstatus is nog niet ingevuld. Deze twee moeten allebei ingevuld zijn om een boek aant te maken");
//            }
//            if (!isbnField.getText().matches("\\d+") || isbnField.getText().length() != 13 ) {
//                showAlert("Formulier Error", "Het Isbn-nummer moet alleen nummers bevatten en precies 13 nummers lang zijn. ");
//            }
//            if (!pageNumberField.getText().matches("\\d+")) {
//                showAlert("Formulier Error", "Het paginanummer moet een positief getal zijn tussen de 0 en 9.999.999.");
//            }
//            else {
//                showAlert("Success", "Boek succesvol aangemaakt!");
//            }
        });

        gridPane.add(isbnLabel, 0, 0);
        gridPane.add(isbnField, 1, 0);

        gridPane.add(titleLabel, 0, 1);
        gridPane.add(titleField, 1, 1);

        gridPane.add(bookTypeLabel, 0, 2);
        gridPane.add(bookTypeField, 1, 2);

        gridPane.add(bookStatusLabel, 0, 3);
        gridPane.add(bookStatusField, 1, 3);

        gridPane.add(locationLabel, 0, 4);
        gridPane.add(locationField, 1, 4);

        gridPane.add(authorLabel, 0, 5);
        gridPane.add(authorField, 1, 5);

        gridPane.add(favouriteLabel, 0, 6);
        gridPane.add(favouriteField, 1, 6);

        gridPane.add(pageNumberLabel, 0, 7);
        gridPane.add(pageNumberField, 1, 7);

        gridPane.add(coverLocationLabel, 0, 8);
        gridPane.add(coverLocationField, 1, 8);

        gridPane.add(coverTypeLabel, 0, 9);
        gridPane.add(coverTypeField, 1, 9);

        gridPane.add(narratorLabel, 0, 10);
        gridPane.add(narratorField, 1, 10);

        gridPane.add(fileTypeLabel, 0, 11);
        gridPane.add(fileTypeField, 1, 11);

        gridPane.add(specialFeatureLabel, 0, 12);
        gridPane.add(specialFeatureField, 1, 12);

        gridPane.add(submitButton, 1, 13);

        return gridPane;
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Getters en Setters
    public DatabaseController getDbController() { return dbController;}

    public void setDbController(DatabaseController dbController) { this.dbController = dbController;}

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

    public String getPageName(int index) {
        return this.pageName[index];
    }

    public String[] getPageNames() {
        return this.pageName;
    }

    public String[] getPageName() {
        return pageName;
    }

    public String getNewScene() {
        return newScene;
    }

    public void setNewScene(String newScene) {
        this.newScene = newScene;
    }
}
