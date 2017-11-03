package DataLoader;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Instance {
    private ArrayList<String> attributes;
    private String signedClass;

    public Instance(ArrayList<String> attributes) {
        this.attributes = attributes;
    }

    public Instance(ArrayList<String> attributes, String signedClass) {
        this.attributes = attributes;
        this.signedClass = signedClass;
    }

    public String getAttribute(int i) {
        return attributes.get(i);
    }

    public List<String> getAttributes(List<Integer> indexes) {
        return indexes.stream()
                .map(attributes::get)
                .collect(Collectors.toList());
    }

    public List<Double> getDoubleAttributes() {
        return attributes.stream()
                .map(Double::parseDouble)
                .collect(Collectors.toList());
    }

    public ArrayList<String> getAttributes() {
        return attributes;
    }

    public void setAttribute(int index, String value) {
        attributes.set(index, value);
    }

    public int getAttributesSize() {
        return attributes.size();
    }

    public String getSignedClass() {
        return signedClass;
    }

}
