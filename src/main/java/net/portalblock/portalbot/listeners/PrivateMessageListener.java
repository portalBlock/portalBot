package net.portalblock.portalbot.listeners;

import net.portalblock.portalbot.Bot;
import net.portalblock.portalbot.Ignorable;
import net.portalblock.portalbot.command.CommandManager;
import net.portalblock.portalbot.config.ServerSettings;
import net.portalblock.portalbot.senders.UserSender;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.PrivateMessageEvent;

import java.util.ArrayList;

/**
 * Created by portalBlock on 1/31/2015.
 */
public class PrivateMessageListener extends ListenerAdapter implements Ignorable {

    private ServerSettings serverSettings;
    private CommandManager manager;
    private final ArrayList<String> ignoredMasks = new ArrayList<String>();

    public PrivateMessageListener(ServerSettings serverSettings){
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
    public void onPrivateMessage(PrivateMessageEvent event) throws Exception {
        if(event.getUser() == null) return;
        String msg = event.getMessage();
        if(msg.startsWith(serverSettings.getDefaultPrefix())) msg = msg.replaceFirst(serverSettings.getDefaultPrefix(), "");
        boolean staff = event.getUser().isVerified()&&event.getUser().getLogin().equals(serverSettings.getOwner());
        Bot bot = event.getBot();
        manager.handle(new UserSender(event.getUser().getNick(), bot, staff, staff, event.getUserHostmask().getNick() + "!" + event.getUserHostmask().getLogin() + "@" + event.getUserHostmask().getHostname()), msg);
    }
}
