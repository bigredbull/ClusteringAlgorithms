package Algorithm;

import DataLoader.Instance;

import java.util.List;
import java.util.Map;

public interface ClusteringAlgorithm {
    public Map<Instance, String> test(List<Instance> testSet);

    public void train(List<Instance> trainSet);

}
