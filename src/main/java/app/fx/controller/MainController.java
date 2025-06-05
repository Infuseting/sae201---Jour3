package app.fx.controller;

import app.fx.util.Dialogues;
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

    }

    public boolean onSauvegarde() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Load World from Json");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSON files", "*.json"));
        File file = fileChooser.showOpenDialog(contentController.Canvas.getScene().getWindow());
        if (file != null) {
            try {
                world = WorldIO.loadWorld((InputStream) new FileInputStream(file));
                contentController.isModifiedProperty.set(false);
                contentController.currentFileProperty.set(file.getAbsolutePath());
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }
    public boolean onChargement() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Load Json World");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSON files", "*.json"));
        File file = fileChooser.showOpenDialog(contentController.Canvas.getScene().getWindow());
        if (file != null) {
            try {
                InputStream inputStream = new FileInputStream(file);
                world = WorldIO.loadWorld(inputStream);
                contentController.isModifiedProperty.set(false);
                contentController.currentFileProperty.set(file.getAbsolutePath());
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public boolean onQuitter(MainController controller) {
        if (Dialogues.confirmation(controller)) {
            return true;
        }
        return false;
    }
}
