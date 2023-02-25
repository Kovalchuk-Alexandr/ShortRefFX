module com.itproger.diplomfx {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires mysql.connector.j;
    requires javafx.graphics;


    opens com.itproger.diplomfx to javafx.fxml;
    exports com.itproger.diplomfx;
    exports com.itproger.diplomfx.controllers;
    opens com.itproger.diplomfx.controllers to javafx.fxml;
}