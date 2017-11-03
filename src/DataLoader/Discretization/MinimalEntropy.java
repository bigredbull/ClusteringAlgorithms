package DataLoader.Discretization;

import DataLoader.Instance;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class MinimalEntropy implements DiscretizationMethod {
    List<Integer> cutPoints;

    public MinimalEntropy() {
        cutPoints = new ArrayList<>();
    }

    @Override
    public void discretize(List<Instance> data, int indexAttribute) {
        List<Instance> sortedInstances = data.stream()
                .sorted(Comparator.comparingDouble(inst -> Double.parseDouble(inst.getAttribute(indexAttribute))))
                .collect(Collectors.toList());
        discretize(sortedInstances, 0, sortedInstances.size(), 0.7);
        assignClasses(sortedInstances, indexAttribute);
    }

    private void assignClasses(List<Instance> sortedInstances, int indexAttribute) {
        cutPoints = cutPoints.stream().sorted().collect(Collectors.toList());
        IntStream.range(0, sortedInstances.size()).forEach(indexInstance -> sortedInstances
                .get(indexInstance)
                .setAttribute(indexAttribute, getDiscreteValue(indexInstance)));
    }

    private void discretize(List<Instance> sortedInstances, int start, int stop, double error) {
        int indexCut = findCut(sortedInstances, start, stop);
        double entropy = getEntropy(sortedInstances, start, stop);
        cutPoints.add(indexCut);
        if (entropy - getConditionalEntropy(sortedInstances, start, stop, indexCut) > error) {
            System.out.println(entropy - getConditionalEntropy(sortedInstances, start, stop, indexCut));
            if(indexCut - start > 1)
            discretize(sortedInstances, start, indexCut, error);
            if(stop - indexCut > 1)
            discretize(sortedInstances, indexCut, stop, error);
        }
    }

    private int findCut(List<Instance> sorted, int start, int stop) {
        Map<Integer, Double> cutsEntropies = new HashMap<>();
        IntStream.range(start, stop).forEach(cutCandidateIndex -> cutsEntropies.put(cutCandidateIndex, getConditionalEntropy(sorted, start, stop, cutCandidateIndex)));
        return cutsEntropies.entrySet().stream().min(Map.Entry.comparingByValue()).map(Map.Entry::getKey).orElse(-1);
    }

    private double getConditionalEntropy(List<Instance> sorted, int start, int stop, int cutIndex) {
        double s1 = (double) (cutIndex - start) / (stop - start);
        double s2 = (double) (stop - cutIndex) / (stop - start);
        return s1 * getEntropy(sorted, start, cutIndex) + s2 * getEntropy(sorted, cutIndex, stop);
    }

    private double getEntropy(List<Instance> sorted, int start, int stop) {
        Map<String, List<Instance>> classInstances = IntStream.range(start, stop)
                .mapToObj(sorted::get)
                .collect(Collectors.groupingBy(Instance::getSignedClass));
        double size = stop - start;
        return classInstances.entrySet().stream()
                .mapToDouble(entry -> -(entry.getValue().size() / size) * Math.log(entry.getValue().size() / size))
                .sum();
    }

    private String getDiscreteValue(double indexInstance) {
        return cutPoints.stream()
                .filter(cut -> indexInstance < cut)
                .findFirst()
                .orElse(cutPoints.get(cutPoints.size() - 1))
                .toString();
    }
}
