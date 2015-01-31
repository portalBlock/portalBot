package net.portalblock.portalbot.listeners;

import net.portalblock.portalbot.Bot;
import net.portalblock.portalbot.command.CommandManager;
import net.portalblock.portalbot.config.ChannelSettings;
import net.portalblock.portalbot.config.ServerSettings;
import net.portalblock.portalbot.senders.UserCommandSender;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.MessageEvent;

/**
 * Created by portalBlock on 1/31/2015.
 */
public class ChannelListener extends ListenerAdapter {

    private final ChannelSettings settings;
    private final CommandManager manager;
    private final ServerSettings serverSettings;

    public ChannelListener(ChannelSettings settings, ServerSettings serverSettings){
        this.settings = settings;
        this.serverSettings = serverSettings;
        manager = new CommandManager(/*serverSettings*/);
    }

    @Override
    public void onMessage(MessageEvent event) throws Exception {
        if(event.getUser() == null) return;
        if(event.getMessage().startsWith(settings.getPrefix())){
            Bot bot = event.getBot();
            manager.handle(new UserCommandSender(event.getUser().getNick(), event.getChannel(), bot,
                            settings.isStaff(event.getUser().getLogin()),
                            serverSettings.isStrictStaff(event.getChannel().getName(), event.getUser())),
                    event.getMessage().replaceFirst(settings.getPrefix(), ""));
        }
    }

    public String getChanName(){
        return settings.getName();
    }

    public ChannelSettings getChannelSettings() {
        return settings;
    }
}
