package app.fx.controller;

import javafx.fxml.Initializable;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {


    public MenuBar menu;
    public Pane content;
    public VBox placeParameters;
    public VBox worldParameters;

    public MenuController menuController;
    public ContentController contentController;
    public worldParametersController worldParametersController;
    public placeParametersController placeParametersController;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.menuController.setMainController(this);
        this.contentController.setMainController(this);
        this.worldParametersController.setMainController(this);
        this.placeParametersController.setMainController(this);

    }
}
