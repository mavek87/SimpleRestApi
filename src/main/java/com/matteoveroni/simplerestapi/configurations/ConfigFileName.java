package com.matteoveroni.simplerestapi.configurations;

public enum ConfigFileName {

    CONFIG_FILE("config.json");

    private final String fileName;

    ConfigFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }

}

