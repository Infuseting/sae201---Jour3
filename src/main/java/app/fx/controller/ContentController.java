package app.fx.controller;

import app.model.map.Place;
import javafx.beans.binding.StringExpression;
import javafx.beans.property.*;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ResourceBundle;

public class ContentController implements Initializable {
    public Pane CanvasPane;
    public javafx.scene.canvas.Canvas Canvas;

    public MainController controller;

    public Place selectedPlace;
    public void setMainController(MainController controller) {
        this.controller = controller;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


    }

    public void setSelectedPlace(Place place) {
        this.selectedPlace = place;
    }

}
