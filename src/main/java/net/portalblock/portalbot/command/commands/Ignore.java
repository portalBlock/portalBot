package net.portalblock.portalbot.command.commands;

import net.portalblock.portalbot.Ignorable;
import net.portalblock.portalbot.PortalBot;
import net.portalblock.portalbot.command.Command;
import net.portalblock.portalbot.senders.CommandSender;

/**
 * Created by portalBlock on 1/31/2015.
 */
public class Ignore extends Command {

    private Ignorable ignorable;

    public Ignore(Ignorable ignorable){
        this.ignorable = ignorable;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(sender.isStrictStaff()){
            if(args.length < 1){
                sender.sendMessage("Please include a user/hostmask!");
                return;
            }
            String mask = args[0];
            if(!mask.matches(PortalBot.HOSTMASK_REGEX)){
                mask = mask + "!.*@.*";
            }
            ignorable.ignore(mask);
        }else{
            sender.sendMessage("No permission!");
        }
    }
}
