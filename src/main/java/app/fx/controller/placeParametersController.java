package app.fx.controller;

import app.model.entity.Monster;
import javafx.beans.binding.Bindings;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import javafx.scene.input.MouseEvent;
import java.net.URL;
import java.util.ResourceBundle;

public class placeParametersController implements Initializable {
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
//        listenerForMonsterFields();
    }

//    private void listenerForMonsterFields() {
//
//        nameMonsterField.textProperty().addListener((obs, oldVal, newVal) -> {
//            System.out.println(controller.contentController.selectedPlace);
//            if (controller.contentController.selectedPlace.monsterProperty().get() != null) {
//                controller.contentController.selectedPlace.monsterProperty().set(
//                        new Monster(newVal,
//                                armorMonsterField.getText().isEmpty() ? 0 : Math.max(0,Integer.parseInt(armorMonsterField.getText())),
//                                hpMonsterField.getText().isEmpty() ? 1 : Math.max(1,Integer.parseInt(hpMonsterField.getText())),
//                                attackMonsterField.getText().isEmpty() ? 0 : Math.max(0,Integer.parseInt(attackMonsterField.getText()))));
//            }
//        });
//        armorMonsterField.textProperty().addListener((obs, oldVal, newVal) -> {
//            System.out.println(controller.contentController.selectedPlace);
//            if (controller.contentController.selectedPlace.monsterProperty().get() != null) {
//                controller.contentController.selectedPlace.monsterProperty().set(
//                        new Monster(nameMonsterField.getText(),
//                                newVal.isEmpty() ? 0 : Math.max(0,Integer.parseInt(newVal)),
//                                hpMonsterField.getText().isEmpty() ? 1 : Math.max(1,Integer.parseInt(hpMonsterField.getText())),
//                                attackMonsterField.getText().isEmpty() ? 0 : Math.max(0,Integer.parseInt(attackMonsterField.getText()))));
//                armorMonsterField.setText(newVal);
//
//            }
//        });
//        hpMonsterField.textProperty().addListener((obs, oldVal, newVal) -> {
//            System.out.println(controller.contentController.selectedPlace);
//            if (controller.contentController.selectedPlace.monsterProperty().get() != null) {
//                controller.contentController.selectedPlace.monsterProperty().set(
//                        new Monster(nameMonsterField.getText(),
//                                armorMonsterField.getText().isEmpty() ? 0 : Math.max(0,Integer.parseInt(armorMonsterField.getText())),
//                                newVal.isEmpty() ? 1 : Math.max(1,Integer.parseInt(newVal)),
//                                attackMonsterField.getText().isEmpty() ? 0 : Math.max(0,Integer.parseInt(attackMonsterField.getText()))));
//                hpMonsterField.setText(newVal);
//            }
//        });
//        attackMonsterField.textProperty().addListener((obs, oldVal, newVal) -> {
//            System.out.println(controller.contentController.selectedPlace);
//            if (controller.contentController.selectedPlace.monsterProperty().get() != null) {
//                controller.contentController.selectedPlace.monsterProperty().set(
//                        new Monster(nameMonsterField.getText(),
//                                armorMonsterField.getText().isEmpty() ? 0 : Math.max(0,Integer.parseInt(armorMonsterField.getText())),
//                                hpMonsterField.getText().isEmpty() ? 1 : Math.max(1,Integer.parseInt(hpMonsterField.getText())),
//                                newVal.isEmpty() ? 0 : Math.max(0,Integer.parseInt(newVal))));
//                attackMonsterField.setText(newVal);
//            }
//        });
//    }

    public void loadNewPlace() {
        startBtn.selectedProperty().bindBidirectional(controller.contentController.selectedPlace.isStartProperty());
        endBtn.selectedProperty().bindBidirectional(controller.contentController.selectedPlace.isEndProperty());
        defeatBtn.selectedProperty().bindBidirectional(controller.contentController.selectedPlace.isDefeatProperty());
        isMonsterCheck.selectedProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal) {
                controller.contentController.selectedPlace.monsterProperty().set(new Monster("", 1, 0, 0));
            } else {
                controller.contentController.selectedPlace.monsterProperty().set(null);
            }
        });
        idArea.setDisable(true);
        idArea.setText(controller.contentController.selectedPlace.getId() + "");
        nameArea.textProperty().bindBidirectional(controller.contentController.selectedPlace.nameProperty());
        descArea.textProperty().bindBidirectional(controller.contentController.selectedPlace.descriptionProperty());
        nameMonsterField.disableProperty().bind(isMonsterCheck.selectedProperty().not());
        armorMonsterField.disableProperty().bind(isMonsterCheck.selectedProperty().not());
        hpMonsterField.disableProperty().bind(isMonsterCheck.selectedProperty().not());
        attackMonsterField.disableProperty().bind(isMonsterCheck.selectedProperty().not());

        nameMonsterField.textProperty().addListener((obs, oldVal, newVal) -> {
            System.out.println(controller.contentController.selectedPlace);
            System.out.println(controller.contentController.selectedPlace.monsterProperty().get());
            if (controller.contentController.selectedPlace.monsterProperty().get() != null) {
                controller.contentController.selectedPlace.monsterProperty().get().nomProperty().set(newVal);
            }
        });

        armorMonsterField.textProperty().addListener((obs, oldVal, newVal) -> {
            if (controller.contentController.selectedPlace.monsterProperty().get() != null) {
                try {

                    controller.contentController.selectedPlace.monsterProperty().get().armorProperty().set(
                            newVal.isEmpty() ? 0 : Math.max(0, Integer.parseInt(newVal)));
                } catch (NumberFormatException e) {

                    controller.contentController.selectedPlace.monsterProperty().get().armorProperty().set(0);
                }
            }
        });
        hpMonsterField.textProperty().addListener((obs, oldVal, newVal) -> {
            if (controller.contentController.selectedPlace.monsterProperty().get() != null) {
                try {
                    controller.contentController.selectedPlace.monsterProperty().get().maximumHPProperty().set(
                            newVal.isEmpty() ? 1 : Math.max(1, Integer.parseInt(newVal)));
                    controller.contentController.selectedPlace.monsterProperty().get().currentHPProperty().set(
                            newVal.isEmpty() ? 1 : Math.max(1, Integer.parseInt(newVal)));
                } catch(NumberFormatException e) {
                    controller.contentController.selectedPlace.monsterProperty().get().maximumHPProperty().set(1);
                    controller.contentController.selectedPlace.monsterProperty().get().currentHPProperty().set(1);
                }

            }
        });
        attackMonsterField.textProperty().addListener((obs, oldVal, newVal) -> {
            if (controller.contentController.selectedPlace.monsterProperty().get() != null) {
                try {
                    controller.contentController.selectedPlace.monsterProperty().get().attackProperty().set(
                            newVal.isEmpty() ? 0 : Math.max(0, Integer.parseInt(newVal)));
                } catch (NumberFormatException e) {
                    controller.contentController.selectedPlace.monsterProperty().get().attackProperty().set(0);
                }
            }
        });

    }
}
