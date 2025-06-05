package app.fx.controller;

import app.model.map.Place;
import app.model.map.World;
import app.model.parser.WorldIO;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
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

    public World world = new World("Undefined");
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.menuController.setMainController(this);
        this.contentController.setMainController(this);
        this.worldParametersController.setMainController(this);
        this.placeParametersController.setMainController(this);

        contentController.setSelectedPlace(new Place(0, "Le cerveau de Leo", null, "Y a r zebi", world, false, false, false));
        placeParametersController.loadNewPlace();

    }
}
