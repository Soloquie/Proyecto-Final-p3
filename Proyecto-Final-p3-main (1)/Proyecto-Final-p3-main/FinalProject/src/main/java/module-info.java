module uniquindio.finalproject {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.logging;


    opens uniquindio.finalproject to javafx.fxml;
    exports uniquindio.finalproject;

    opens uniquindio.finalproject.controller;
    exports uniquindio.finalproject.controller;

    opens uniquindio.finalproject.Model;
    exports uniquindio.finalproject.Model;
<<<<<<< Updated upstream:FinalProject/src/main/java/module-info.java
=======

    exports uniquindio.finalproject.mapping.mappers to org.mapstruct;

    opens uniquindio.finalproject.mapping.dto;
    exports uniquindio.finalproject.mapping.dto;

    opens uniquindio.finalproject.controller;
    exports uniquindio.finalproject.controller;
>>>>>>> Stashed changes:Proyecto-Final-p3-main (1)/Proyecto-Final-p3-main/FinalProject/src/main/java/module-info.java
}