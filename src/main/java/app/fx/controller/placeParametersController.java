package app.fx.controller;

import app.fx.handler.DijkstraEventListener;
import app.model.entity.Monster;
import app.model.map.Place;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.util.Callback;

import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class placeParametersController implements Initializable, DijkstraEventListener {
    public GridPane right_pane;
    public ObservableList<HashMap<Place, SimpleIntegerProperty>> dijkstraData = FXCollections.observableArrayList();


    public TextArea descArea;
    public ToggleButton startBtn;
    public ToggleButton endBtn;
    public ToggleButton defeatBtn;
    public Button djikstraBtn;
    public TextField nameMonsterField;
    public TextField armorMonsterField;
    public TextField hpMonsterField;
    public TextField attackMonsterField;
    public TableView djikstraTable;
    public TextField nameArea;
    public TextField idArea;
    public CheckBox isMonsterCheck;

    public MainController controller;

    public void setMainController(MainController controller) {
        this.controller = controller;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        descArea.setWrapText(true);
        nameArea.setPromptText("Enter place name");
        idArea.setPromptText("Enter place ID");
        nameMonsterField.setPromptText("Enter monster name");
        armorMonsterField.setPromptText("Enter monster armor");
        hpMonsterField.setPromptText("Enter monster HP");
        attackMonsterField.setPromptText("Enter monster attack damage");
        djikstraTable.setItems(dijkstraData);
        //updateTableColumns();
    }

    private void updateTableColumns() {
        djikstraTable.getColumns().clear();

        List<Place> allPlaces = controller.world.getPlaces();
        for (Place p : allPlaces) {
            TableColumn<HashMap<Place, SimpleIntegerProperty>, String> distanceColumn = new TableColumn<>(p.getName());
            distanceColumn.setCellValueFactory(cellData -> {
                SimpleIntegerProperty prop = cellData.getValue().get(p);
                int value = prop == null ? Integer.MAX_VALUE : prop.get();
                if (value == Integer.MAX_VALUE) return new SimpleStringProperty("∞");

                return new SimpleStringProperty(String.valueOf(value));
            });
            distanceColumn.setCellFactory(col -> new TableCell<HashMap<Place, SimpleIntegerProperty>, String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    setText(item);
                    if (empty || item == null) {
                        setStyle("");
                    } else if ("∞".equals(item)) {
                        setStyle("-fx-text-fill: red;");
                    } else {
                        try {
                            int value = Integer.parseInt(item);
                            if (value < 0) {
                                setStyle("-fx-text-fill: red;");
                            } else if (value == 0) {
                                setStyle("-fx-text-fill: green;");
                            } else {
                                setStyle("-fx-text-fill: black;");
                            }
                        } catch (NumberFormatException e) {
                            setStyle("-fx-text-fill: black;");
                        }
                    }
                }
            });
            distanceColumn.setPrefWidth(100);
            djikstraTable.getColumns().add(distanceColumn);
        }

        djikstraTable.setPlaceholder(new Text("No data available"));
    }

    public void loadNewPlace() {




        startBtn.selectedProperty().bindBidirectional(controller.contentController.selectedPlace.get().isStartProperty());
        endBtn.selectedProperty().bindBidirectional(controller.contentController.selectedPlace.get().isEndProperty());
        defeatBtn.selectedProperty().bindBidirectional(controller.contentController.selectedPlace.get().isDefeatProperty());
        isMonsterCheck.selectedProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal) {
                controller.contentController.selectedPlace.get().monsterProperty().set(new Monster("", 1, 0, 0));
            } else {
                controller.contentController.selectedPlace.get().monsterProperty().set(null);
            }
        });
        idArea.setDisable(true);
        idArea.setText(controller.contentController.selectedPlace.get().getId() + "");
        nameArea.textProperty().bindBidirectional(controller.contentController.selectedPlace.get().nameProperty());
        descArea.textProperty().bindBidirectional(controller.contentController.selectedPlace.get().descriptionProperty());
        nameMonsterField.disableProperty().bind(isMonsterCheck.selectedProperty().not());
        armorMonsterField.disableProperty().bind(isMonsterCheck.selectedProperty().not());
        hpMonsterField.disableProperty().bind(isMonsterCheck.selectedProperty().not());
        attackMonsterField.disableProperty().bind(isMonsterCheck.selectedProperty().not());

        nameMonsterField.textProperty().addListener((obs, oldVal, newVal) -> {
            System.out.println(controller.contentController.selectedPlace);
            System.out.println(controller.contentController.selectedPlace.get().monsterProperty().get());
            if (controller.contentController.selectedPlace.get().monsterProperty().get() != null) {
                controller.contentController.selectedPlace.get().monsterProperty().get().nomProperty().set(newVal);
            }
        });

        armorMonsterField.textProperty().addListener((obs, oldVal, newVal) -> {
            if (controller.contentController.selectedPlace.get().monsterProperty().get() != null) {
                try {

                    controller.contentController.selectedPlace.get().monsterProperty().get().armorProperty().set(
                            newVal.isEmpty() ? 0 : Math.max(0, Integer.parseInt(newVal)));
                } catch (NumberFormatException e) {

                    controller.contentController.selectedPlace.get().monsterProperty().get().armorProperty().set(0);
                }
            }
        });
        hpMonsterField.textProperty().addListener((obs, oldVal, newVal) -> {
            if (controller.contentController.selectedPlace.get().monsterProperty().get() != null) {
                try {
                    controller.contentController.selectedPlace.get().monsterProperty().get().maximumHPProperty().set(
                            newVal.isEmpty() ? 1 : Math.max(1, Integer.parseInt(newVal)));
                    controller.contentController.selectedPlace.get().monsterProperty().get().currentHPProperty().set(
                            newVal.isEmpty() ? 1 : Math.max(1, Integer.parseInt(newVal)));
                } catch(NumberFormatException e) {
                    controller.contentController.selectedPlace.get().monsterProperty().get().maximumHPProperty().set(1);
                    controller.contentController.selectedPlace.get().monsterProperty().get().currentHPProperty().set(1);
                }

            }
        });
        attackMonsterField.textProperty().addListener((obs, oldVal, newVal) -> {
            if (controller.contentController.selectedPlace.get().monsterProperty().get() != null) {
                try {
                    controller.contentController.selectedPlace.get().monsterProperty().get().attackProperty().set(
                            newVal.isEmpty() ? 0 : Math.max(0, Integer.parseInt(newVal)));
                } catch (NumberFormatException e) {
                    controller.contentController.selectedPlace.get().monsterProperty().get().attackProperty().set(0);
                }
            }
        });
        djikstraBtn.disableProperty().bind(controller.dijkstraRunningProperty());
        djikstraBtn.setOnAction(e -> {
            if (controller.dijkstraRunningProperty().get()) return;
            controller.launchDijkstra();
        });

    }

    @Override
    public void setup(Place place) {
        dijkstraData.clear();
        updateTableColumns();
    }

    @Override
    public void beforeLineFrom(Place place) {
        HashMap<Place, SimpleIntegerProperty> newLine = new HashMap<>();
        newLine.put(place, new SimpleIntegerProperty(0)); // place courant
        if (!dijkstraData.isEmpty()) {
            HashMap<Place, SimpleIntegerProperty> last = dijkstraData.get(dijkstraData.size() - 1);
            for (Map.Entry<Place, SimpleIntegerProperty> entry : last.entrySet()) {
                newLine.put(entry.getKey(), new SimpleIntegerProperty(entry.getValue().get()));
            }
        } else {
            for (Place p : controller.contentController.selectedPlace.get().getPaths().keySet()) {
                newLine.put(p, new SimpleIntegerProperty(Integer.MAX_VALUE));
            }
        }
        dijkstraData.add(newLine);
        updateTableColumns();
    }

    @Override
    public void beforeNewDistance(Place from, Place to) {
        if (dijkstraData.isEmpty()) {
            beforeLineFrom(from);
        }
        if (!dijkstraData.get(dijkstraData.size() - 1).containsKey(to)) {
            dijkstraData.get(dijkstraData.size() - 1).put(to, new SimpleIntegerProperty(Integer.MAX_VALUE));
        }
    }

    @Override
    public void newDistance(Place from, Place to, int distance) {
        if (!dijkstraData.isEmpty()) {
            dijkstraData.get(dijkstraData.size() - 1).get(to).set(distance);
        }
    }

    @Override
    public void afterNewDistance(Place from, Place to) {
        if (dijkstraData.isEmpty()) {
            beforeLineFrom(from);
        }
        if (!dijkstraData.get(dijkstraData.size() - 1).containsKey(to)) {
            dijkstraData.get(dijkstraData.size() - 1).put(to, new SimpleIntegerProperty(Integer.MAX_VALUE));
        }
        djikstraTable.refresh();
    }

    @Override
    public void afterLineFrom(Place current) {
        if (dijkstraData.isEmpty()) {
            beforeLineFrom(current);
        }
        djikstraTable.refresh();
    }

    @Override
    public void tearDown() {

        updateTableColumns();
        controller.dijkstraRunningProperty().set(false);
    }

    public void updatePlaceParameters() {
        loadNewPlace();
    }


    public void unloadPlace(Place place) {
        startBtn.selectedProperty().unbindBidirectional(controller.contentController.selectedPlace.get().isStartProperty());
        endBtn.selectedProperty().unbindBidirectional(controller.contentController.selectedPlace.get().isEndProperty());
        defeatBtn.selectedProperty().unbindBidirectional(controller.contentController.selectedPlace.get().isDefeatProperty());
        isMonsterCheck.selectedProperty().unbind();
        nameArea.textProperty().unbindBidirectional(controller.contentController.selectedPlace.get().nameProperty());
        descArea.textProperty().unbindBidirectional(controller.contentController.selectedPlace.get().descriptionProperty());
        nameMonsterField.textProperty().unbind();
        armorMonsterField.textProperty().unbind();
        hpMonsterField.textProperty().unbind();
        attackMonsterField.textProperty().unbind();
        idArea.setText("");
        startBtn.setSelected(false);
        endBtn.setSelected(false);
        defeatBtn.setSelected(false);
        isMonsterCheck.setSelected(false);
        nameArea.setText("");

        descArea.setText("");
        nameMonsterField.setText("");
        armorMonsterField.setText("");
        hpMonsterField.setText("");
        attackMonsterField.setText("");
        dijkstraData.clear();
        updateTableColumns();
    }
}
