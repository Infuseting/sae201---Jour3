package app.fx.graphics;

import app.fx.controller.MainController;
import app.model.map.Place;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.Label;
import javafx.scene.shape.Circle;

import java.awt.*;

public class GraphicPlace extends Circle{
	private Place place;
	public Label labelIn = new Label();
	public Label labelOut = new Label();
	private SimpleObjectProperty<GraphicPlaceState> state = new SimpleObjectProperty<GraphicPlaceState>(GraphicPlaceState.DEFAULT);
	private MainController controller;
	public GraphicPlace(MainController controller, Place place, double x, double y) {
		super(x, y, 15);

		
		this.controller = controller;
		state.bind(Bindings.createObjectBinding(() -> {
			if (place.isStartProperty().get()) {
				return GraphicPlaceState.IS_START;
			} else if (place.isEndProperty().get()) {
				return GraphicPlaceState.IS_END;
			} else if (place.isDefeatProperty().get()) {
				return GraphicPlaceState.IS_DEFEAT;
			} else {
				return GraphicPlaceState.DEFAULT;
			}
		}, place.isStartProperty(), place.isEndProperty(), place.isDefeatProperty()));
		this.fillProperty().bind(Bindings.createObjectBinding(()->state.get().getColor(), state));
		this.strokeWidthProperty().bind(
				Bindings.when(
						Bindings.or(
								pressedProperty(),
								Bindings.createBooleanBinding(
										() -> controller.contentController.selectedPlace.get() == place,
										controller.contentController.selectedPlace
								)
						)
				).then(5.).otherwise(state.get().getStroke())
		);
		this.strokeProperty().bind(Bindings.createObjectBinding(() -> javafx.scene.paint.Color.BLACK, state));
		this.place = place;
		this.labelOut.layoutXProperty().bind(centerXProperty().subtract(labelOut.widthProperty().divide(2)));
		this.labelOut.layoutYProperty().bind(centerYProperty().subtract(labelOut.heightProperty().divide(2)).add(30));
		this.labelIn.layoutXProperty().bind(centerXProperty().subtract(labelIn.widthProperty().divide(2)));
		this.labelIn.layoutYProperty().bind(centerYProperty().subtract(labelIn.heightProperty().divide(2)));
		this.labelIn.textProperty().bind(Bindings.when(state.get().isDijkstra()).then(String.format("%d", place.getId())).otherwise(String.format(" %d",1)));
		this.labelOut.textProperty().bind(place.nameProperty());
	}
	
	public Place getPlace() {
		return place;
	}
}
