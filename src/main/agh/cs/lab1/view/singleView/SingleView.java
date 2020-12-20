package agh.cs.lab1.view.singleView;

import agh.cs.lab1.view.simulation.AbstractSimulationView;
import agh.cs.lab1.view.simulation.SimulationInstance;
import com.google.gson.JsonSyntaxException;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SingleView extends AbstractSimulationView {
    private final AnimationTimer animationTimer;
    private final SingleViewController singleViewController;
    private SimulationInstance simulationInstance;


    public SingleView(Stage defaultStage) throws IOException {
        super(defaultStage);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("SingeView.fxml"));
        Parent root = loader.load();
        singleViewController = loader.getController();

        try {
            simulationInstance = new SimulationInstance(singleViewController.getCanvas(), singleViewController);
        } catch (IOException | JsonSyntaxException | NumberFormatException exception) {
            showErrorWindowAndQuit(exception);
        }
        singleViewController.setSimulationInstance(simulationInstance);

        getDefaultStage().setTitle("Simulation");
        getDefaultStage().setScene(new Scene(root, 1280, 720));


        animationTimer = new AnimationTimer() {
            private long previousSecond = 0;
            private int tps = 0;

            @Override
            public void handle(long time) {

                if (time / 1000000000 != previousSecond) {
                    previousSecond = time / 1000000000;
                    singleViewController.updateTPS(tps);
                    tps = 1;
                } else {
                    tps++;
                }

                simulationInstance.runTurn();
            }
        };
    }


    @Override
    public AnimationTimer getAnimationTimer() {
        return animationTimer;
    }
}
