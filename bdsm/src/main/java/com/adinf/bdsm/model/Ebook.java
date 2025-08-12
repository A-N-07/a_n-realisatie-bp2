package com.adinf.bdsm.model;


public class Ebook extends Book {

    //fields
    //private final String bookType = "E-book";
    private FileType fileType; // Deze field is optioneel

    public Ebook(BookStatus bookStatus,
                 String title,
                 String isbnNumber,
                 String author,
                 String coverLocation,
                 Boolean favourite,
                 Integer pageNumber,
                 String location,
                 FileType fileType
                 ) {
        super(BookType.E_BOOK);

        setBookStatus(bookStatus);

        setTitle(title);

        setAuthor(author);

        setIsbnNumber(isbnNumber);

        setCoverLocation(coverLocation);

        setFavourite(favourite != null ? favourite : false);

        if (getBookStatus() == BookStatus.UNREAD) {
            setPageNumber(0);
        } else {setPageNumber(pageNumber != null ? pageNumber : 0);}

        setLocation(location);

        this.fileType = fileType;
    }

    // Getters en Setters

    public FileType getFileType() {
        return fileType;
    }

    public void setFileType(FileType fileType) {
        this.fileType = fileType;
    }
}
