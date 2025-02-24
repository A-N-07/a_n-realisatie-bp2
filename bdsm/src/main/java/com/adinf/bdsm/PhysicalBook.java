package com.adinf.bdsm;

public class PhysicalBook extends Book {

    public enum CoverType{
        HARDCOVER, SOFTCOVER;
    }

    private final String booktype = "Physical Book";
    private CoverType coverType;

    public PhysicalBook(BookStatus bookStatus,
                        String title,
                        String isbnNumber,
                        String author,
                        String coverLocation,
                        Boolean favourite,
                        Integer pageNumber,
                        String location,
                        CoverType coverType){
        setBookStatus(bookStatus);
        setTitle(title);
        setAuthor(author);
        setIsbnNumber(isbnNumber);
        setCoverLocation(coverLocation);
        setFavourite(favourite != null ? favourite : false);
        if (getBookStatus() == BookStatus.UNREAD) {
            setPageNumber(null);
        } else {setPageNumber(pageNumber);}
        setLocation(location);
        this.coverType = coverType;
    }

    // Getters en Setters

    public String getBooktype() {
        return booktype;
    }

    public CoverType getCoverType() {
        return coverType;
    }

    public void setCoverType(CoverType coverType) {
        this.coverType = coverType;
    }
}