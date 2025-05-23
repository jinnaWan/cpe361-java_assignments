module fxapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.common;
    
    exports fxapp;
    exports fxapp.common;
    
    opens fxapp to javafx.fxml;
}