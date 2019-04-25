package ru.kamikadze_zm.zmedia.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class StreamInfo {

    private final String name;
    private final String path;

    @JsonCreator
    public StreamInfo(@JsonProperty(value = "name") String name, @JsonProperty(value = "path") String path) {
        this.name = name;
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public String getPath() {
        return path;
    }

    @Override
    public String toString() {
        return "StreamInfo{" + "name=" + name + ", path=" + path + '}';
    }
}
