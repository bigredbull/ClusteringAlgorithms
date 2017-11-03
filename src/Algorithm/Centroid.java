package Algorithm;

import DataLoader.Instance;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Centroid {
    private List<Instance> instanceList;

    public Centroid(List<Instance> instanceList) {
        this.instanceList = instanceList;
    }

    public Centroid(Instance instance) {
        instanceList = new ArrayList<>();
        instanceList.add(instance);
    }

    public List<Double> getMeans() {
        ArrayList<Double> initValues = new ArrayList<>();
        IntStream.range(0, instanceList.get(0).getAttributes().size()).forEach(i -> initValues.add(0.0));
        return instanceList
                .stream()
                .map(Instance::getDoubleAttributes)
                .reduce(initValues, this::sumAttributes)
                .stream()
                .map(sum -> sum / instanceList.size())
                .collect(Collectors.toList());
    }

    private List<Double> sumAttributes(List<Double> attr1, List<Double> attr2) {
        if (attr1.size() != attr2.size())
            throw new RuntimeException("sumAttributes: niezgodność rozmiarów: attr1.size=" + attr1.size() + " attr2.size=" + attr2.size());
        return IntStream.range(0, attr1.size())
                .mapToDouble(i -> attr1.get(i) + attr2.get(i))
                .boxed()
                .collect(Collectors.toList());
    }

    public void addInstance(Instance instance) {
        instanceList.add(instance);
    }

    public void clear() {
        instanceList = new ArrayList<>();
    }

}
