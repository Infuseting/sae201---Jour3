package app.fx.graphics;

import app.model.map.Path;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class GraphicPath extends Line {
	private Path path;
	private Label label = new Label();
	private SimpleObjectProperty<GraphicPathState> state = new SimpleObjectProperty<GraphicPathState>(GraphicPathState.DEFAULT);
	
	public GraphicPath(Path path, GraphicPlace firstPlace, GraphicPlace secondPlace) {
		this.fillProperty().bind(Bindings.createObjectBinding(()->state.get().getColor(), state));
		this.path = path;
		this.startXProperty().bind(firstPlace.centerXProperty());
		this.startYProperty().bind(firstPlace.centerYProperty());
		this.endXProperty().bind(secondPlace.centerXProperty());
		this.endYProperty().bind(secondPlace.centerYProperty());
		this.label = new Label();
		this.label.textProperty().setValue(path.getLength() + "");
		System.out.println(this.label);
		this.label.layoutXProperty().bind(Bindings.divide(Bindings.subtract(this.endXProperty(), this.startXProperty()), 2));
		this.label.layoutYProperty().bind(Bindings.divide(Bindings.subtract(this.endYProperty(), this.startYProperty()), 2));
		this.label.textFillProperty().bind(this.fillProperty());
		//this.label.setPrefWidth(this.getStrokeWidth()); Sinon le texte ne s'affiche pas bien.
		this.strokeWidthProperty().bind(Bindings.when(Bindings.or(firstPlace.pressedProperty(),secondPlace.pressedProperty()))
				.then(Bindings.createDoubleBinding(()->{
					state.set(GraphicPathState.HAS_FOCUS);
					return state.get().getStroke();
					}, state))
				.otherwise(Bindings.createDoubleBinding(()->{
					state.set(GraphicPathState.DEFAULT);
					return state.get().getStroke();
					}, state)));

		label.layoutXProperty().bind(
				Bindings.createDoubleBinding(() ->
								(getStartX() + getEndX()) / 2 + 20 * (getEndY() - getStartY()) / Math.hypot(getEndX() - getStartX(), getEndY() - getStartY()),
						startXProperty(), endXProperty(), startYProperty(), endYProperty()
				)
		);
		label.layoutYProperty().bind(
				Bindings.createDoubleBinding(() ->
								(getStartY() + getEndY()) / 2 - 20 * (getEndX() - getStartX()) / Math.hypot(getEndX() - getStartX(), getEndY() - getStartY()),
						startXProperty(), endXProperty(), startYProperty(), endYProperty()
				)
		);


	}
	public Label getLabel() {
		return label;
	}
}
