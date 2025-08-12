package com.adinf.bdsm.model;

import com.adinf.bdsm.controller.DatabaseController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class BookListManager {
    private ObservableList<Book> allBooksList;
    private DatabaseController databaseController;

    public BookListManager() {
        this.allBooksList = FXCollections.observableArrayList();
        this.databaseController = new DatabaseController();
    }

    public void deleteBook(String isbnNumber){
        allBooksList.removeIf(book -> book.getIsbnNumber().equals(isbnNumber));
        //remember to take what's inside deleteBookInDatabase put it here and delete that function for the actual project deleteBookInDatabase was made to test and deleteBook is made to work in the final product
    }

    public void deleteBookInDatabase(String isbnNumber){
        getDatabaseController().deleteBook(isbnNumber);
    }

    public void addBook(Book.BookType bookType,
                        Book.BookStatus bookStatus,
                        String title,
                        String isbnNumber,
                        String author,
                        String coverLocation,
                        Boolean favourite,
                        Integer pageNumber,
                        String location,
                        Book.FileType fileType,
                        String narrator,
                        PhysicalBook.CoverType coverType,
                        String specialFeature) {
        if (bookType == Book.BookType.E_BOOK) {
            Ebook newEbook = new Ebook(bookStatus, title, isbnNumber, author, coverLocation, favourite, pageNumber, location, fileType);
            allBooksList.add(newEbook);
        }
        else if (bookType == Book.BookType.AUDIO_BOOK) {
            AudioBook newAudioBook = new AudioBook(bookStatus, title, isbnNumber, author, coverLocation, favourite, pageNumber, location, fileType, narrator);
            allBooksList.add(newAudioBook);
        }

        else if (bookType == Book.BookType.PHYSICAL_BOOK){
            PhysicalBook newPhysicalBook = new PhysicalBook(bookStatus, title, isbnNumber, author, coverLocation, favourite, pageNumber, location, coverType);
            allBooksList.add(newPhysicalBook);
        }
        else {
            LuxuryEditionBook newLuxuryEditionBook = new LuxuryEditionBook(bookStatus, title, isbnNumber, author, coverLocation, favourite, pageNumber, location, specialFeature);
            allBooksList.add(newLuxuryEditionBook);
        }

        //getDatabaseController().addBook(bookType, bookStatus, title, isbnNumber, author, coverLocation, favourite, pageNumber, location, fileType, narrator, coverType, specialFeature);

    }

    public String getTitle(int index){
        return getBook(index).getTitle();
    }

    public Book getBook(int index) {
        return allBooksList.get(index);
    }

    public ObservableList<Book> getAllBooksList() {
        return allBooksList;
    }

    public void setAllBooksList(ObservableList<Book> allBooksList) {
        this.allBooksList = allBooksList;
    }

    public DatabaseController getDatabaseController() {
        return databaseController;
    }

    public void setDatabaseController(DatabaseController databaseController) {
        this.databaseController = databaseController;
    }
}
