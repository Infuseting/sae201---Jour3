package app.fx.controller;

import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public class placeParametersController implements Initializable {
    public TextArea descArea;
    public Button startBtn;
    public Button endBtn;
    public Button defeatBtn;
    public Button djikstraBtn;
    public TextField nameMonsterField;
    public TextField armorMonsterField;
    public TextField hpMonsterField;
    public TextField attackMonsterField;
    public TableView djikstraTable;
    public TextField nameArea;
    public TextField idArea;
    public CheckBox isMonsterCheck;

    public MainController controller;

    public void setMainController(MainController controller) {
        this.controller = controller;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
