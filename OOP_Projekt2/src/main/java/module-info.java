module com.example.projekt2uus {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.projekt2uus to javafx.fxml;
    exports com.example.projekt2uus;
}