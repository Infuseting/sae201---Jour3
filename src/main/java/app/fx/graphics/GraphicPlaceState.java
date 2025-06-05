package app.fx.graphics;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.paint.Color;

public enum GraphicPlaceState {
	DEFAULT(color, stroke),
	IS_END(color, stroke),
	IS_START(color, stroke),
	IS_DEFEAT(color, stroke),
	DIJKSTRA_OVER(color, stroke),
	DIJKSTRA_VISITED(color, stroke),
	DIJKSTRA_UNVISITED(color, stroke),
	DIJKSTRA_CURRENT(color, stroke),
	DIJKSTRA_MODIFIED(color, stroke);
	
	private SimpleObjectProperty<Color> color;
	private SimpleDoubleProperty stroke;
	
	private GraphicPlaceState(Color color, Double stroke) {
		this.color.set(color);
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
