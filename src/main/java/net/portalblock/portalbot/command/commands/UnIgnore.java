package net.portalblock.portalbot.command.commands;

import net.portalblock.portalbot.Ignorable;
import net.portalblock.portalbot.PortalBot;
import net.portalblock.portalbot.command.Command;
import net.portalblock.portalbot.senders.CommandSender;
import net.portalblock.portalbot.senders.UserCommandSender;

/**
 * Created by portalBlock on 1/31/2015.
 */
public class UnIgnore extends Command {

    private Ignorable ignorable;

    public UnIgnore(Ignorable ignorable) {
        this.ignorable = ignorable;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(!sender.isStrictStaff()){
            sender.sendMessage(NO_PERM);
            return;
        }
        if(args.length < 1){
            sender.sendMessage("Please include a user/hostmask!");
            return;
        }
        String mask = args[0];
        if(!mask.matches(PortalBot.HOSTMASK_REGEX)) mask = mask + PortalBot.MALFORMED_MASK_SUFFIX;
        ignorable.unignore(mask);
        sender.sendMessage("I am no longer ignoring " + mask);
    }
}
