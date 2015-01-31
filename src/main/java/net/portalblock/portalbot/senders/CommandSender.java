package net.portalblock.portalbot.senders;

/**
 * Created by portalBlock on 1/31/2015.
 */
public interface CommandSender {

    public void sendMessage(String msg);

    public void sendPublic(String msg);

    public String getName();

    public boolean isStaff();

    public boolean isStrictStaff();

}
