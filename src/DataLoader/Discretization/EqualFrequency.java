package DataLoader.Discretization;

import DataLoader.Instance;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class EqualFrequency implements DiscretizationMethod {
    int groupNumber;

    public EqualFrequency(int groupNumber) {
        this.groupNumber = groupNumber;
    }

    @Override
    public void discretize(List<Instance> data, int indexAttribute) {
        List<Instance> sortedInstances = data.stream()
                .sorted(Comparator.comparingDouble(inst -> Double.parseDouble(inst.getAttribute(indexAttribute))))
                .collect(Collectors.toList());

        int oneGropuSize = sortedInstances.size() / groupNumber;

        IntStream.range(0, sortedInstances.size()).forEach(ind -> sortedInstances.get(ind).setAttribute(indexAttribute, Integer.toString(ind / oneGropuSize)));

    }

}