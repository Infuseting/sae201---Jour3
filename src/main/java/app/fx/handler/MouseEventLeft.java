package app.fx.handler;

import java.util.ArrayList;

import app.fx.controller.MainController;
import app.fx.graphics.GraphicPlace;
import javafx.collections.ObservableList;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;

public class MouseEventLeft implements CenterMouseEvent {
	private double originX, originY;
	private MainController controller;
	private GraphicPlace place;

	public MouseEventLeft(MainController controller) {
		this.controller = controller;
	}
	
	@Override
	public void mousePressed(MouseEvent event) {
		originX = event.getX();
		originY = event.getY();
		((Node) controller.contentController.CanvasPane).setCursor(Cursor.CLOSED_HAND);
		
		ObservableList<Node> child = controller.contentController.CanvasPane.getChildren();
		
		place = null;
		for (Node node : child) {
			if (node instanceof GraphicPlace) {
				if(node.contains(event.getX(), event.getY())) {
					place = (GraphicPlace) node;
					controller.selectedPlace = place.getPlace();
				}
				
			}
		}
		
		
	}

	@Override
	public void mouseDragged(MouseEvent event) {
		if(place == null) {
			double dX = event.getX() - originX;
			double dY = event.getY() - originY;
			((Node) controller.contentController.CanvasPane).setTranslateX(((Node) controller.contentController.CanvasPane).getTranslateX() + dX);
			((Node) controller.contentController.CanvasPane).setTranslateY(((Node) controller.contentController.CanvasPane).getTranslateY() + dY);
		}
		else {
			place.setCenterX(event.getX());
			place.setCenterY(event.getY());
		}
	}

	@Override
	public void mouseReleased(MouseEvent event) {
		((Node) controller.contentController.CanvasPane).setCursor(Cursor.DEFAULT);
		
	}
	
}
