package app.fx.handler;

import java.util.Map;

import app.fx.controller.MainController;
import app.fx.graphics.GraphicPath;
import app.fx.graphics.GraphicPlace;
import app.model.map.Path;
import app.model.map.Place;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Line;

public class MouseEventRight implements CenterMouseEvent {
	private GraphicPlace orgin;
	private MainController controller;
	private Line line = new Line();
	
	public MouseEventRight(MainController controller) {
		this.controller = controller;
	}

	@Override
	public void mousePressed(MouseEvent event) {
		for(GraphicPlace gp : controller.getPlaces().values()) {
			if(gp.contains(event.getX(), event.getY())) {
				orgin = gp;
				line = new Line(event.getX(), event.getY(), event.getX(), event.getY());
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
		for(GraphicPlace gp : controller.getPlaces().values()) {
			if(gp.contains(event.getX(), event.getY())) {
				Path path = new Path(orgin.getPlace(), gp.getPlace(),1);
				controller.getWorld().addPath(path);
				controller.getPaths().put(path, new GraphicPath(path, orgin, gp));
				controller.onChangeWorld();
				return;
			}
		}

	}

}
