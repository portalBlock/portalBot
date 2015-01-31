package net.portalblock.portalbot.listeners;

import net.portalblock.portalbot.Bot;
import net.portalblock.portalbot.command.CommandManager;
import net.portalblock.portalbot.config.ServerSettings;
import net.portalblock.portalbot.senders.UserSender;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.PrivateMessageEvent;

/**
 * Created by portalBlock on 1/31/2015.
 */
public class PrivateMessageListener extends ListenerAdapter {

    private ServerSettings serverSettings;
    private CommandManager manager;

    public PrivateMessageListener(ServerSettings serverSettings){
        this.serverSettings = serverSettings;
        manager = new CommandManager();
    }

    @Override
    public void onPrivateMessage(PrivateMessageEvent event) throws Exception {
        if(event.getUser() == null) return;
        String msg = event.getMessage();
        if(msg.startsWith(serverSettings.getDefaultPrefix())) msg = msg.replaceFirst(serverSettings.getDefaultPrefix(), "");
        boolean staff = event.getUser().isVerified()&&event.getUser().getLogin().equals(serverSettings.getOwner());
        Bot bot = event.getBot();
        manager.handle(new UserSender(event.getUser().getNick(), bot, staff, staff), msg);
    }
}
