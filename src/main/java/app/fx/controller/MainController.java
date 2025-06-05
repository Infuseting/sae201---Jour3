package app.fx.controller;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import javafx.util.Pair;

import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.function.Supplier;

import app.ai.world.WorldAnalyzer;
import app.fx.handler.DijkstraEventListener;
import app.model.map.Place;
import app.model.map.World;

public class MainController implements Initializable {

    public MenuBar menu;
    public Pane content;
    public VBox placeParameters;
    public VBox worldParameters;

    public MenuController menuController;
    public ContentController contentController;
    public worldParametersController worldParametersController;
    public placeParametersController placeParametersController;
    
    private Place selectedPlace;
    private World world;
    private boolean isDijkstraRunning = false;
    private boolean isGeneratingWorld = false;
    private ObservableList<DijkstraEventListener> dijkstraList = FXCollections.observableArrayList();
    
    
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.menuController.setMainController(this);
        this.contentController.setMainController(this);
        this.worldParametersController.setMainController(this);
        this.placeParametersController.setMainController(this);

    }
    
    public void launchDijkstra() {
    	if (selectedPlace == null) return;
    	isDijkstraRunning = true;
    	
    	CompletableFuture<List<Pair<Place, HashMap<Place, Integer>>>> thread = CompletableFuture.supplyAsync(() -> {
        	WorldAnalyzer worldAnalyzer = new WorldAnalyzer(world);
    		return worldAnalyzer.dijkstraWithSteps(selectedPlace);
    	});
    	
    	thread.thenAccept(steps -> {
    		 final Timeline timeline = new Timeline();
    		 Duration currentTime = Duration.ZERO;
    		 Duration duration = Duration.millis(250);
				
    		 for (Pair<Place, HashMap<Place, Integer>> pair : steps) {
				Place currentPlace = pair.getKey();
				
				timeline.getKeyFrames().add(new KeyFrame(currentTime, e -> dijkstraList.forEach(elem -> elem.beforeLineFrom(currentPlace))));
				currentTime = currentTime.add(duration);
				
				HashMap<Place, Integer> map = pair.getValue();
				for (Place to : map.keySet()) {
					timeline.getKeyFrames().add(new KeyFrame(currentTime, e -> dijkstraList.forEach(elem -> elem.beforeNewDistance(currentPlace, to))));
					currentTime = currentTime.add(duration);
					timeline.getKeyFrames().add(new KeyFrame(currentTime, e -> dijkstraList.forEach(elem -> elem.newDistance(currentPlace, to, map.get(to)))));
					currentTime = currentTime.add(duration);
					timeline.getKeyFrames().add(new KeyFrame(currentTime, e -> dijkstraList.forEach(elem -> elem.afterNewDistance(currentPlace, to))));
					currentTime = currentTime.add(duration);
				}
				timeline.getKeyFrames().add(new KeyFrame(currentTime, e -> dijkstraList.forEach(elem -> elem.afterLineFrom(currentPlace))));
				currentTime = currentTime.add(duration);
    		}
			timeline.getKeyFrames().add(new KeyFrame(currentTime, e -> {
				dijkstraList.forEach(elem -> elem.tearDown());
				isDijkstraRunning = false;
			}));
			currentTime = currentTime.add(duration);
			timeline.play();
    	});
    	
    }
}
