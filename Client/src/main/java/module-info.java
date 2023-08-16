module gr.uop {
    requires javafx.controls;
    requires javafx.fxml;

    opens gr.uop to javafx.fxml;
    exports gr.uop;
}
