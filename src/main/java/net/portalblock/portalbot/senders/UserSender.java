package net.portalblock.portalbot.senders;

import net.portalblock.portalbot.Bot;

/**
 * Created by portalBlock on 1/31/2015.
 */
public class UserSender implements CommandSender {

    private String name;
    private Bot bot;
    private boolean staff, strictStaff;

    public UserSender(String name, Bot bot, boolean staff, boolean strictStaff) {
        this.name = name;
        this.bot = bot;
        this.staff = staff;
        this.strictStaff = strictStaff;
    }

    @Override
    public void sendMessage(String msg) {
        bot.sendIRC().message(name, msg);
    }

    @Override
    public void sendPublic(String msg) {
        sendMessage(msg);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean isStaff() {
        return staff;
    }

    @Override
    public boolean isStrictStaff() {
        return strictStaff;
    }

    public Bot getBot(){
        return bot;
    }
}
