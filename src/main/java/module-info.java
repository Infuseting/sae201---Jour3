module sae2025 {
    requires javafx.graphics;
    requires javafx.fxml;
    requires javafx.controls;
    requires java.net.http;
    requires java.desktop;
	requires javafx.base;

    opens app.model.map;

    opens app to javafx.fxml;
    exports app;
    exports app.fx.controller;
}