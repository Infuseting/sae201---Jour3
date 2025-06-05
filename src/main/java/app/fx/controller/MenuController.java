package app.fx.controller;

import app.model.parser.WorldIO;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;

public class MenuController implements Initializable {
    public MenuItem saveBtn;
    public MenuItem loadBtn;
    public MenuItem closeBtn;

    public MainController controller;


    public void setMainController(MainController controller) {
        this.controller = controller;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        saveBtn();
        loadBtn();
        closeBtn();
    }

    private void closeBtn() {
        closeBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            event.consume();
        });
    }

    private void loadBtn() {
        loadBtn.setOnAction(event -> {
            controller.onChargement();
        });
    }

    public void saveBtn() {
        saveBtn.setOnAction(event -> {
            controller.onSauvegarde();
        });
    }
}
