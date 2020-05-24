package com.matteoveroni.simplerestapi.producers;

import com.matteoveroni.simplerestapi.configurations.ConfigFile;
import com.matteoveroni.simplerestapi.configurations.ConfigFileName;
import com.matteoveroni.simplerestapi.configurations.ConfigHandler;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;

@ApplicationScoped
public class ConfigFileProducer {

    private final ConfigHandler configHandler;

    @Inject
    public ConfigFileProducer(ConfigHandler configHandler) {
        this.configHandler = configHandler;
    }

    @Produces
    public ConfigFile produceConfigFile() {
        return configHandler.getConfigurations(ConfigFileName.CONFIG_FILE, ConfigFile.class);
    }
}
