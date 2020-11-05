package agh.cs.lab1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class SimulationEngine implements IEngine {

    private MoveDirection[] directions;
    private List<Animal> animals;
    private IWorldMap map;

    public SimulationEngine(MoveDirection[] directions, IWorldMap map, Vector2d[] positions){
        this.directions = directions.clone();
        this.map = map;
        animals = new ArrayList<>(
                Arrays
                .stream(positions)
                .map(v -> new Animal(map, v))
                .filter(map::place)
                .collect(Collectors.toList())
        );
    }


    @Override
    public void run() {
        if (animals.size() == 0) return;
        for (int i = 0; i < directions.length; i++) {
            animals.get(i % animals.size()).move(directions[i]);
        }
    }
}
