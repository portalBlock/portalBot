package net.portalblock.portalbot.moderating;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.portalblock.portalbot.Bot;
import net.portalblock.portalbot.senders.UserCommandSender;
import org.pircbotx.Channel;

/**
 * Created by portalBlock on 1/31/2015.
 */
@AllArgsConstructor
public class EventWrapper {

    @Getter private UserCommandSender sender;
    @Getter private Bot bot;
    @Getter private Channel channel;
    @Getter private String message;

}
