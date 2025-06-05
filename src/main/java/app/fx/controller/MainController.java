package app.fx.controller;

import app.Main;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import app.model.map.Place;
import app.model.map.World;
import app.model.parser.WorldIO;
import app.fx.util.Dialogues;
import app.model.map.World;
import app.model.parser.WorldIO;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.util.Duration;
import javafx.util.Pair;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
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

    public World world = new World("Undefined");;
    private SimpleBooleanProperty isDijkstraRunning = new SimpleBooleanProperty(false);
    private SimpleBooleanProperty isGeneratingWorld = new SimpleBooleanProperty(false);
    private ObservableList<DijkstraEventListener> dijkstraList = FXCollections.observableArrayList();



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.menuController.setMainController(this);
        this.contentController.setMainController(this);
        this.worldParametersController.setMainController(this);
        this.placeParametersController.setMainController(this);
		dijkstraList.add(placeParametersController);
        try {
            world = WorldIO.loadWorld(Main.class.getResourceAsStream("Monde1.json"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
		placeParameters.visibleProperty().bind(contentController.selectedPlace.isNotNull());
		contentController.setSelectedPlace(world.getPlaces().get(0));

    }

    public void launchDijkstra() {
    	if (contentController.selectedPlace == null) return;
    	isDijkstraRunning.set(true);
		WorldAnalyzer worldAnalyzer = new WorldAnalyzer(world);
		CompletableFuture<List<Pair<Place, HashMap<Place, Integer>>>> thread = CompletableFuture.supplyAsync(() -> {
    		return worldAnalyzer.dijkstraWithSteps(contentController.selectedPlace.get());
    	});
    	thread.thenAccept(steps -> {
    		 final Timeline timeline = new Timeline();
    		 Duration currentTime = Duration.ZERO;
    		 Duration duration = Duration.millis(250);

    		 for (Pair<Place, HashMap<Place, Integer>> pair : steps) {
				Place currentPlace = pair.getKey();

				timeline.getKeyFrames().add(new KeyFrame(currentTime, e -> dijkstraList.forEach(elem -> elem.beforeLineFrom(currentPlace))));
				currentTime = currentTime.add(duration);

    }

    public boolean onSauvegarde() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Load World from Json");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSON files", "*.json"));
        File file = fileChooser.showOpenDialog(contentController.Canvas.getScene().getWindow());
        if (file != null) {
            try {
                world = WorldIO.loadWorld((InputStream) new FileInputStream(file));
                contentController.isModifiedProperty.set(false);
                contentController.currentFileProperty.set(file.getAbsolutePath());
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }
    public boolean onChargement() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Load Json World");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSON files", "*.json"));
        File file = fileChooser.showOpenDialog(contentController.Canvas.getScene().getWindow());
        if (file != null) {
            try {
                InputStream inputStream = new FileInputStream(file);
                world = WorldIO.loadWorld(inputStream);
                contentController.isModifiedProperty.set(false);
                contentController.currentFileProperty.set(file.getAbsolutePath());
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public boolean onQuitter(MainController controller) {
        if (Dialogues.confirmation(controller)) {
            return true;
        }
        return false;
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
				isDijkstraRunning.set(false);
			}));
			currentTime = currentTime.add(duration);
			timeline.play();
    	}).exceptionally(ex -> {
			ex.printStackTrace();
			return null;
		});

    }

	public SimpleBooleanProperty dijkstraRunningProperty() {
		return isDijkstraRunning;
	}
}
