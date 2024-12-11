package com.luminia.config;

import com.luminia.config.utils.NumberParser;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.serialize.SerializationException;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class ConfigNode {

    private final ConfigurationNode node;

    public ConfigNode(ConfigurationNode node) {
        this.node = node;
    }

    public ConfigurationNode getConfigurationNode() {
        return node;
    }

    public ConfigNode node(Object... path) {
        return new ConfigNode(node.node(path));
    }

    public ConfigNode nodes(String fullPath) {
        String[] pathArray = fullPath.split("\\.");
        ConfigurationNode currentNode = node;

        for (String path : pathArray) {
            currentNode = currentNode.node(path);
        }

        return new ConfigNode(currentNode);
    }

    public Object getValue() {
        return getValue(null);
    }

    @SuppressWarnings("unchecked")
    public <T> T getValue(T defaultValue) {
        Object rawValue = this.raw();
        return (rawValue != null) ? (T) rawValue : defaultValue;
    }

    public <T> T getValue(Class<T> classOfT) {
        try {
            return node.get(classOfT);
        } catch (Exception e) {
            return null;
        }
    }

    public void setValue(Object value) {
        try {
            node.set(value);
        } catch (SerializationException e) {
            throw new RuntimeException("Failed to set node value " + this);
        }
    }

    public Object raw() {
        return node.raw();
    }

    public boolean isNull() {
        return this.raw() == null;
    }

    public String asString() {
        return asString(null);
    }

    public String asString(String defaultValue) {
        return String.valueOf(getValue(defaultValue));
    }

    public boolean isString() {
        return this.raw() instanceof String;
    }

    public Integer asInt() {
        return asInt(0);
    }

    public Integer asInt(Integer defaultValue) {
        return NumberParser.parseInteger(String.valueOf(getValue(defaultValue)));
    }

    public boolean isInt() {
        return this.raw() instanceof Integer;
    }

    public Long asLong() {
        return asLong(0L);
    }

    public Long asLong(Long defaultValue) {
        return NumberParser.parseLong(String.valueOf(getValue(defaultValue)));
    }

    public boolean isLong() {
        return this.raw() instanceof Long;
    }

    public Double asDouble() {
        return asDouble(0D);
    }

    public Double asDouble(Double defaultValue) {
        return NumberParser.parseDouble(String.valueOf(getValue(defaultValue)));
    }

    public boolean isDouble() {
        return this.raw() instanceof Double;
    }

    public Float asFloat() {
        return  asFloat(0F);
    }

    public Float asFloat(Float defaultValue) {
        return NumberParser.parseFloat(String.valueOf(getValue(defaultValue)));
    }

    public boolean isFloat() {
        return this.raw() instanceof Float;
    }

    public Boolean asBoolean() {
        return asBoolean(false);
    }

    public Boolean asBoolean(Boolean defaultValue) {
        return getValue(defaultValue);
    }

    public boolean isBoolean() {
        return this.raw() instanceof Boolean;
    }

    public <T> List<T> asList() {
        return asList(Collections.emptyList());
    }

    public <T> List<T> asList(List<T> defaultValue) {
        return getValue(defaultValue);
    }

    public boolean isList() {
        return node.isList();
    }

    public List<String> asStringList() {
        return asStringList(Collections.emptyList());
    }

    public List<String> asStringList(List<String> defaultValue) {
        return getValue(defaultValue);
    }

    public <K, V> Map<K, V> asMap() {
        return asMap(Collections.emptyMap());
    }

    @SuppressWarnings("unchecked")
    public <K, V> Map<K, V> asMap(Map<K, V> defaultValue) {
        Object value = this.raw();
        return value instanceof Map ? (Map<K, V>) value : defaultValue;
    }

    public boolean isMap() {
        return node.isMap();
    }

    @Override
    public String toString() {
        return node.toString();
    }
}