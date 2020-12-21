package agh.cs.lab1.view.singleView;

import agh.cs.lab1.model.statistics.IStatisticsListener;
import agh.cs.lab1.model.statistics.StatisticsPackage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;


public class SingleViewController implements IStatisticsListener {

    private SingleView singleView;

    public void setSingleView(SingleView singleView) {
        this.singleView = singleView;
    }

    @FXML
    private Canvas canvas;

    @FXML
    private Button startButton;

    @FXML
    private Button stopButton;

    @FXML
    private Text animalAmount;
    
    @FXML
    private Text plantAmount;

    @FXML
    private Text averageEnergy;

    @FXML
    private Text averageChildren;

    @FXML
    private Text averageLifeExpectancy;

    @FXML
    private Text bestGenome;

    @FXML
    private Text tps;

    @FXML
    private Text trackedChildren;

    @FXML
    private Text trackedDescendants;

    @FXML
    private Text trackedDeath;

    @FXML
    private Text trackedGenome;

    @FXML
    private Button highlightButton;

    @FXML
    private Button statsButton;
    
    @FXML
    private void startButtonPressed(ActionEvent event){
        singleView.unpauseSimulation();
        startButton.setDisable(true);
        stopButton.setDisable(false);
        highlightButton.setDisable(true);
        statsButton.setDisable(true);
    }

    @FXML
    private void stopButtonPressed(ActionEvent event){
        singleView.pauseSimulation();
        stopButton.setDisable(true);
        startButton.setDisable(false);
        highlightButton.setDisable(false);
        statsButton.setDisable(false);
    }

    @FXML
    private void highlightButtonPressed(ActionEvent event){
        singleView.getSimulationInstance().highlightBestGenome();
    }

    @FXML
    private void statsButtonPressed(ActionEvent event){
        singleView.writeStatistics();
    }

    @FXML
    private void canvasClicked(MouseEvent event){
        singleView.getSimulationInstance().canvasClicked(event);
    }

    public Canvas getCanvas() {
        return canvas;
    }


    public void updateStatistics(StatisticsPackage statisticsPackage){
        animalAmount.setText(statisticsPackage.formattedAnimalAmount);
        plantAmount.setText(statisticsPackage.formattedPlantAmount);
        averageEnergy.setText(statisticsPackage.formattedAverageEnergy);
        averageChildren.setText(statisticsPackage.formattedAverageChildren);
        averageLifeExpectancy.setText(statisticsPackage.formattedAverageLifeExpectancy);
        bestGenome.setText(statisticsPackage.formattedBestGenome);
        trackedChildren.setText(statisticsPackage.formattedTrackedChildren);
        trackedDescendants.setText(statisticsPackage.formattedTrackedDescendants);
        trackedDeath.setText(statisticsPackage.formattedTrackedDeath);
        trackedGenome.setText(statisticsPackage.formattedTrackedGenome);
    }

    public void updateTPS(double tps){
        String tpsFormat = "Turns per second: \n%.2f";
        this.tps.setText(String.format(tpsFormat, tps));
    }
}
