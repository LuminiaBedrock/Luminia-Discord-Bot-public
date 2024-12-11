package com.luminia.discord.bot.utils

import com.luminia.config.Config
import com.luminia.config.ConfigType
import java.io.File

object ConfigHelper {

    const val CONFIG = "config.yml"

    private val configs: MutableMap<String, Config> = mutableMapOf()

    @JvmStatic
    fun getConfig(name: String): Config? {
        return configs[name]
    }

    @JvmStatic
    fun getConfigNotNull(name: String): Config {
        return configs[name]!!
    }

    @JvmStatic
    fun loadConfig(name: String) {
        configs[name] = ConfigType.DETECT.createOf(name);
    }

    @JvmStatic
    fun saveResource(target: String, replace: Boolean = false) {
        saveResource(target, target, replace)
    }

    @JvmStatic
    fun saveResource(target: String, output: String, replace: Boolean = false) {
        val resourceStream = javaClass.classLoader.getResourceAsStream(target)
        val outputFile = File(output)
        if (resourceStream != null && (replace || !outputFile.exists())) {
            outputFile.createNewFile()
            outputFile.outputStream().use { resourceStream.copyTo(it) }
        }
    }
}