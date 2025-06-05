package app.fx.graphics;

import app.model.map.Place;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.paint.Color;

public enum GraphicPlaceState {
	DEFAULT(Color.GRAY, 2.5),
	IS_END(Color.BLUE, 2.5),
	IS_START(Color.GREEN, 2.5),
	IS_DEFEAT(Color.RED, 2.5),
	DIJKSTRA_OVER(Color.GRAY, 5.),
	DIJKSTRA_VISITED(Color.GRAY, 2.5),
	DIJKSTRA_UNVISITED(Color.GRAY, 2.5),
	DIJKSTRA_CURRENT(Color.GRAY, 5.),
	DIJKSTRA_MODIFIED(Color.GRAY, 2.5);
	
	private final Color color;
	private final Double stroke;
	
	private GraphicPlaceState(Color color, Double stroke) {
		this.color = color;
		this.stroke = stroke;
	}

	public Color getColor() {
		return color;
	}

	public Double getStroke() {
		return stroke;
	}
	
	public SimpleBooleanProperty isDijkstra() {
		return new SimpleBooleanProperty(ordinal() < 3);
	}

}
