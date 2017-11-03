package Algorithm.CMeansClustering;

import Algorithm.Centroid;
import Algorithm.ClusteringAlgorithm;
import DataLoader.Instance;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class CMeans implements ClusteringAlgorithm {
    private int c;

    public CMeans(int c) {
        this.c = c;
    }

    @Override
    public Map<Instance, String> test(List<Instance> testSet) {
        return null;
    }

    @Override
    public void train(List<Instance> trainSet) {
        List<Centroid> centroids = assignRandomlyToCentroids(trainSet);
        Map<Centroid, List<Double>> centroidsMeans = getCentroidsMeans(centroids);
        boolean lastChanged = true;
        while (lastChanged) {
            clearCentroids(centroidsMeans.keySet());
            List<Centroid> newCentroids = assignToNewCentroids(trainSet, centroidsMeans);
            Map<Centroid, List<Double>> newCentroidsMeans = getCentroidsMeans(newCentroids);
            lastChanged = !centroidsMeans.equals(newCentroidsMeans);
            centroidsMeans = newCentroidsMeans;
        }
    }

    private List<Centroid> assignToNewCentroids(List<Instance> trainSet, Map<Centroid, List<Double>> centroidsMeans) {
        trainSet.forEach(instance -> assignToNewCentroid(instance, centroidsMeans));
        return new ArrayList<>(centroidsMeans.keySet());
    }

    private void assignToNewCentroid(Instance instance, Map<Centroid, List<Double>> centroidsMeans) {
        Map<Centroid, Double> centroidsDistances = centroidsMeans.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey,
                        entry -> getEuclideanDistance(instance.getDoubleAttributes(), entry.getValue())));
        Centroid theClosedCentroid = centroidsDistances.entrySet().stream()
                .sorted(Map.Entry.comparingByValue())
                .findFirst()
                .map(Map.Entry::getKey)
                .orElseThrow(RuntimeException::new);
        theClosedCentroid.addInstance(instance);
    }

    private void clearCentroids(Set<Centroid> centroids) {
        centroids.forEach(Centroid::clear);
    }

    private Map<Centroid, List<Double>> getCentroidsMeans(List<Centroid> centroids) {
        return centroids.stream()
                .collect(Collectors.toMap(Function.identity(), Centroid::getMeans));
    }

    private List<Centroid> assignRandomlyToCentroids(List<Instance> instances) {
        List<Instance> randomlyOrderedInstances = getRandomlyOrdered(instances);
        return IntStream.range(0, c)
                .mapToObj(i -> new Centroid(randomlyOrderedInstances.get(i)))
                .collect(Collectors.toList());
    }

    private List<Instance> getRandomlyOrdered(List<Instance> instances) {
        List<Instance> result = new ArrayList<>(instances);
        Collections.shuffle(result);
        return result;
    }

    private double getEuclideanDistance(List<Double> p1, List<Double> p2) {
        if (p1.size() != p2.size())
            throw new RuntimeException("getEuclideanDistance: Niezgodność rozmiarów");
        return Math.sqrt(IntStream.range(0, p1.size())
                .mapToDouble(i -> Math.pow(p1.get(i) - p2.get(i), 2))
                .sum());
    }
}
