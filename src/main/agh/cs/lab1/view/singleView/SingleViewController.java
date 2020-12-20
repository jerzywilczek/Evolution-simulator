package agh.cs.lab1.view.singleView;

import agh.cs.lab1.model.engine.IStatisticsListener;
import agh.cs.lab1.model.engine.StatisticsTracker;
import agh.cs.lab1.view.simulation.SimulationInstance;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;


public class SingleViewController implements IStatisticsListener {

    private SimulationInstance simulationInstance;

    private final String animalAmountFormat = "Animal amount: \n%d";
    private final String plantAmountFormat = "Plant amount: \n%d";
    private final String averageEnergyFormat = "Average energy for living: \n%.2f";
    private final String averageChildrenFormat = "Average children amount for living: \n%.2f";
    private final String averageLifeExpectancyFormat = "Average life expectancy for dead: \n%.2f";
    private final String bestGenomeFormat = "Most popular genome: \n%s";
    private final String noBestGenome = "There is no most popular genome\n";
    private final String tpsFormat = "Turns per second: \n%.2f";
    private final String trackedChildrenFormat = "Tracked animal's children amount: \n%d";
    private final String trackedDescendantsFormat = "Tracked animal's descendants amount: \n%d";
    private final String trackedDeathFormat = "Tracked animal's turn of death: \n%d";
    private final String trackedDeathAlive = "Tracked animal's turn of death: \nAnimal is still alive";
    private final String trackedGenomeFormat = "Tracked animal's genome: \n%s";
    private final String noTrackedAnimal = "No tracked animal\n";

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
    private void startButtonPressed(ActionEvent event){
        SingleView.getInstance().unpauseSimulation();
        startButton.setDisable(true);
        stopButton.setDisable(false);
        highlightButton.setDisable(true);
    }

    @FXML
    private void stopButtonPressed(ActionEvent event){
        SingleView.getInstance().pauseSimulation();
        stopButton.setDisable(true);
        startButton.setDisable(false);
        highlightButton.setDisable(false);
    }

    @FXML
    private void highlightButtonPressed(ActionEvent event){
        simulationInstance.highlightBestGenome();
    }

    private void canvasClicked(MouseEvent event){
        simulationInstance.canvasClicked(event);
    }

    public Canvas getCanvas() {
        return canvas;
    }

    public void setSimulationInstance(SimulationInstance simulationInstance) {
        this.simulationInstance = simulationInstance;
    }

    public void updateStatistics(StatisticsTracker tracker){

        animalAmount.setText(String.format(animalAmountFormat, tracker.getAnimalAmount()));
        plantAmount.setText(String.format(plantAmountFormat, tracker.getPlantAmount()));
        averageEnergy.setText(String.format(averageEnergyFormat, tracker.getAverageEnergyForLivingAnimals()));
        averageChildren.setText(String.format(averageChildrenFormat, tracker.getAverageChildrenAmount()));
        averageLifeExpectancy.setText(String.format(averageLifeExpectancyFormat, tracker.getAverageLifeExpectancy()));

        if(tracker.getBestGenome() == null)
            bestGenome.setText(noBestGenome);
        else
            bestGenome.setText(String.format(bestGenomeFormat, tracker.getBestGenome().toString()));

        if(tracker.trackingAnimal()){
            trackedChildren.setText(String.format(trackedChildrenFormat, tracker.getAmountOfChildrenForTrackedAnimal()));
            trackedDescendants.setText(String.format(trackedDescendantsFormat, tracker.getAmountOfDescendantsForTrackedAnimal()));

            if(tracker.getDeathTurnForTrackedAnimal() == -1)
                trackedDeath.setText(trackedDeathAlive);
            else
                trackedDeath.setText(String.format(trackedDeathFormat, tracker.getDeathTurnForTrackedAnimal()));

            trackedGenome.setText(String.format(trackedGenomeFormat, tracker.getTrackedGenome().toString()));
        }else{
            trackedChildren.setText(noTrackedAnimal);
            trackedDescendants.setText(noTrackedAnimal);
            trackedDeath.setText(noTrackedAnimal);
            trackedGenome.setText(noTrackedAnimal);
        }
    }

    public void updateTPS(double tps){
        this.tps.setText(String.format(tpsFormat, tps));
    }
}
