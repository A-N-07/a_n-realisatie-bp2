package com.adinf.bdsm;

public class PhysicalBook extends Book {

    private enum CoverType{
        HARDCOVER, SOFTCOVER;
    }

    private final String booktype = "Physical Book";
    private FileType fileType; // Deze field is optioneel
    private CoverType coverType;

    public PhysicalBook(BookStatus bookStatus,
                        String title,
                        String author,
                        String isbnNumber,
                        String coverLocation,
                        Boolean favourite,
                        Integer pageNumber,
                        String location,
                        FileType fileType,
                        CoverType coverType){
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
        this.coverType = coverType;
    }

    // Getters en Setters

    public String getBooktype() {
        return booktype;
    }

    public FileType getFileType() {
        return fileType;
    }

    public void setFileType(FileType fileType) {
        this.fileType = fileType;
    }

    public CoverType getCoverType() {
        return coverType;
    }

    public void setCoverType(CoverType coverType) {
        this.coverType = coverType;
    }
}