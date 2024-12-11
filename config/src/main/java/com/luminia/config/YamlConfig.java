package com.luminia.config;

import org.spongepowered.configurate.yaml.NodeStyle;
import org.spongepowered.configurate.yaml.YamlConfigurationLoader;

import java.io.File;

public class YamlConfig extends Config {

    public YamlConfig(File file) {
        super(file, YamlConfigurationLoader.builder()
                .file(file)
                .nodeStyle(NodeStyle.BLOCK)
                .indent(2)
                .build());
    }

    public YamlConfig(String fileName) {
        this(new File(fileName));
    }
}
