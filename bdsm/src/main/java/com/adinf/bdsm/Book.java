package com.adinf.bdsm;

import java.util.Optional;

public class Book {
    private BookStatus bookStatus;
    private String title;
    private String isbnNumber; // Deze field is optioneel
    private String author; // // Deze field is optioneel
    private String coverLocation; // Deze field is optioneel
    private Boolean favourite ; // Deze field heeft als default-waarde 'false'
    private Integer pageNumber; // Deze field is optioneel
    private String location;

    //Enums
    public enum BookStatus {
        READ, READING, STARTED_READING, UNREAD;
    }

    public enum FileType{
        PDF, EPUB, MOBI, TXT, MP3, M4B;
    }

    public enum BookType{
        E_BOOK, AUDIO_BOOK, PHYSICAL_BOOK, LUXURY_EDITION_BOOK;
    }



    // Getters en Setters
    public BookStatus getBookStatus() {
        return bookStatus;
    }

    public void setBookStatus(BookStatus bookStatus) {
        this.bookStatus = bookStatus;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIsbnNumber() {
        return isbnNumber;
    }

    public void setIsbnNumber(String isbnNumber) {this.isbnNumber = isbnNumber;}

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCoverLocation() {
        return coverLocation;
    }

    public void setCoverLocation(String coverLocation) {
        this.coverLocation = coverLocation;
    }

    public Boolean getFavourite() {
        return favourite;
    }

    public void setFavourite(Boolean favourite) {
        this.favourite = favourite;
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
