module com.adinf.bdsm {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.adinf.bdsm to javafx.fxml;
    exports com.adinf.bdsm;
}