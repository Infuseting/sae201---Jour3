package app.fx.controller;

import app.ai.world.WorldGenerator;
import app.model.map.World;
import javafx.beans.binding.Bindings;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.paint.Color;

import javax.swing.*;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.CompletableFuture;

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
    public Button generateBtn;
    public ProgressBar progressBar;
    public MainController controller;

    public void setMainController(MainController controller) {
        this.controller = controller;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setNbPlace();
        // Initialisation des sliders
        sliderDebut.setMin(0); sliderDebut.setMax(100);
        sliderVictoire.setMin(0); sliderVictoire.setMax(100);
        sliderDefaite.setMin(0); sliderDefaite.setMax(100);
        sliderAutre.setMin(0); sliderAutre.setMax(100);
        sliderAutre.setDisable(true);

        // Ajout des listeners
        sliderDebut.valueProperty().addListener((obs, oldVal, newVal) -> { updateSliders(); updateAllSliders();});
        sliderVictoire.valueProperty().addListener((obs, oldVal, newVal) -> { updateSliders(); updateAllSliders();});
        sliderDefaite.valueProperty().addListener((obs, oldVal, newVal) -> { updateSliders(); updateAllSliders();});
        nbPlace.textProperty().addListener((obs, oldVal, newVal) -> { updateSliders(); updateAllSliders();});
        sliderCouverture.valueProperty().addListener((obs, oldVal, newVal) -> { gererSliderCouverture(); updateAllSliders();});
        sliderMonstre.valueProperty().addListener((obs, oldVal, newVal) -> { gererSliderMonstre(); updateAllSliders();});
        generateBtn.setOnAction(event -> generation());
        updateSliders();
    }


    public void setNbPlace() {
        nbPlace.setText("2");
        nbPlace.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                nbPlace.setText(Integer.parseInt(newValue.isEmpty() ? "0" : newValue) + "");
            } catch (Exception e) {
                nbPlace.setText(oldValue.replaceAll("[^0-9]", ""));
            }

        });
    }

    private void updateSliders() {
        int nb = 1;
        try { nb = Integer.parseInt(nbPlace.getText()); } catch (Exception ignored) {}
        double debut = sliderDebut.getValue();
        double victoire = sliderVictoire.getValue();
        double defaite = sliderDefaite.getValue();
        double somme = debut + victoire + defaite;
        double neutre = Math.max(0, 100 - somme);

        // Mise à jour des labels
        labelDebut.setText(String.format("%.0f%% (%d)", debut, Math.round(nb * debut / 100)));
        labelVictoire.setText(String.format("%.0f%% (%d)", victoire, Math.round(nb * victoire / 100)));
        labelDefaite.setText(String.format("%.0f%% (%d)", defaite, Math.round(nb * defaite / 100)));
        labelAutre.setText(String.format("%.0f%% (%d)", neutre, Math.round(nb * neutre / 100)));

        // Mise à jour du slider neutre
        sliderAutre.setValue(neutre);

        // Gestion des couleurs
        boolean depasse = somme > 100;
        String rouge = "-fx-control-inner-background: red;";
        String normal = "";

        labelDebut.setTextFill(depasse ? Color.RED : Color.BLACK);
        labelVictoire.setTextFill(depasse ? Color.RED : Color.BLACK);
        labelDefaite.setTextFill(depasse ? Color.RED : Color.BLACK);
        labelAutre.setTextFill(depasse ? Color.RED : Color.BLACK);

        sliderDebut.setStyle(depasse ? rouge : normal);
        sliderVictoire.setStyle(depasse ? rouge : normal);
        sliderDefaite.setStyle(depasse ? rouge : normal);
        sliderAutre.setStyle(depasse ? rouge : normal);

        if (depasse) {
            sliderAutre.setValue(0);
        }
    }

    public void updateAllSliders() {
        updateSliders();
        gererSliderCouverture();
        gererSliderMonstre();
        gererGenerationIA();
        boolean activable = sliderDebut.getValue() + sliderVictoire.getValue() + sliderDefaite.getValue() <= 100;
        int nb = 1;
        try { nb = Integer.parseInt(nbPlace.getText()); } catch (Exception ignored) {}
        activable = activable && nb >= 2;
        generateBtn.setDisable(!activable);
    }

    public void gererSliderCouverture() {
        sliderCouverture.setMax(100);
        sliderCouverture.setMin(0);
        int nbPlaces = 1;
        try { nbPlaces = Integer.parseInt(nbPlace.getText()); } catch (Exception ignored) {}
        double pourcentage = sliderCouverture.getValue();
        int totalChemins = nbPlaces * (nbPlaces - 1) / 2;
        int cheminsEstimes = (int) Math.round(totalChemins * (pourcentage / 100.0));
        labelCouverture.setText(String.format("%.0f%% (≈ %d)", pourcentage, cheminsEstimes));
    }

    public void gererSliderMonstre() {
        sliderMonstre.setMax(100);
        sliderMonstre.setMin(0);
        int nbPlaces = 1;
        try { nbPlaces = Integer.parseInt(nbPlace.getText()); } catch (Exception ignored) {}
        double pourcentage = sliderMonstre.getValue();
        int monstresEstimes = (int) Math.round(nbPlaces * (pourcentage / 100.0));
        labelMonstre.setText(String.format("%.0f%% (≈ %d)", pourcentage, monstresEstimes));
    }

    public void gererGenerationIA() {
        if (sliderDebut.getValue() + sliderVictoire.getValue() + sliderDefaite.getValue() <= 100
                && Integer.parseInt(nbPlace.getText()) >= 2) {
            iaGeneratorCheck.setDisable(false);
        } else {
            iaGeneratorCheck.setDisable(true);
        }
    }

    public void generation() {
        if (generateBtn.isDisabled()) return;
        int nbPlaces = Integer.parseInt(nbPlace.getText());
        boolean withIA = iaGeneratorCheck.isSelected();
        progressBar.setVisible(withIA);
        progressBar.setProgress(0);
        CompletableFuture<World> worldGenerator = CompletableFuture.supplyAsync(() -> {
            return WorldGenerator.builder()
                    .name(nameWorld.getText())
                    .nbPlace(nbPlaces)
                    .percentageStartPoint(sliderDebut.getValue() / 100.0)
                    .percentageDefeatPoint(sliderDefaite.getValue() / 100.0)
                    .percentageMonster(sliderMonstre.getValue() / 100.0)
                    .withAIGeneration(withIA)
                    .build()
                    .generate(place -> {
                        if (withIA) {
                            javafx.application.Platform.runLater(() -> {
                                System.out.println(progressBar.getProgress());
                                double progress = progressBar.getProgress() + 1.0 / nbPlaces;
                                progressBar.setProgress(Math.min(progress, 1.0));
                            });
                        }
                    });
        });

        worldGenerator.thenAccept(world -> {
            javafx.application.Platform.runLater(() -> {progressBar.setProgress(1.0); controller.world = world;
                controller.onChangeWorld(); });


        }).exceptionally(ex -> {
            System.err.println("Erreur lors de la génération du monde : " + ex.getMessage());
            return null;
        });
        progressBar.setVisible(false);
    }
}
