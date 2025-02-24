package com.adinf.bdsm;

public class LuxuryEditionBook extends Book {
    private final String booktype = "Luxury Edition Book";
    private String specialFeature; // Deze field is optioneel

    public LuxuryEditionBook(BookStatus bookStatus,
                             String title,
                             String isbnNumber,
                             String author,
                             String coverLocation,
                             Boolean favourite,
                             Integer pageNumber,
                             String location,
                             String specialFeature) {
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
        this.specialFeature = specialFeature;
    }

    // Getters en Setters

    public String getBooktype() {
        return booktype;
    }

    public String getSpecialFeature() {
        return specialFeature;
    }

    public void setSpecialFeature(String specialFeature) {
        this.specialFeature = specialFeature;
    }
}
