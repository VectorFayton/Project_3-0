module com.example.project_30 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.project_30 to javafx.fxml;
    exports com.example.project_30;
}