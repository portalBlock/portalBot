package net.portalblock.portalbot.command.commands;

import net.portalblock.portalbot.command.Command;
import net.portalblock.portalbot.senders.CommandSender;
import net.portalblock.portalbot.senders.UserSender;

/**
 * Created by portalBlock on 1/31/2015.
 */
public class Join extends Command {

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(!sender.isStrictStaff()){
            sender.sendMessage(NO_PERM);
            return;
        }
        if(!(sender instanceof UserSender)){
            sender.sendMessage("You must be on a server for that!");
            return;
        }
        if(args.length < 1){
            sender.sendMessage("Please include a channel to join!");
            return;
        }
        UserSender us = (UserSender) sender;
        args[0] = (args[0].startsWith("#") ? args[0] : "#" + args[0]);
        us.getBot().sendIRC().joinChannel(args[0]);
    }
}
