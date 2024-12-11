package com.luminia.config;

import org.spongepowered.configurate.hocon.HoconConfigurationLoader;

import java.io.File;

public class HoconConfig extends Config {

    public HoconConfig(File file) {
        super(file, HoconConfigurationLoader.builder()
                .file(file)
                .build());
    }

    public HoconConfig(String fileName) {
        this(new File(fileName));
    }
}