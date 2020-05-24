package com.matteoveroni.simplerestapi.configurations;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class ConfigHandler {

    private static final Logger LOG = LoggerFactory.getLogger(ConfigHandler.class);
    private static final String CONFIG_FOLDER = "conf";
    private static final Map<String, Object> CACHE_CONFIGURATIONS = new HashMap<>();

    private final Gson gson;

    @Inject
    public ConfigHandler(Gson gson) {
        this.gson = gson;
    }

    public <T> T getConfigurations(ConfigFileName configFileName, Class<T> clazz) {
        return getConfigurations(configFileName.getFileName(), clazz);
    }

    public <T> T getConfigurations(String configFileName, Class<T> clazz) {
        try {
            T result;
            if (CACHE_CONFIGURATIONS.containsKey(configFileName)) {
                LOG.debug("The cache already contains configuration file " + configFileName);
                return clazz.cast(CACHE_CONFIGURATIONS.get(configFileName));
            } else {
                File configFile = new File(CONFIG_FOLDER + File.separator + configFileName);
                LOG.info("Config file: " + configFile.getAbsolutePath());
                LOG.info("Config file path: " + new File(ConfigHandler.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getPath());
                JsonReader configFileReader = new JsonReader(new FileReader(configFile));
                result = gson.fromJson(configFileReader, clazz);
                CACHE_CONFIGURATIONS.put(configFileName, result);
            }
            return result;
        } catch (IOException | URISyntaxException ex) {
            LOG.error("Error loading configuration [" + configFileName + "]", ex);
            throw new RuntimeException(ex);
        }
    }
}
