package app.fx.controller;

import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.ResourceBundle;

public class worldParametersController implements Initializable {
    public TextField nameWorld;
    public TextField nbPlace;
    public Slider sliderDebut;
    public Label labelDebut;
    public Slider sliderVictoire;
    public Label labelVictoire;
    public Slider sliderDefaite;
    public Label labelDefaite;
    public Slider sliderAutre;
    public Label labelAutre;
    public Slider sliderCouverture;
    public Label labelCouverture;
    public Slider sliderMonstre;
    public Label labelMonstre;
    public CheckBox iaGeneratorCheck;

    public MainController controller;

    public void setMainController(MainController controller) {
        this.controller = controller;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }


    public void setNbPlace() {
        nbPlace.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                Integer.parseInt(newValue);
            } catch (Exception e) {
                nbPlace.setText(oldValue);
            }
            nbPlace.setText(newValue);
        });
    }

    public void gererSliderDebut() {
        sliderDebut.setMax(100);
        sliderDebut.setMin(0);
        labelDebut.setText(sliderDebut.getValue() + "% (" + (Integer.parseInt(nbPlace.getText())*(sliderDebut.getValue()/100)) + ")");
        if (sliderAutre.getValue() > 0) {
            sliderDebut.valueProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue.doubleValue() <= sliderAutre.getValue()){
                    this.sliderDebut.setValue(Math.round(newValue.doubleValue()));
                } else {
                    this.sliderDebut.setValue(Math.round(oldValue.doubleValue()));
                }
            });
        }
    }
    public void gererSliderVictoire() {
        sliderVictoire.setMax(100);
        sliderVictoire.setMin(0);
        labelVictoire.setText(sliderVictoire.getValue() + "% (" + (Integer.parseInt(nbPlace.getText())*(sliderVictoire.getValue()/100)) + ")");
        if (sliderAutre.getValue() > 0) {
            sliderVictoire.valueProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue.doubleValue() <= sliderAutre.getValue()){
                    this.sliderVictoire.setValue(Math.round(newValue.doubleValue()));
                } else {
                    this.sliderVictoire.setValue(Math.round(oldValue.doubleValue()));
                }
            });
        }
    }

    public void gererSliderDefaite() {
        sliderDefaite.setMax(100);
        sliderDefaite.setMin(0);
        labelDefaite.setText(sliderDefaite.getValue() + "% (" + (Integer.parseInt(nbPlace.getText())*(sliderDefaite.getValue()/100)) + ")");
        if (sliderAutre.getValue() > 0) {
            sliderDefaite.valueProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue.doubleValue() <= sliderAutre.getValue()){
                    this.sliderDefaite.setValue(Math.round(newValue.doubleValue()));
                } else {
                    this.sliderDefaite.setValue(Math.round(oldValue.doubleValue()));
                }
            });
        }
    }

    public void gererSliderAutre() {
        sliderAutre.setMax(100);
        sliderAutre.setMin(0);
        labelAutre.setText(sliderAutre.getValue() + "% (" + (Integer.parseInt(nbPlace.getText())*(sliderAutre.getValue()/100)) + ")");
        if (sliderDebut.getValue() + sliderDefaite.getValue() + sliderVictoire.getValue() <= 100) {
            sliderAutre.setValue(100 - (sliderDebut.getValue() + sliderDefaite.getValue() + sliderVictoire.getValue()));
        } else {
            labelDebut.setTextFill(Color.RED);
            sliderDebut.setStyle("-fx-control-inner-background: red;");
            sliderDebut.setValue(0);
        }
    }

    public void gererSliderCouverture() {
        sliderCouverture.setMax(100);
        sliderCouverture.setMin(0);
        labelCouverture.setText(sliderCouverture.getValue() + "% (≈ " + calcul(Integer.parseInt(nbPlace.getText()), sliderCouverture.getValue()/100) + ")");
    }

    // Calcul du nombre de chemin par rapport au pourcentage
    private int calcul(int nbtotalplace, double pourcentage) {
        int result = 0;
        for (int i = 1; i < nbtotalplace; i++) {
            result = result + i;
        }
        return (int)(result * pourcentage);
    }

    public void gererSliderMonstre(){
        sliderMonstre.setMax(100);
        sliderMonstre.setMin(0);
        labelMonstre.setText(sliderMonstre.getValue() + "% (" + (Integer.parseInt(nbPlace.getText())*(sliderMonstre.getValue()/100) + ")"));
    }


}
