package agh.cs.lab1.model.animal;

import java.util.Arrays;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Genome {

    private final int[] genes;

    /**
     * Creates a random genome
     */
    public Genome() {
        genes = Stream.of(
                IntStream.range(0, 8),
                new Random().ints(24, 0, 8))
                .flatMapToInt(s -> s)
                .sorted()
                .toArray();
    }


    /**
     * Generates child genome from parent genomes
     *
     * @param parent1 genome of the first parent
     * @param parent2 genome of the second parent
     */
    public Genome(Genome parent1, Genome parent2) {
        Random random = new Random();
//        choose two places where parent genomes are divided
        int cut1 = random.nextInt(30) + 1; // <1, 30>
        int cut2 = random.nextInt(32 - cut1 - 1) + cut1 + 1; // <cut1 + 1, 31>
        Genome[] parents = {parent1, parent2};
//        choose which slices come from which parent
        int weakParent = random.nextInt(2);
        int[] choices = new int[3];
        Arrays.fill(choices, 1 - weakParent);
        choices[random.nextInt(3)] = weakParent;
//        combine correct slices from parents
        genes = Stream.of(
                Arrays.stream(parents[choices[0]].genes, 0, cut1),
                Arrays.stream(parents[choices[1]].genes, cut1, cut2),
                Arrays.stream(parents[choices[2]].genes, cut2, 32))
                .flatMapToInt(s -> s)
                .toArray();
//        count genes
        Map<Integer, Long> geneAmount = Arrays.stream(genes)
                .boxed()
                .collect(Collectors.groupingBy(gene -> gene, Collectors.counting()));
//        gen missing genes
        IntStream.range(0, 8)
                .filter(gene -> !geneAmount.containsKey(gene))
                .forEach(missingGene -> {
                    int randomIndex;
                    do {
                        randomIndex = random.nextInt(32);
                    } while (!(geneAmount.containsKey(genes[randomIndex]) && geneAmount.get(genes[randomIndex]) > 1));
                    geneAmount.put(genes[randomIndex], geneAmount.get(genes[randomIndex]) - 1);
                    genes[randomIndex] = missingGene;
                });
        Arrays.sort(genes);
    }

    /**
     * Choose rotation for an animal based on genome
     *
     * @return rotation as an int (amount of 45 deg clockwise turns)
     */
    public int chooseRotation() {
        return genes[new Random().nextInt(32)];
    }

    public static int compare(Genome a, Genome b){
        return Arrays.compare(a.genes, b.genes);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof Genome))
            return false;
        return Arrays.equals(genes, ((Genome) obj).genes);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(genes);
    }

    @Override
    public String toString() {
        return Arrays.toString(genes);
    }
}
