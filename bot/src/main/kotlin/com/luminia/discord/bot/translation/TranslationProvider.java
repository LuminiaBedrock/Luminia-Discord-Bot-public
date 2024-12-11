package com.luminia.discord.bot.translation;

import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class TranslationProvider {

    private final Map<String, TranslationContainer> containers = new HashMap<>();

    private final static Yaml yaml = new Yaml();

    public void init(File folder) {
        for (LanguageCode code : LanguageCode.values()) {

            File file = new File(folder, code.getName() + ".yml");
            if (file.exists()) {
                try (FileInputStream fileInputStream = new FileInputStream(file)) {

                    Map<String, String> strings = yaml.loadAs(fileInputStream, Map.class);
                    strings.forEach((key, message) -> containers
                            .computeIfAbsent(key, k -> new TranslationContainer())
                            .addTranslation(code, message));

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public TranslationContainer getContainer(String key) {
        return containers.getOrDefault(key, TranslationContainer.EMPTY);
    }

    public Map<String, TranslationContainer> getContainers() {
        return containers;
    }
}
