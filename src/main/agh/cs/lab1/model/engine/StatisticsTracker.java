package agh.cs.lab1.model.engine;

import agh.cs.lab1.model.animal.*;
import agh.cs.lab1.model.map.WorldMap;
import org.jetbrains.annotations.NotNull;

import java.util.*;


public class StatisticsTracker implements IAnimalSpawnedObserver, IAnimalDiedObserver, IEnergyChangeObserver, IChildBornObserver {
    private final WorldMap map;
    private int animalAmount = 0;
    private int plantAmount = 0;
    private long deadAnimalAmount = 0;
    private long deadAnimalAgeSum = 0;
    private long aliveAnimalEnergySum = 0;
    private long aliveAnimalChildrenAmountSum = 0;

    private Map<Genome, Integer> genomeAmount = new HashMap<>();
    private SortedSet<GenomeAmountEntry> genomeAmountEntries = new TreeSet<>();
    private Genome bestGenome;

    private long amountOfDescendantsForTrackedAnimal = -1;
    private int amountOfChildrenForTrackedAnimal = -1;
    private long deathTurnForTrackedAnimal = -1;
    private Animal trackedAnimal;

//    TODO dominating genomes

    private static class GenomeAmountEntry implements Comparable<GenomeAmountEntry> {
        public final Genome genome;
        public final int amount;

        public GenomeAmountEntry(Genome genome, int amount) {
            this.genome = genome;
            this.amount = amount;
        }

        @Override
        public int compareTo(@NotNull StatisticsTracker.GenomeAmountEntry other) {
            if (amount != other.amount) return Integer.compare(amount, other.amount);
            return Genome.compare(genome, other.genome);
        }
    }

    private void addGenome(Genome genome) {
        if (genomeAmount.containsKey(genome)) {
            Integer amount = genomeAmount.get(genome);
            genomeAmountEntries.remove(new GenomeAmountEntry(genome, amount));
            genomeAmount.put(genome, amount + 1);
            genomeAmountEntries.add(new GenomeAmountEntry(genome, amount + 1));
        } else {
            genomeAmount.put(genome, 1);
            genomeAmountEntries.add(new GenomeAmountEntry(genome, 1));
        }

    }

    private void removeGenome(Genome genome) {
        int amount = genomeAmount.get(genome);
        genomeAmountEntries.remove(new GenomeAmountEntry(genome, amount));
        if (amount == 1) {
            genomeAmount.remove(genome);
        } else {
            genomeAmount.put(genome, amount - 1);
            genomeAmountEntries.add(new GenomeAmountEntry(genome, amount - 1));
        }
    }

    public StatisticsTracker(WorldMap map, SimulationEngine engine) {
        this.map = map;
        engine.addAnimalSpawnedObserver(this);
        updateStatistics();
    }


    @Override
    public void animalSpawned(Animal animal) {
        animalAmount++;
        aliveAnimalEnergySum += animal.getEnergy();
        addGenome(animal.getGenome());

        animal.addEnergyChangeObserver(this);
        animal.addChildBornObserver(this);
        animal.addAnimalDiedObserver(this);
    }

    @Override
    public void animalDied(Animal animal, long deathTurn) {
        animalAmount--;
        deadAnimalAmount++;
        deadAnimalAgeSum += animal.getAge();
        aliveAnimalEnergySum -= animal.getEnergy();
        aliveAnimalChildrenAmountSum -= animal.getChildrenAmount();
        removeGenome(animal.getGenome());

        animal.removeEnergyChangeObserver(this);
        animal.removeChildBornObserver(this);
        animal.removeAnimalDiedObserver(this);
    }

    @Override
    public void energyChanged(int oldEnergy, int newEnergy, Animal animal) {
        aliveAnimalEnergySum += newEnergy - oldEnergy;
    }

    @Override
    public void childBorn(Animal parent, Animal child) {
        aliveAnimalChildrenAmountSum++;
    }

    public long getAmountOfDescendantsForTrackedAnimal() {
        return amountOfDescendantsForTrackedAnimal;
    }

    public int getAmountOfChildrenForTrackedAnimal() {
        return amountOfChildrenForTrackedAnimal;
    }

    public long getDeathTurnForTrackedAnimal() {
        return deathTurnForTrackedAnimal;
    }

    public boolean trackingAnimal() {
        return trackedAnimal != null;
    }

    public int getAnimalAmount() {
        return animalAmount;
    }

    public int getPlantAmount() {
        return plantAmount;
    }

    public double getAverageEnergyForLivingAnimals() {
        if (animalAmount == 0) return 0;
        return (double) aliveAnimalEnergySum / (double) animalAmount;
    }

    public double getAverageLifeExpectancy() {
        if (deadAnimalAmount == 0) return 0;
        return (double) deadAnimalAgeSum / (double) deadAnimalAmount;
    }

    public double getAverageChildrenAmount() {
        if (animalAmount == 0) return 0;
        return (double) aliveAnimalChildrenAmountSum / (double) animalAmount;
    }

    public Genome getBestGenome(){
        if(genomeAmountEntries.isEmpty()) return null;
        return genomeAmountEntries.last().genome;
    }

    public Genome getTrackedGenome(){
        if(trackingAnimal()) return trackedAnimal.getGenome();
        else return null;
    }

    public void setTrackedAnimal(Animal trackedAnimal) {
        this.trackedAnimal = trackedAnimal;
    }

    public void updateStatistics() {
        plantAmount = map.getPlantAmount();
        if (trackingAnimal()) {
            amountOfChildrenForTrackedAnimal = trackedAnimal.getChildrenAmount();
            amountOfDescendantsForTrackedAnimal = trackedAnimal.getDescendantAmount();
            deathTurnForTrackedAnimal = trackedAnimal.getDeathTurn();
        }
    }
}
