package agh.cs.lab1.model.statistics;

import agh.cs.lab1.model.animal.Genome;

import java.util.Objects;

public class StatisticsPackage {
    public final int animalAmount, plantAmount, trackedChildren;
    public final long trackedDescendants, trackedDeath;
    public final boolean trackingAnimal;
    public final Genome bestGenome, trackedGenome;
    public final double averageChildren, averageLifeExpectancy, averageEnergy;

    public final String formattedAnimalAmount;
    public final String formattedPlantAmount;
    public final String formattedTrackedChildren;
    public final String formattedTrackedDescendants;
    public final String formattedTrackedDeath;
    public final String formattedBestGenome;
    public final String formattedTrackedGenome;
    public final String formattedAverageChildren;
    public final String formattedAverageLifeExpectancy;
    public final String formattedAverageEnergy;

    public StatisticsPackage(StatisticsTracker tracker) {
        this.animalAmount = tracker.getAnimalAmount();
        this.plantAmount = tracker.getPlantAmount();
        this.trackedChildren = tracker.getAmountOfChildrenForTrackedAnimal();
        this.trackedDescendants = tracker.getAmountOfDescendantsForTrackedAnimal();
        this.trackedDeath = tracker.getDeathTurnForTrackedAnimal();
        this.trackingAnimal = tracker.trackingAnimal();
        this.bestGenome = tracker.getBestGenome();
        this.trackedGenome = tracker.getTrackedGenome();
        this.averageChildren = tracker.getAverageChildrenAmount();
        this.averageLifeExpectancy = tracker.getAverageLifeExpectancy();
        this.averageEnergy = tracker.getAverageEnergyForLivingAnimals();

        String animalAmountFormat = "Animal amount: \n%d";
        formattedAnimalAmount = String.format(animalAmountFormat, animalAmount);
        String plantAmountFormat = "Plant amount: \n%d";
        formattedPlantAmount = String.format(plantAmountFormat, plantAmount);
        String bestGenomeFormat = "Most popular genome: \n%s";
        formattedBestGenome = String.format(bestGenomeFormat, Objects.requireNonNullElse(bestGenome, ""));
        String averageChildrenFormat = "Average children amount for living: \n%.2f";
        formattedAverageChildren = String.format(averageChildrenFormat, averageChildren);
        String averageLifeExpectancyFormat = "Average life expectancy for dead: \n%.2f";
        formattedAverageLifeExpectancy = String.format(averageLifeExpectancyFormat, averageLifeExpectancy);
        String averageEnergyFormat = "Average energy for living: \n%.2f";
        formattedAverageEnergy = String.format(averageEnergyFormat, averageEnergy);
        if(trackingAnimal){
            String trackedGenomeFormat = "Tracked animal's genome: \n%s";
            formattedTrackedGenome = String.format(trackedGenomeFormat, trackedGenome);
            String trackedChildrenFormat = "Tracked animal's children amount: \n%d";
            formattedTrackedChildren = String.format(trackedChildrenFormat, trackedChildren);
            String trackedDescendantsFormat = "Tracked animal's descendants amount: \n%d";
            formattedTrackedDescendants = String.format(trackedDescendantsFormat, trackedDescendants);
            String trackedDeathFormat = "Tracked animal's turn of death: \n%d";
            String trackedDeathAlive = "Tracked animal's turn of death: \nAnimal is still alive";
            formattedTrackedDeath = trackedDeath == -1 ? trackedDeathAlive : String.format(trackedDeathFormat, trackedDeath);
        }else{
            String noTrackedAnimal = "No tracked animal\n";
            formattedTrackedGenome = noTrackedAnimal;
            formattedTrackedChildren = noTrackedAnimal;
            formattedTrackedDescendants = noTrackedAnimal;
            formattedTrackedDeath = noTrackedAnimal;
        }

    }


}
