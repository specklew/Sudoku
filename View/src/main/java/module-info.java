module pl.cp.view {
    requires javafx.controls;
    requires javafx.fxml;
    requires pl.cp.model;

    opens pl.cp.view to javafx.fxml;
    exports pl.cp.view;
}