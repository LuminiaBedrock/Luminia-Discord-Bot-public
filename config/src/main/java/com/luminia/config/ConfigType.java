package com.luminia.config;

import com.luminia.config.utils.FileUtils;
import com.google.common.collect.ImmutableMap;

import java.io.File;
import java.util.Map;

public enum ConfigType {
    DETECT,
    YAML,
    JSON,
    HOCON;

    public static final Map<String, ConfigType> EXTENSIONS = ImmutableMap.<String, ConfigType>builder()
            .put("yaml", YAML)
            .put("yml", YAML)
            .put("json", JSON)
            .put("conf", HOCON)
            .build();

    public Config createOf(String fileName) {
        return createOf(new File(fileName));
    }

    public Config createOf(File file) {
        String extension = FileUtils.getExtension(file).toLowerCase();
        if (this == DETECT && EXTENSIONS.containsKey(extension)) {
            return switch (EXTENSIONS.get(extension)) {
                case YAML -> new YamlConfig(file);
                case JSON -> new JsonConfig(file);
                default -> null;
            };
        } else if (this == YAML) {
            return new YamlConfig(file);
        } else if (this == JSON) {
            return new JsonConfig(file);
        }
        return null;
    }
}
