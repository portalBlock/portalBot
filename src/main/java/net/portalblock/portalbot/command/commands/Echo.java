package net.portalblock.portalbot.command.commands;

import net.portalblock.portalbot.command.Command;
import net.portalblock.portalbot.senders.CommandSender;
import net.portalblock.portalbot.senders.UserSender;

/**
 * Created by portalBlock on 1/31/2015.
 */
public class Echo extends Command {

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(sender instanceof UserSender){
            UserSender us = (UserSender) sender;
            us.getBot().sendIRC().mode("#Test", "+o " + sender.getName());
        }
        StringBuffer buffer = new StringBuffer();
        for(String s : args)
            buffer.append(s).append(" ");
        sender.sendPublic(buffer.toString());
    }
}
