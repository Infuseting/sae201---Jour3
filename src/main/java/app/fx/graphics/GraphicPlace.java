package app.fx.graphics;

import app.model.map.Place;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.Label;
import javafx.scene.shape.Circle;

public class GraphicPlace extends Circle{
	private Place place;
	private Label label = new Label();
	private SimpleObjectProperty<GraphicPlaceState> state = new SimpleObjectProperty<GraphicPlaceState>(GraphicPlaceState.DEFAULT);

	public GraphicPlace(Place place, double x, double y) {
		super(x, y, 15);
		this.fillProperty().bind(Bindings.createObjectBinding(()->state.get().getColor(), state));
		this.strokeWidthProperty().bind(Bindings.when(pressedProperty())
				.then(5.)
				.otherwise(state.get().getStroke()));
		this.place = place;
		this.label.layoutXProperty().bind(this.centerXProperty());
		this.label.layoutYProperty().bind(this.centerYProperty());
		this.label.textProperty().bind(Bindings.when(state.get().isDijkstra()).then(String.format("id : %d", place.getId())).otherwise(String.format("distance : %d",1)));
	}
	
	public Place getPlace() {
		return place;
	}
}
