package com.adinf.bdsm;


import java.util.Optional;

public class Ebook extends Book{

    //fields
    private final String bookType = "E-book";
    private FileType fileType; // Deze field is optioneel

    public Ebook(BookStatus bookStatus,
                 String title,
                 String author,
                 String isbnNumber,
                 String coverLocation,
                 Boolean favourite,
                 Integer pageNumber,
                 String location,
                 FileType fileType
                 ) {
        setBookStatus(bookStatus);
        setTitle(title);
        setAuthor(author);
        setIsbnNumber(isbnNumber);
        setCoverLocation(coverLocation);
        setFavourite(favourite != null ? favourite : false);
        if (getBookStatus() == BookStatus.NOT_STARTED_READING) {
            setPageNumber(null);
        } else {setPageNumber(pageNumber);}
        setLocation(location);
        this.fileType = fileType;
    }

    // Getters en Setters

    public String getBookType() {
        return bookType;
    }

    public FileType getFileType() {
        return fileType;
    }

    public void setFileType(FileType fileType) {
        this.fileType = fileType;
    }
}
