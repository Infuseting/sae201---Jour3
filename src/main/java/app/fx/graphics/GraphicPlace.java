package app.fx.graphics;

import app.model.map.Place;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.control.Label;
import javafx.scene.shape.Circle;

public class GraphicPlace extends Circle{
	private Place place;
	private Label label;
	private GraphicPlaceState state = GraphicPlaceState.DEFAULT;

	public GraphicPlace(Place place) {
		this.fillProperty().bind(state.colorProperty());
		this.strokeWidthProperty().bind(Bindings.when(pressedProperty()).then(new SimpleDoubleProperty(10.)).otherwise(state.strokeProperty()));
		this.place = place;
		this.label.layoutXProperty().bind(this.layoutXProperty());
		this.label.layoutYProperty().bind(this.layoutYProperty());
		this.label.textProperty().bind(Bindings.when(state.isDijkstra()).then(String.format("id : %d", place.getId())).otherwise(String.format("distance : %d",1)));
	}
}
