package DataLoader;

import Algorithm.ClusteringAlgorithm;
import DataLoader.Discretization.DiscretizationMethod;

import java.util.List;

public interface DataSource {
    List<Instance> loadData(String path);
    void discretizeAttribute(int index, DiscretizationMethod discretizationMethod);
    void run(ClusteringAlgorithm clusteringAlgorithm);
}
