module com.example.c195 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires mysql.connector.java;



    opens com.example.c195 to javafx.fxml;
    opens model to javafx.fxml;
    exports com.example.c195;
    exports model;
}