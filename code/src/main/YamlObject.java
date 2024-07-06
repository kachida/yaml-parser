package main;

import java.util.List;
import java.util.Map;

public class YamlObject {
    private Map<String, Object> mapping;
    private List<Object> sequences;

    public YamlObject(List<Object> sequences, Map<String, Object> mapping) {
        this.sequences = sequences;
        this.mapping = mapping;
    }

    public Map<String, Object> getMapping() {
        return mapping;
    }

    public void setMapping(Map<String, Object> mapping) {
        this.mapping = mapping;
    }

    public List<Object> getSequences() {
        return sequences;
    }

    public void setSequences(List<Object> sequences) {
        this.sequences = sequences;
    }


    @Override
    public String toString() {
        return "YamlObject{" +
                "mapping=" + mapping +
                ", sequences=" + sequences +
                '}';
    }
}
