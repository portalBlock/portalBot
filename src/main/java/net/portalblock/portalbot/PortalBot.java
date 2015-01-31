package net.portalblock.portalbot;

import com.google.gson.GsonBuilder;
import lombok.Getter;
import net.portalblock.portalbot.config.Config;
import net.portalblock.portalbot.config.ServerSettings;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

/**
 * Created by portalBlock on 1/30/2015.
 */
public class PortalBot {

    @Getter private static PortalBot instance;

    public static void main(String[] args){
        instance = new PortalBot();
    }

    //===========================================//

    @Getter
    private Config config;

    private ArrayList<Bot> activeBots = new ArrayList<Bot>();

    public PortalBot(){
        try{
            config = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create().fromJson(new FileReader("config.json"), Config.class);
            for(ServerSettings serverSettings : config.getServers()){
                activeBots.add(new Bot(ServerSettings.makeConfig(serverSettings), serverSettings));
            }
        }catch(FileNotFoundException e){
            System.out.println("Could not find config.json file, please fix!");
        }
    }

}
