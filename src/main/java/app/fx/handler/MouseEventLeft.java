package app.fx.handler;

import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;

public class MouseEventLeft implements CenterMouseEvent {
	private double originX, originY;
	private Node node;

	public MouseEventLeft(Node node) {
		this.node = node;
	}
	
	@Override
	public void mousePressed(MouseEvent event) {
		originX = event.getX();
		originY = event.getY();
		node.setCursor(Cursor.CLOSED_HAND);
	}

	@Override
	public void mouseDragged(MouseEvent event) {
		double dX = event.getX() - originX;
		double dY = event.getY() - originY;
		node.setTranslateX(dX);
		node.setTranslateY(dY);
	}

	@Override
	public void mouseReleased(MouseEvent event) {
		node.setCursor(Cursor.DEFAULT);
		
	}

}
