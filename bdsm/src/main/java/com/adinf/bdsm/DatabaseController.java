package com.adinf.bdsm;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseController {
    Connection con;

    //Dit zijn queries om relaties toe te voegen tussen de main table en subtables voor een boek
    // Deze wordt door addbook() en updateBook() gebruikt
    static String[] updateQueries = {
            "UPDATE book SET narrator_id = (SELECT id FROM person WHERE name = ?) WHERE isbn_number =?",
            "UPDATE book SET cover_location_id = (SELECT id FROM cover_location WHERE `value` = ?) WHERE isbn_number =?",
            "UPDATE book SET book_location_id = (SELECT id FROM book_location WHERE `value` = ?) WHERE isbn_number =?",
            "UPDATE book SET file_type_id = (SELECT id FROM file_type WHERE `value` = ?) WHERE isbn_number =?",
            "UPDATE book SET cover_type_id = (SELECT id FROM cover_type WHERE `value` = ?) WHERE isbn_number =?",
            "UPDATE book SET author_id = (SELECT id FROM person WHERE name = ?) WHERE isbn_number =?",
    };

    public DatabaseController() {
        try{
            this.con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bdsm_db", "root", "");
            System.out.println("Connection successful!");

        } catch (SQLException e){
            System.err.println("Connection failed: " + e.getMessage());
        }
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
        //Deze functie voegt een boek toe. Door eerst een boek toe te voegen in de book table zonder foreign keys. (Alle foreign key kolommen zijn dus null)
        // En daarna gaat de functie kijken welke columns met foreign keys geüpdate moet worden. En UPDATE ze.
        // !!NOTE!! Deze functie gaat ervan uit dat de waardes van het boek die in sub-tables moeten al toegevoegd zijn in de Front End.
        // Bijvoorbeeld: Je slaat een boek op met daarin een author. Dan wordt meteen bij het opslaan de author toegevoegd aan de sub-table 'person'.
        // En wanneer alle sub-table waardes die nog niet bestaan toegevoegd zijn aan zijn specifieke sub-table, word pas deze functie aangeroepen om een boek toe te voegen.
        // Deze volgorde in de Front End is extreem belangrijk anders werkt deze functie niet. En dus de applicatie niet.

        String sql = """
            INSERT INTO book (
                title, isbn_number, special_feature, page_number, favourite,
                narrator_id, cover_location_id, file_type_id, cover_type_id, author_id,
                book_location_id, book_status_id, book_type_id
            )
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, 
                (SELECT id FROM book_location WHERE value = ?),
                (SELECT id FROM book_status WHERE value = ?),
                (SELECT id FROM book_type WHERE value = ?));
        """;

        // Hier wordt eerst een boek toegevoegd zonder alle foreign keys, behalve foreign keys van kolommen die verplicht zijn in dit geval bookStatus en booktype (kan dus nooit null zijn)
        try(PreparedStatement pstmt = getCon().prepareStatement(sql)){
            pstmt.setString(1, title);
            pstmt.setString(2, isbnNumber);
            pstmt.setString(3, specialFeature);
            pstmt.setInt(4, pageNumber);
            pstmt.setBoolean(5, favourite);
            pstmt.setNull(6, java.sql.Types.VARCHAR);
            pstmt.setNull(7, java.sql.Types.VARCHAR);
            pstmt.setNull(8, java.sql.Types.VARCHAR);
            pstmt.setNull(9, java.sql.Types.VARCHAR);
            pstmt.setNull(10, java.sql.Types.VARCHAR);
            pstmt.setString(11, location);
            pstmt.setString(12, String.valueOf(bookStatus));
            pstmt.setString(13, String.valueOf(bookType));
            pstmt.executeUpdate();
        }catch (SQLException e) {
            e.printStackTrace();
        }

        // Dit is een array met de waardes van sub-tables die bij dit boek hoort (meegegeven in parameter van de funtie) om te gebruiken voor zoeken naar het id van deze waardes.
        // Maar sommigen kunnen null zijn
        String[] foreignKeyInput = {narrator, coverLocation, location, String.valueOf(fileType), String.valueOf(coverType), author};

        // Update elke foreign key kolom van het boek op basis van de meegegeven parameter die niet null is
        for (int i = 0; i < foreignKeyInput.length; i++) {
            if (foreignKeyInput[i] !=null) {
                try(PreparedStatement pstmt = getCon().prepareStatement(updateQueries[i])){
                    pstmt.setString(1, foreignKeyInput[i]);
                    pstmt.setString(2, isbnNumber);
                    pstmt.executeUpdate();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
        }
    }

    // Delete een row van table 'book' op basis van het isbn-nummer
    // Note waardes uit sub-tables worden niet verwijderd omdat bijvoorbeeld een author meerdere boeken geschreven kunnen hebben
    public void deleteBook(String isbnNumber) {
        String sqlDeleteBook = "DELETE FROM book WHERE isbn_number = ?";

        try(PreparedStatement pstmt = getCon().prepareStatement(sqlDeleteBook)){
            pstmt.setString(1, isbnNumber);
            pstmt.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void updateBook(Book.BookType bookType,
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
                           String specialFeature){
        // updateBook werkt hetzelfde als het aanmaken van een boek het doet alleen niet INSERT, maar UPDATE
        // Het update alle waardes van het boek, maar het zet de Foreign Key kolommen eerst op null en gaat daarna apart uitzoeken welke Foreign Key kolom een Foreign Key moet hebben

        String strUpdateQuery = """
                UPDATE book 
                SET title = ?, isbn_number = ?, special_feature = ?, page_number = ?, favourite = ?,
                    narrator_id = ?, cover_location_id = ?, file_type_id = ?, cover_type_id = ?, author_id = ?,
                    book_location_id = ?, book_status_id = ?, book_type_id = ?
                WHERE isbn_number = ?
                """;
        try(PreparedStatement pstmt = getCon().prepareStatement(strUpdateQuery)){
            pstmt.setString(1, title);
            pstmt.setString(2, isbnNumber);
            pstmt.setString(3, specialFeature);
            pstmt.setInt(4, pageNumber);
            pstmt.setBoolean(5, favourite);
            pstmt.setNull(6, java.sql.Types.INTEGER);
            pstmt.setNull(7, java.sql.Types.INTEGER);
            pstmt.setNull(8, java.sql.Types.INTEGER);
            pstmt.setNull(9, java.sql.Types.INTEGER);
            pstmt.setNull(10, java.sql.Types.INTEGER);
            pstmt.setString(11, location);
            pstmt.setString(12, String.valueOf(bookStatus));
            pstmt.setString(13, String.valueOf(bookType));
            pstmt.setString(14, isbnNumber);
            pstmt.executeUpdate();
        }catch (SQLException e) {
            e.printStackTrace();
        }

        String[] updateInput = {narrator, coverLocation, location, String.valueOf(fileType), String.valueOf(coverType), author};

        for (int i = 0; i < updateInput.length; i++) {
            try(PreparedStatement pstmt = getCon().prepareStatement(updateQueries[i])){
                pstmt.setString(1, updateInput[i]);
                pstmt.setString(2, isbnNumber);
                pstmt.executeUpdate();
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
    }

    public ArrayList<Book> retrieveBooks() {

        //RetrieveBooks haalt boek voor boek de informatie om een boek te maken uit de database,
        // creëert het juiste type boek object en slaat het op in de lijst die hieronder is aangemaakt, en returned de lijst
        // Deze functie is zo gemaakt zodat iedere keer wanneer de applicatie opstart alle boeken als eerste worden ingeladen.

        ArrayList<Book> allBooksList = new ArrayList<>();

        String retrieveBooks = """
            SELECT title, isbn_number, special_feature, page_number, favourite,
                narrator_id, cover_location_id, file_type_id, cover_type_id, author_id,
                book_location_id, book_status_id, book_type_id
            FROM book 
        
        """;
        try(Statement stmt = getCon().createStatement();
            ResultSet rs = stmt.executeQuery(retrieveBooks)){

            //Het aanmaken en opslaan van het boek wordt boek voor boek gedaan en dat gebeurt allemaal in deze while loop
            while (rs.next()) {
                //als eerste wordt een record van het boek hieronder opgeslagen in aparte variabelen. Om natuurlijk straks het boek object aan kunnen maken.
                Integer bookTypeId = rs.getInt("book_type_id");
                Integer bookStatusId = rs.getInt("book_status_id");
                String title = rs.getString("title");
                String isbnNumber = rs.getString("isbn_number");
                Integer authorId = rs.getInt("author_id");
                Integer coverLocationId = rs.getInt("cover_location_id");
                Boolean favourite = rs.getBoolean("favourite");
                Integer pageNumber = rs.getInt("page_number");
                String location = rs.getString("location");
                Integer fileTypeId = rs.getInt("file_type_id");
                Integer narratorId = rs.getInt("narrator_id");
                Integer coverTypeId = rs.getInt("cover_type_id");
                String specialFeature = rs.getString("book_special_feature");

                // Om een boek object te maken heb je natuurlijk niks aan id waardes, maar wel de waarde die het id representeert
                // Daarom worden de id's in een array hier onder opgeslagen om later te gebruiken bij het halen van de relevante waardes.
                Integer[] ids = {bookTypeId,bookStatusId,authorId,coverLocationId,fileTypeId,narratorId,coverTypeId };
                String[] getValues = {
                        "SELECT `value` FROM book_type WHERE id = ?",
                        "SELECT `value` FROM book_status WHERE id = ?",
                        "SELECT name FROM person WHERE id = ?",
                        "SELECT `value` FROM cover_location WHERE id = ?",
                        "SELECT `value` FROM file_type WHERE id = ?",
                        "SELECT name FROM person WHERE id = ?",
                        "SELECT `value` FROM cover_type WHERE id = ?",
                };

                // De waarde die de id's representeren worden hieronder apart opgeslagen. Default is null.
                Book.BookType bookType = null;
                Book.BookStatus bookStatus = null;
                String author = null;
                String coverLocation = null;
                Book.FileType fileType = null;
                String narrator = null;
                PhysicalBook.CoverType coverType = null;

                // Deze for loop kijkt of de variabelen met Id's niet null zijn en elke id representeert natuurlijk een waarde en die wordt opghaald en in de juiste variablele gestopt.
                for (int i = 0; i < ids.length; i++) {
                    if (ids[i] != null) {
                        try (PreparedStatement pstmt = getCon().prepareStatement(getValues[i])) {
                            pstmt.setInt(1, ids[i]);
                            try (ResultSet idRs = pstmt.executeQuery()){
                                if (idRs.next()) {
                                    if (i == 0){
                                        bookType = Book.BookType.valueOf(idRs.getString("value"));
                                    }
                                    else if (i == 1){
                                        bookStatus = Book.BookStatus.valueOf(idRs.getString("value"));
                                    }
                                    else if (i == 2){
                                        author = idRs.getString("name");
                                    }
                                    else if (i == 3){
                                        coverLocation = idRs.getString("value");
                                    }
                                    else if (i == 4){
                                        fileType = PhysicalBook.FileType.valueOf(idRs.getString("value"));
                                    }
                                    else if (i == 5){
                                        narrator = idRs.getString("name");
                                    }
                                    else if (i == 6){
                                        coverType = PhysicalBook.CoverType.valueOf(idRs.getString("value"));
                                    }
                                }

                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        }catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                }

                // Op basis van welke boekType het boek is, wordt hieronder het boek aangemaakt en meteen in de list gestopt.
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
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allBooksList;
    }

    public Connection getCon() {
        return con;
    }

    public void setCon(Connection con) {
        this.con = con;
    }
}
