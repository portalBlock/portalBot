package net.portalblock.portalbot.listeners;

import net.portalblock.portalbot.Bot;
import net.portalblock.portalbot.Ignorable;
import net.portalblock.portalbot.command.CommandManager;
import net.portalblock.portalbot.config.ChannelSettings;
import net.portalblock.portalbot.config.ServerSettings;
import net.portalblock.portalbot.moderating.EventWrapper;
import net.portalblock.portalbot.moderating.Moderator;
import net.portalblock.portalbot.senders.UserCommandSender;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.MessageEvent;

import java.util.ArrayList;

/**
 * Created by portalBlock on 1/31/2015.
 */
public class ChannelListener extends ListenerAdapter implements Ignorable {

    private final ChannelSettings settings;
    private final CommandManager manager;
    private final ServerSettings serverSettings;
    private final ArrayList<String> ignoredMasks = new ArrayList<String>();
    private final Moderator moderator = new Moderator();

    public ChannelListener(ChannelSettings settings, ServerSettings serverSettings){
        this.settings = settings;
        this.serverSettings = serverSettings;
        manager = new CommandManager(this);
    }

    @Override
    public void ignore(String hostmask) {
        ignoredMasks.add(hostmask.toLowerCase().replace("*", ".*"));
    }

    @Override
    public void unignore(String hostmask) {
        hostmask = hostmask.replace("*", ".*");
        while(ignoredMasks.contains(hostmask.toLowerCase())) ignoredMasks.remove(hostmask.toLowerCase());
    }

    @Override
    public boolean isIgnoring(String hostmask) {
        for(String s : ignoredMasks){
            if(hostmask.toLowerCase().matches(s)) return true;
        }
        return false;
    }

    @Override
    public void onMessage(MessageEvent event) throws Exception {
        if(event.getUser() == null){
            System.out.println("Null User onMessage: " + event);
            return;
        }
        Bot bot = event.getBot();
        UserCommandSender ucs = new UserCommandSender(event.getUser().getNick(), event.getChannel(), bot,
                settings.isStaff(event.getUser().getNick()),
                serverSettings.isStrictStaff(event.getChannel().getName(), event.getUser()),
                event.getUserHostmask().getNick() + "!" + event.getUserHostmask().getLogin() + "@" + event.getUserHostmask().getHostname());
        moderator.receiveEvent(new EventWrapper(ucs, bot, event.getChannel(), event.getMessage()));
        if(event.getMessage().startsWith(settings.getPrefix())){
            manager.handle(ucs, event.getMessage().replaceFirst(settings.getPrefix(), ""));
        }
    }

    public String getChanName(){
        return settings.getName();
    }

    public ChannelSettings getChannelSettings() {
        return settings;
    }
}
