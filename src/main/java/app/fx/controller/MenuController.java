package app.fx.controller;

import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;

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
        
    }
}
