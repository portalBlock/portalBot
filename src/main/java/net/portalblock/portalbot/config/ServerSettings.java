package net.portalblock.portalbot.config;

import com.google.gson.annotations.Expose;
import lombok.Getter;
import net.portalblock.portalbot.ListenerDispatcher;
import net.portalblock.portalbot.listeners.ChannelListener;
import net.portalblock.portalbot.listeners.FeatureListener;
import net.portalblock.portalbot.listeners.LoggingListener;
import net.portalblock.portalbot.listeners.PrivateMessageListener;
import org.pircbotx.Configuration;
import org.pircbotx.User;

import java.util.HashMap;
import java.util.List;

/**
 * Created by portalBlock on 1/30/2015.
 */
public class ServerSettings {

    @Getter
    @Expose
    private String host, username, password, defaultPrefix, owner;
    @Getter
    @Expose
    private int port;
    @Getter
    @Expose
    private List<ChannelSettings> channels;

    private HashMap<String, ChannelListener> channelListeners = new HashMap<String, ChannelListener>();

    public boolean isStaff(String chan, String login){
        for(ChannelSettings channel : channels){
            if(chan.equalsIgnoreCase(channel.getName())){
                return channel.isStaff(login);
            }
        }
        return false;
    }

    public boolean isStrictStaff(String chan, User user){
         return (isStaff(chan, (user.getLogin().startsWith("~") ? user.getLogin().replaceFirst("~", "") : user.getLogin())) && user.isVerified());
    }

    public void addChannel(String chan, ChannelListener channelListener){
        chan = (chan.startsWith("#") ? chan.toLowerCase() : "#" + chan.toLowerCase());
        if(channelListeners.containsKey(chan.toLowerCase())) return;
        channelListeners.put(chan, channelListener);
        if(!channels.contains(channelListener.getChannelSettings())) channels.add(channelListener.getChannelSettings());
    }

    public ChannelListener[] getChannelListeners(){
        return channelListeners.values().toArray(new ChannelListener[channelListeners.size()]);
    }


    public static Configuration makeConfig(ServerSettings settings){
        Configuration.Builder builder = new Configuration.Builder();
        builder.setName(settings.getUsername());
        builder.setLogin(settings.getUsername());
        builder.setNickservPassword(settings.getPassword());
        for(ChannelSettings channel : settings.getChannels()){
            builder.addAutoJoinChannel(channel.getName());
            settings.addChannel(channel.getName(), new ChannelListener(channel, settings));
        }
        builder.addListener(new ListenerDispatcher(settings));
        builder.addListener(new FeatureListener(settings));
        builder.addListener(new PrivateMessageListener(settings));
        builder.addListener(new LoggingListener());
        builder.addServer(new Configuration.ServerEntry(settings.getHost(), settings.getPort()));
        builder.setMessageDelay(0);
        builder.setRealName(settings.getUsername() + " - Operated by " + settings.getOwner());
        return builder.buildConfiguration();
    }

}
