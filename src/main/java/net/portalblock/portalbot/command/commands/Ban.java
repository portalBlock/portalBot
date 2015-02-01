package net.portalblock.portalbot.command.commands;

import net.portalblock.portalbot.PortalBot;
import net.portalblock.portalbot.command.Command;
import net.portalblock.portalbot.moderating.IRCUser;
import net.portalblock.portalbot.senders.CommandSender;
import net.portalblock.portalbot.senders.UserCommandSender;

/**
 * Created by portalBlock on 1/31/2015.
 */
public class Ban extends Command {

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(!sender.isStrictStaff()){
            sender.sendMessage(NO_PERM);
            return;
        }
        if(!(sender instanceof UserCommandSender)){
            sender.sendMessage("At this time you must be in a channel to ban someone!");
            return;
        }
        if(args.length < 1){
            sender.sendMessage("Please include a user/hostmask!");
            return;
        }
        UserCommandSender ucs = (UserCommandSender) sender;
        String mask = args[0];
        if(!mask.matches(PortalBot.HOSTMASK_REGEX)) mask = mask + PortalBot.MALFORMED_MASK_SUFFIX;
        ucs.getChannel().send().ban(mask);
        String reason = "Banned from the channel.";
        if(args.length > 1){
            StringBuilder stringBuffer = new StringBuilder();
            for(int i = 1; i < args.length; i++)
                stringBuffer.append(args[i]).append(" ");
            reason = stringBuffer.toString().trim();
        }
        ucs.getBot().kick(ucs.getChannelName(), args[0], reason);
    }
}
