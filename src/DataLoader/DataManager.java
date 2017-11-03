package DataLoader;

import Algorithm.ClusteringAlgorithm;
import DataLoader.Discretization.DiscretizationMethod;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class DataManager implements DataSource {
    private List<Instance> instanceList;

    public DataManager(String path) {
        this.instanceList = new ArrayList<>();
        instanceList = loadData(path);
    }

    @Override
    public List<Instance> loadData(String path) {
        try (Stream<String> linesStream = Files.lines(Paths.get(path))) {
            instanceList = linesStream
                    .map(line -> line.split(","))
                    .map(recordData -> new Instance(getRecordAttributes(recordData), getRecordClass(recordData)))
                    .collect(Collectors.toList());

        } catch (IOException e) {
            e.printStackTrace();
        }
        return instanceList;
    }

    @Override
    public void discretizeAttribute(int indexAttribute, DiscretizationMethod discretizationMethod) {
        discretizationMethod.discretize(instanceList, indexAttribute);
    }

    @Override
    public void run(ClusteringAlgorithm clusteringAlgorithm) {
        clusteringAlgorithm.train(instanceList);
    }

    private ArrayList<String> getRecordAttributes(String[] recordData) {
        ArrayList<String> result = new ArrayList<>();
        IntStream.range(0, recordData.length - 1).forEach(i -> result.add(recordData[i]));
        return result;
    }

    private String getRecordClass(String[] recordData) {
        return recordData[recordData.length - 1];
    }


}
