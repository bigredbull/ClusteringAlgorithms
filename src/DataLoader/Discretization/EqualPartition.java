package DataLoader.Discretization;

import DataLoader.Instance;

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.DoubleStream;

public class EqualPartition implements DiscretizationMethod {
    int groupNumber;

    public EqualPartition(int groupNumber) {
        this.groupNumber = groupNumber;
    }

    @Override
    public void discretize(List<Instance> data, int indexAttribute) {
        Supplier<DoubleStream> valuesStreamSupplier =
                () -> data.stream()
                        .mapToDouble(instance -> Double.parseDouble(instance.getAttribute(indexAttribute)));
        double minValue = valuesStreamSupplier.get().min().getAsDouble();
        double maxValue = valuesStreamSupplier.get().max().getAsDouble();
        data.forEach(instance -> instance
                        .setAttribute(indexAttribute, calculatePartition(minValue, maxValue,
                                Double.parseDouble(instance.getAttribute(indexAttribute)))));
    }

    private String calculatePartition(double minValue, double maxValue, double currentValue) {
        double distance = round(Math.abs(maxValue - minValue));
        double distanceOneGroup = round(distance / groupNumber);
        int indexGroup = (int) ((currentValue - minValue) / distanceOneGroup);
        double minCurrentPartitionElement = round(minValue + indexGroup * distanceOneGroup);
        return " (" + minCurrentPartitionElement + " : " + (minCurrentPartitionElement + distanceOneGroup) + ") ";
    }

    private double round(double number) {
        return Math.round(number * 1000) / 1000.0;
    }

}
