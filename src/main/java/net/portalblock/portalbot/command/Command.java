package net.portalblock.portalbot.command;

import net.portalblock.portalbot.senders.CommandSender;

/**
 * Created by portalBlock on 1/31/2015.
 */
public abstract class Command {

    protected final String NO_PERM = "You don't have permission for that!";

    public abstract void execute(CommandSender sender, String[] args);

}
