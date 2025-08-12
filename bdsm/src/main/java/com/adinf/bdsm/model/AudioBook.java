package com.adinf.bdsm.model;

public class AudioBook extends Book {
    //private final String booktype = "Audio Book";
    private FileType fileType; // Deze field is optioneel
    private String narrator;

    public AudioBook(BookStatus bookStatus,
                     String title,
                     String isbnNumber,
                     String author,
                     String coverLocation,
                     Boolean favourite,
                     Integer pageNumber,
                     String location,
                     FileType fileType,
                     String narrator) {
        super(BookType.AUDIO_BOOK);
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
        this.narrator = narrator;
    }

    // Getters en Setters

    public FileType getFileType() {
        return fileType;
    }

    public void setFileType(FileType fileType) {
        this.fileType = fileType;
    }

    public String getNarrator() {
        return narrator;
    }

    public void setNarrator(String narrator) {
        this.narrator = narrator;
    }
}
