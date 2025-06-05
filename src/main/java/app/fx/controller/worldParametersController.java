package app.fx.controller;

import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class worldParametersController implements Initializable {
    public TextField nameWorld;
    public TextField nbPlace;
    public Slider sliderDebut;
    public Label labelDebut;
    public Slider sliderVictoire;
    public Label labelVictoire;
    public Slider sliderDefaite;
    public Label labelDefaite;
    public Slider sliderAutre;
    public Label labelAutre;
    public Slider sliderCouverture;
    public Label labelCouverture;
    public CheckBox iaGeneratorCheck;

    public MainController controller;

    public void setMainController(MainController controller) {
        this.controller = controller;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

}
