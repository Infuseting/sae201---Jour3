package app.fx.graphics;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.paint.Color;

public enum GraphicPlaceState {
	DEFAULT(Color.GRAY, 5.),
	IS_END(Color.GRAY, 5.),
	IS_START(Color.GRAY, 5.),
	IS_DEFEAT(Color.GRAY, 5.),
	DIJKSTRA_OVER(Color.GRAY, 5.),
	DIJKSTRA_VISITED(Color.GRAY, 5.),
	DIJKSTRA_UNVISITED(Color.GRAY, 5.),
	DIJKSTRA_CURRENT(Color.GRAY, 5.),
	DIJKSTRA_MODIFIED(Color.GRAY, 5.);
	
	private SimpleObjectProperty<Color> color;
	private SimpleDoubleProperty stroke;
	
	private GraphicPlaceState(Color Color, Double stroke) {
		this.color.set(Color);
		this.stroke.set(stroke);
	}

	public SimpleObjectProperty<Color> colorProperty() {
		return color;
	}

	public SimpleDoubleProperty strokeProperty() {
		return stroke;
	}
	
	public SimpleBooleanProperty isDijkstra() {
		return new SimpleBooleanProperty(ordinal() < 3);
	}
	
}
