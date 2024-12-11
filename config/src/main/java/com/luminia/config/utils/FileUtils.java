package com.luminia.config.utils;

import java.io.File;

public class FileUtils {

    public static String getExtension(File file) {
        String fileName = file.getName();
        int lastDotIndex = fileName.lastIndexOf(".");
        if (lastDotIndex != -1 && lastDotIndex != 0) {
            return fileName.substring(lastDotIndex + 1);
        }
        return "";
    }
}