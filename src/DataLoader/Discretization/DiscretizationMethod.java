package DataLoader.Discretization;

import DataLoader.Instance;

import java.util.List;

public interface DiscretizationMethod {
    void discretize(List<Instance> data, int indexAttribute);
}
