package net.portalblock.portalbot.config;

import com.google.gson.annotations.Expose;
import lombok.Getter;

import java.util.List;

/**
 * Created by portalBlock on 1/30/2015.
 */
public class Config {

    @Getter
    @Expose
    private List<ServerSettings> servers;

}
