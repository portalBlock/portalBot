package net.portalblock.portalbot.senders;

import net.portalblock.portalbot.Bot;
import org.pircbotx.Channel;

/**
 * Created by portalBlock on 1/31/2015.
 */
public class UserCommandSender extends UserSender {

    private Channel channel;

    public UserCommandSender(String name, Channel channel, Bot bot, boolean staff, boolean strictStaff) {
        super(name, bot, staff, strictStaff);
        this.channel = channel;
    }

    @Override
    public void sendMessage(String msg) {
        getBot().sendIRC().notice(getName(), msg);
    }

    @Override
    public void sendPublic(String msg) {
        channel.send().message(msg);
    }

    public String getChannelName() {
        return channel.getName();
    }
}
