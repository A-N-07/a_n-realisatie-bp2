module com.adinf.bdsm {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.jetbrains.annotations;


    opens com.adinf.bdsm to javafx.fxml;
    exports com.adinf.bdsm;
    exports com.adinf.bdsm.model;
    opens com.adinf.bdsm.model to javafx.fxml;
    exports com.adinf.bdsm.controller;
    opens com.adinf.bdsm.controller to javafx.fxml;
    exports com.adinf.bdsm.util;
    opens com.adinf.bdsm.util to javafx.fxml;
    exports com.adinf.bdsm.view;
    opens com.adinf.bdsm.view to javafx.fxml;
}