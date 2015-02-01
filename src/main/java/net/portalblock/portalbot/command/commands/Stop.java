package net.portalblock.portalbot.command.commands;

import net.portalblock.portalbot.PortalBot;
import net.portalblock.portalbot.command.Command;
import net.portalblock.portalbot.senders.CommandSender;
import net.portalblock.portalbot.senders.UserSender;

/**
 * Created by portalBlock on 1/31/2015.
 */
public class Stop extends Command {

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(!sender.isStrictStaff()){
            sender.sendMessage(NO_PERM);
            return;
        }
        String msg = "Shutting down!";
        if(args.length > 0){
            StringBuilder builder = new StringBuilder();
            for(int i = 0; i < args.length; i++)
                builder.append(args[i]).append(" ");
            msg = builder.toString();
        }
        sender.sendMessage("Shutting down the bot!");
        PortalBot.getInstance().stop(msg);
    }
}
