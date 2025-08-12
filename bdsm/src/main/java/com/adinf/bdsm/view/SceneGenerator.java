package com.adinf.bdsm.view;

import com.adinf.bdsm.util.SceneChanger;
import com.adinf.bdsm.model.Book;
import com.adinf.bdsm.model.BookListManager;
import com.adinf.bdsm.model.PhysicalBook;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.transformation.FilteredList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
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
    private String newScene;

    public SceneGenerator(Stage stage) {
        this.sceneChanger = new SceneChanger(stage);
        this.bookListManager = new BookListManager();
        this.newScene = "AllBooks";
        addBook();
        getSceneChanger().changeScene(generateScene(getNewScene()));

        System.out.println(getBookListManager().getTitle(0));
        System.out.println(getBookListManager().getTitle(1));
        System.out.println(getBookListManager().getTitle(2));
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

//        HBox rowZero = new HBox(10);
//        Label title = new Label("Title");
//        Label author = new Label("Author");
//        Label isbn = new Label("ISBN Nummer");
//        rowZero.getChildren().addAll(title, author, isbn);

//        center.getChildren().add(rowZero);
//        for (Book book : getBookListManager().getAllBooksList()){
//            HBox newRow = new HBox();
//            Label titleLabel = new Label(book.getTitle());
//            Label authorLabel = new Label(book.getAuthor());
//            Label isbnLabel = new Label(book.getIsbnNumber());
//            newRow.getChildren().addAll(titleLabel, authorLabel, isbnLabel);
//            center.getChildren().add(newRow);
//        }

        center.getChildren().add(generateBookList(getNewScene()));

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

    public GridPane createBookScene(){
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

        Button submitButton = new Button("Submit");

        submitButton.setOnAction(e -> {
            if (titleField.getText().isEmpty() || isbnField.getText().isEmpty() || locationField.getText().isEmpty() || authorField.getText().isEmpty()) {
                showAlert("Formulier Error", "De eerste vijf velden moeten allemaal ingevuld zijn om een boek aan te maken");
                //Ik moet nog zorgen dat het aangeeft welke velden er allemaal niet ingevuld zijn
            }
            if (bookTypeField.getValue() == null || bookStatusField.getValue() == null) {
                showAlert("Formulier Error", "Het boektype of boekstatus is nog niet ingevuld. Deze twee moeten allebei ingevuld zijn om een boek aant te maken");
            }
            if (!isbnField.getText().matches("\\d+") || isbnField.getText().length() != 13 ) {
                showAlert("Formulier Error", "Het Isbn-nummer moet alleen nummers bevatten en precies 13 nummers lang zijn. ");
            }
            if (!pageNumberField.getText().matches("\\d+")) {
                showAlert("Formulier Error", "Het paginanummer moet een positief getal zijn tussen de 0 en 9.999.999.");
            }
            else {
                showAlert("Success", "Boek succesvol aangemaakt!");
            }
        });

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
