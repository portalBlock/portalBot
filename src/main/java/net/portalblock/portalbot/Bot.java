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

    public void kick(String chan, String nick, String reason){
        chan = (chan.startsWith("#") ? chan : "#" + chan);
        sendRaw().rawLine("KICK " + chan + " " + nick + " :" + reason);
    }

    public void kick(String chan, String nick){
        kick(chan, nick, "Kicked from the channel.");
    }

}
