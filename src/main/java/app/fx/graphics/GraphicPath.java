package app.fx.graphics;

import app.model.map.Path;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ReadOnlyLongWrapper;
import javafx.scene.control.Label;
import javafx.scene.shape.Line;

public class GraphicPath extends Line {
	private Path path;
	private Label label;
	private GraphicPlace firstPlace;
	private GraphicPlace secondPlace;
	private GraphicPathState state = GraphicPathState.DEFAULT;
	
	public GraphicPath(Path path) {
		this.path = path;
		this.firstPlace = new GraphicPlace(path.getFirstPlace());
		this.secondPlace = new GraphicPlace(path.getSecondPlace());
		this.startXProperty().bind(firstPlace.centerXProperty());
		this.startYProperty().bind(firstPlace.centerYProperty());
		this.endXProperty().bind(secondPlace.centerXProperty());
		this.endYProperty().bind(secondPlace.centerYProperty());
		this.label = new Label(String.format("%d", path.getLength()));
		this.label.layoutXProperty().bind(Bindings.divide(Bindings.subtract(this.endXProperty(), this.startXProperty()), 2));
		this.label.layoutYProperty().bind(Bindings.divide(Bindings.subtract(this.endYProperty(), this.startYProperty()), 2));
		this.label.textFillProperty().bind(this.fillProperty());
		this.label.setPrefWidth(this.getStrokeWidth());
	}
}
