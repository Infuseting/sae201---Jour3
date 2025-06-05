package app.fx.graphics;

import app.model.map.Path;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.Label;
import javafx.scene.shape.Line;

public class GraphicPath extends Line {
	private Path path;
	private Label label;
	private SimpleObjectProperty<GraphicPathState> state = new SimpleObjectProperty<GraphicPathState>(GraphicPathState.DEFAULT);
	
	public GraphicPath(Path path, GraphicPlace firstPlace, GraphicPlace secondPlace) {
		this.fillProperty().bind(Bindings.createObjectBinding(()->state.get().getColor(), state));
		this.strokeWidthProperty().bind(Bindings.when(pressedProperty()).then(new SimpleDoubleProperty(10.)).otherwise(Bindings.createDoubleBinding(()->state.get().getStroke(), state)));
		this.path = path;
		this.startXProperty().bind(firstPlace.centerXProperty());
		this.startYProperty().bind(firstPlace.centerYProperty());
		this.endXProperty().bind(secondPlace.centerXProperty());
		this.endYProperty().bind(secondPlace.centerYProperty());
		this.label = new Label(String.format("%d", path.getLength()));
		this.label.layoutXProperty().bind(Bindings.divide(Bindings.subtract(this.endXProperty(), this.startXProperty()), 2));
		this.label.layoutYProperty().bind(Bindings.divide(Bindings.subtract(this.endYProperty(), this.startYProperty()), 2));
		this.label.textFillProperty().bind(this.fillProperty());
		this.label.setPrefWidth(this.getStrokeWidth());
		this.strokeWidthProperty().bind(Bindings.when(firstPlace.pressedProperty()).then(new SimpleDoubleProperty(10.)).otherwise(new SimpleDoubleProperty(5.)));
		this.strokeWidthProperty().bind(Bindings.when(secondPlace.pressedProperty()).then(new SimpleDoubleProperty(10.)).otherwise(new SimpleDoubleProperty(5.)));
	}
}
