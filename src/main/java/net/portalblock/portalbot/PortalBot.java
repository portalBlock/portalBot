package net.portalblock.portalbot;

import com.google.gson.GsonBuilder;
import lombok.Getter;
import net.portalblock.portalbot.config.Config;
import net.portalblock.portalbot.config.ServerSettings;
import org.pircbotx.MultiBotManager;
import org.pircbotx.PircBotX;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.concurrent.Executors;

/**
 * Created by portalBlock on 1/30/2015.
 */
public class PortalBot {

    public static final String HOSTMASK_REGEX = "(.)+!(.)+@(.)+";
    public static final String MALFORMED_MASK_SUFFIX = "!*@*";

    private static PortalBot instance;

    public static void main(String[] args){
        instance = new PortalBot();
    }

    public static PortalBot getInstance() {
        return instance;
    }

    //===========================================//

    @Getter
    private Config config;
    private MultiBotManager mbm;

    public PortalBot(){
        try{
            config = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create().fromJson(new FileReader("config.json"), Config.class);
            mbm = new MultiBotManager(Executors.newCachedThreadPool());
            for(ServerSettings serverSettings : config.getServers()){
                mbm.addBot(new Bot(ServerSettings.makeConfig(serverSettings), serverSettings));
            }
            mbm.start();
        }catch(FileNotFoundException e){
            System.out.println("Could not find config.json file, please fix!");
        }
    }

    public void stop(String msg){
        for(PircBotX b : mbm.getBots()){
            b.sendIRC().quitServer(msg);
        }
        mbm.stop();
        System.exit(0);
    }

}
