package net.portalblock.portalbot.command.commands;

import net.portalblock.portalbot.command.Command;
import net.portalblock.portalbot.senders.CommandSender;
import net.portalblock.portalbot.senders.UserSender;

/**
 * Created by portalBlock on 1/31/2015.
 */
public class Msg extends Command {

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
        if(args.length < 2){
            sender.sendMessage("Please include a target and message!");
            return;
        }
        UserSender us = (UserSender) sender;
        String target = args[0];
        StringBuilder builder = new StringBuilder();
        for(int i = 1; i < args.length; i++){
            builder.append(args[i]).append(" ");
        }
        us.getBot().sendIRC().message(target, builder.toString().trim());
    }
}
