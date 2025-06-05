package app.fx.handler;

import java.util.Map;

import app.fx.controller.MainController;
import app.fx.graphics.GraphicPath;
import app.fx.graphics.GraphicPlace;
import app.model.map.Path;
import app.model.map.Place;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class MouseEventRight implements CenterMouseEvent {
	private GraphicPlace orgin;
	private MainController controller;
	private Line line;
	public MouseEventRight(MainController controller) {
		this.controller = controller;
		
		
	}

	@Override
	public void mousePressed(MouseEvent event) {
		controller.contentController.isModifiedProperty.set(true);
		for(GraphicPlace gp : controller.getPlaces().values()) {
			if(gp.contains(event.getX(), event.getY())) {
				orgin = gp;
				line = new Line(event.getX(), event.getY(), event.getX(), event.getY());

				line.setFill(Color.BLACK);
				line.setStrokeWidth(1);
				controller.contentController.CanvasPane.getChildren().add(line);
				return;
			}
		}
	}

	@Override
	public void mouseDragged(MouseEvent event) {
		line.setEndX(event.getX());
		line.setEndY(event.getY());
	}

	@Override
	public void mouseReleased(MouseEvent event) {
		for (GraphicPlace gp : controller.getPlaces().values()) {
			if (gp.contains(event.getX(), event.getY())) {
				if (orgin != gp) {
					boolean existe = controller.getWorld().getPaths().stream().anyMatch(
							p -> (p.getFirstPlace().equals(orgin.getPlace()) && p.getSecondPlace().equals(gp.getPlace()))
									|| (p.getFirstPlace().equals(gp.getPlace()) && p.getSecondPlace().equals(orgin.getPlace()))
					);
					if (!existe) {
						Path path = new Path(orgin.getPlace(), gp.getPlace(), 1);
						controller.getWorld().addPath(path);
						GraphicPath ln = new GraphicPath(path, orgin, gp);
						controller.getPaths().put(path, ln);
						controller.contentController.CanvasPane.getChildren().add(0, ln);
						controller.contentController.CanvasPane.getChildren().add(0, ln.getLabel());
					}
					break;
				}
			}
		}
		controller.contentController.CanvasPane.getChildren().remove(line);
		line = null;
	}

}
