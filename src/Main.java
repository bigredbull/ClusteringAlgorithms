import Algorithm.CMeansClustering.CMeans;
import Algorithm.ClusteringAlgorithm;
import DataLoader.DataManager;
import DataLoader.DataSource;

public class Main {
    public static void main(String args[]) {
        ClusteringAlgorithm cMeans = new CMeans(3);
        DataSource dataSource = new DataManager("iris.data");
//        dataSource.discretizeAttribute(1);
//        dataSource.discretizeAttribute(2);
//        dataSource.discretizeAttribute(3);
        dataSource.run(cMeans);
    }
}
