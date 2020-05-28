package com.matteoveroni.simplerestapi.configurations;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
public class ConfigFile {
    @SerializedName("server_port")
    private final Integer serverPort;

    @SerializedName("public_website_folder")
    private final String publicWebsiteFolder;
}