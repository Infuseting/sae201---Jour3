package app.fx.controller;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ResourceBundle;

import app.fx.graphics.GraphicPlace;

public class ContentController implements Initializable {
    public Pane CanvasPane;
    public Canvas Canvas;

    private SimpleDoubleProperty prevX = new SimpleDoubleProperty();
    private SimpleDoubleProperty prevY = new SimpleDoubleProperty();
    
    public MainController controller;

    public void setMainController(MainController controller) {
        this.controller = controller;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    	Canvas.setOnMouseClicked(event ->{
    		if(!(event.getButton() == MouseButton.PRIMARY) || !(event.getTarget() instanceof GraphicPlace))
    			return;
    		GraphicPlace place = (GraphicPlace)event.getTarget();
    		prevX.set(place.getCenterX());
    		prevY.set(place.getCenterY());
    		event.consume();
    	});
    	Canvas.setOnMouseDragged(event -> {
    		if(!(event.getButton() == MouseButton.PRIMARY) || !(event.getTarget() instanceof GraphicPlace))
    			return;
    		GraphicPlace place = (GraphicPlace)event.getTarget();
    		place.setCenterX(event.getX());
    		place.setCenterY(event.getY());
    		event.consume();
    	});
    }
}
