module uniquindio.finalproject {
    requires javafx.controls;
    requires javafx.fxml;


    opens uniquindio.finalproject to javafx.fxml;
    exports uniquindio.finalproject;

    opens uniquindio.finalproject.controller;
    exports uniquindio.finalproject.controller;

    opens uniquindio.finalproject.Model;
    exports uniquindio.finalproject.Model;
}