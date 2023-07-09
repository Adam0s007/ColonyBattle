module com.example.colonybattle {
    requires javafx.controls;
    requires javafx.fxml;
            
                        requires org.kordamp.bootstrapfx.core;
            
    opens com.example.colonybattle to javafx.fxml;
    exports com.example.colonybattle;
    exports com.example.colonybattle.person;
    opens com.example.colonybattle.person to javafx.fxml;
}