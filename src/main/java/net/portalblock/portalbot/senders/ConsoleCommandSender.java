package net.portalblock.portalbot.senders;

/**
 * Created by portalBlock on 1/31/2015.
 */
public class ConsoleCommandSender implements CommandSender {

    @Override
    public void sendMessage(String msg) {
        System.out.println(msg);
    }

    @Override
    public void sendPublic(String msg) {
        sendMessage(msg);
    }

    @Override
    public String getName() {
        return "CONSOLE";
    }

    @Override
    public boolean isStaff() {
        return true;
    }

    @Override
    public boolean isStrictStaff() {
        return true;
    }

    @Override
    public String getHostMask() {
        return "CONSOLE";
    }
}
