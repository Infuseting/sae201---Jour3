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
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuBar;
import javafx.scene.input.MouseButton;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import javafx.util.Pair;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.function.Supplier;

import app.ai.world.WorldAnalyzer;
import app.fx.handler.DijkstraEventListener;
import app.model.map.Place;
import app.model.map.World;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.function.Supplier;

import app.Main;
import app.MainApplication;
import app.ai.world.WorldAnalyzer;
import app.fx.graphics.GraphicPath;
import app.fx.graphics.GraphicPlace;
import app.fx.handler.DijkstraEventListener;
import app.fx.handler.MouseEventLeft;
import app.fx.handler.MouseEventRight;
import app.model.map.Path;
import app.model.map.Place;
import app.model.map.World;
import app.model.parser.WorldIO;

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

    public Place selectedPlace;
    private World world;
    private boolean isDijkstraRunning = false;
    private boolean isGeneratingWorld = false;
    private ObservableList<DijkstraEventListener> dijkstraList = FXCollections.observableArrayList();


    private Map<Place, GraphicPlace> places = new HashMap<Place, GraphicPlace>();
    private Map<Path, GraphicPath> paths = new HashMap<Path, GraphicPath>();



    public Map<Place, GraphicPlace> places = new HashMap<Place, GraphicPlace>();
    public Map<Path, GraphicPath> paths = new HashMap<Path, GraphicPath>();

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
        this.contentController.CanvasPane.setOnScroll(event -> {
            double factor = event.getDeltaY() > 0 ? 1.1 : 0.9;
            this.contentController.CanvasPane.setScaleX(factor * this.contentController.CanvasPane.getScaleX());
            this.contentController.CanvasPane.setScaleY(factor * this.contentController.CanvasPane.getScaleY());
        });

        MouseEventLeft mouseEventLeft = new MouseEventLeft(this.contentController.CanvasPane);
        try {
			world = WorldIO.loadWorld(MainApplication.class.getResourceAsStream("Monde1.json"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}



        MouseEventLeft mouseEventLeft = new MouseEventLeft(this);
        MouseEventRight mouseEventRight = new MouseEventRight(this);
        this.contentController.CanvasPane.setOnMousePressed(event -> {
            if(event.getButton() == MouseButton.PRIMARY)
                mouseEventLeft.mousePressed(event);
            else if(event.getButton() == MouseButton.SECONDARY)
                mouseEventRight.mousePressed(event);

        	if(event.getButton() == MouseButton.PRIMARY) {
        		mouseEventLeft.mousePressed(event);
        	}
        	else if(event.getButton() == MouseButton.SECONDARY)
        		mouseEventRight.mousePressed(event);
        });
        this.contentController.CanvasPane.setOnMouseDragged(event -> {
            if(event.getButton() == MouseButton.PRIMARY)
                mouseEventLeft.mouseDragged(event);
            else if(event.getButton() == MouseButton.SECONDARY)
                mouseEventRight.mouseDragged(event);
        });
        this.contentController.CanvasPane.setOnMouseReleased(event -> {
            if(event.getButton() == MouseButton.PRIMARY)
                mouseEventLeft.mouseReleased(event);
            else if(event.getButton() == MouseButton.SECONDARY)
                mouseEventRight.mouseReleased(event);
        });

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
                onChangeWorld();
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
    }
	public SimpleBooleanProperty dijkstraRunningProperty() {
		return isDijkstraRunning;
	}

    public void onChangeWorld() {
    	contentController.CanvasPane.getChildren().clear();
    	places.clear();
    	paths.clear();
    	Random r = new Random();
    	for(Place place : world.getPlaces()) {
    		places.put(place, new GraphicPlace(place, r.nextDouble(content.getScene().getWidth()), r.nextDouble(content.getScene().getHeight())));
    		contentController.CanvasPane.getChildren().add(places.get(place));
    	}
    	for(Path path : world.getPaths()) {
    		paths.put(path, new GraphicPath(path, places.get(path.getFirstPlace()), places.get(path.getSecondPlace())));
    		contentController.CanvasPane.getChildren().add(paths.get(path));
    	}

    }

	public Map<Place, GraphicPlace> getPlaces() {
		return places;
	}

	public World getWorld() {
		return world;
	}

	public Map<Path, GraphicPath> getPaths() {
		return paths;
	}
}
