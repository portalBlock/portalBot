package net.portalblock.portalbot;

import net.portalblock.portalbot.config.ServerSettings;
import org.pircbotx.Configuration;
import org.pircbotx.PircBotX;

/**
 * Created by portalBlock on 1/30/2015.
 */
public class Bot extends PircBotX {

    private ServerSettings serverSettings;

    public Bot(Configuration configuration, ServerSettings settings) {
        super(configuration);
        this.serverSettings = settings;
        try{
            connect();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}