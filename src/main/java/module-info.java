module com.example.colonybattle {
    requires javafx.controls;
    requires javafx.fxml;
            
                        requires org.kordamp.bootstrapfx.core;
    requires java.desktop;

    opens com.example.colonybattle to javafx.fxml;
    exports com.example.colonybattle;
    exports com.example.colonybattle.person;
    opens com.example.colonybattle.person to javafx.fxml;
    exports com.example.colonybattle.Colors;
    opens com.example.colonybattle.Colors to javafx.fxml;
    exports com.example.colonybattle.colony;
    opens com.example.colonybattle.colony to javafx.fxml;
}