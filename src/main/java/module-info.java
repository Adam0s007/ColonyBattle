module com.example.colonybattle {
    requires javafx.controls;
    requires javafx.fxml;
            
                        requires org.kordamp.bootstrapfx.core;
    requires java.desktop;
    requires lombok;

    opens com.example.colonybattle to javafx.fxml;
    exports com.example.colonybattle;
    exports com.example.colonybattle.models.person;
    opens com.example.colonybattle.models.person to javafx.fxml;
    exports com.example.colonybattle.colors;
    opens com.example.colonybattle.colors to javafx.fxml;
    exports com.example.colonybattle.colony;
    opens com.example.colonybattle.colony to javafx.fxml;
    exports com.example.colonybattle.models.person.characters;
    opens com.example.colonybattle.models.person.characters to javafx.fxml;
    exports com.example.colonybattle.board.boardlocks;
    opens com.example.colonybattle.board.boardlocks to javafx.fxml;
    exports com.example.colonybattle.models.person.type;
    opens com.example.colonybattle.models.person.type to javafx.fxml;
    exports com.example.colonybattle.models.person.helpers;
    opens com.example.colonybattle.models.person.helpers to javafx.fxml;
    exports com.example.colonybattle.models.person.actions;
    opens com.example.colonybattle.models.person.actions to javafx.fxml;
    exports com.example.colonybattle.models.person.status;
    opens com.example.colonybattle.models.person.status to javafx.fxml;
    exports com.example.colonybattle.utils;
    opens com.example.colonybattle.utils to javafx.fxml;
    exports com.example.colonybattle.board.position;
    opens com.example.colonybattle.board.position to javafx.fxml;
    exports com.example.colonybattle.board;
    opens com.example.colonybattle.board to javafx.fxml;
    exports com.example.colonybattle.launcher;
    opens com.example.colonybattle.launcher to javafx.fxml;
    exports com.example.colonybattle.models.person.id;
    opens com.example.colonybattle.models.person.id to javafx.fxml;
    exports com.example.colonybattle.models.person.actions.attack;
    opens com.example.colonybattle.models.person.actions.attack to javafx.fxml;
    exports com.example.colonybattle.models.person.actions.magic;
    opens com.example.colonybattle.models.person.actions.magic to javafx.fxml;
}