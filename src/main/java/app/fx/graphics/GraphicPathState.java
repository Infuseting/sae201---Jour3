package app.fx.graphics;

import javafx.scene.paint.Color;

public enum GraphicPathState {
	DEFAULT(Color.GRAY, 5.),
	HAS_FOCUS(Color.GRAY, 5.),
	HAS_FOCUS_MODIFIED(Color.GRAY, 5.);
	
	private final Color color;
	private final Double stroke;
	
	private GraphicPathState(Color color, Double stroke) {
		this.color = color;
		this.stroke = stroke;
	}

	public Color getColor() {
		return color;
	}

	public Double getStroke() {
		return stroke;
	}
}
