module uniquindio.finalproject {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.logging;
    requires java.xml;
    requires java.desktop;
    requires org.mapstruct;
    requires org.apache.pdfbox;


    opens uniquindio.finalproject to javafx.fxml;
    exports uniquindio.finalproject;

    opens uniquindio.finalproject.view;
    exports uniquindio.finalproject.view;

    opens uniquindio.finalproject.Model;
    exports uniquindio.finalproject.Model;

    exports uniquindio.finalproject.mapping.mappers to org.mapstruct;

    opens uniquindio.finalproject.mapping.dto;
    exports uniquindio.finalproject.mapping.dto;
}