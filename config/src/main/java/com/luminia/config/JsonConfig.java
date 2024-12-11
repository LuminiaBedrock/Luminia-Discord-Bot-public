package com.luminia.config;

import org.spongepowered.configurate.gson.GsonConfigurationLoader;

import java.io.File;

public class JsonConfig extends Config {

    public JsonConfig(File file) {
        super(file, GsonConfigurationLoader.builder()
                .file(file)
                .build());
    }

    public JsonConfig(String fileName) {
        this(new File(fileName));
    }
}
