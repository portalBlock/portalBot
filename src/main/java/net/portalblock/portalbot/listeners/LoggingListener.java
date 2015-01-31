package net.portalblock.portalbot.listeners;

import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.*;

/**
 * Created by portalBlock on 1/31/2015.
 */
public class LoggingListener extends ListenerAdapter {
    
    private void print(String msg){
        System.out.println(msg);
    }

    @Override
    public void onAction(ActionEvent event) throws Exception {
        String chan = "PM";
        if(event.getChannel() != null) chan = event.getChannel().getName();
        print(String.format("[%s] *%s %s", chan, event.getUser().getNick(), event.getMessage()));
    }

    @Override
    public void onInvite(InviteEvent event) throws Exception {
        print("I was invited to " + event.getChannel() + " by " + event.getUser().getNick());
        event.getBot().sendIRC().joinChannel(event.getChannel());
    }

    @Override
    public void onJoin(JoinEvent event) throws Exception {
        print(event.getUser().getNick() + " has joined " + event.getChannel().getName());
    }

    @Override
    public void onKick(KickEvent event) throws Exception {
        print(event.getRecipient().getNick() + " has been kicked from " +
                event.getChannel().getName() + " by " + event.getUser().getNick() + " for " + event.getReason());
    }

    @Override
    public void onMessage(MessageEvent event) throws Exception {
        print("[" + event.getChannel().getName() + "] " + event.getUser().getNick() + ": " + event.getMessage());
    }

    @Override
    public void onMode(ModeEvent event) throws Exception {
        print(event.getUser().getNick() + " set mode " + event.getMode() + " on " + event.getChannel().getName());
    }

    @Override
    public void onNickChange(NickChangeEvent event) throws Exception {
        print(event.getOldNick() + "(" + event.getUserHostmask().getHostmask() + ") is now known as " + event.getNewNick());
    }

    @Override
    public void onNotice(NoticeEvent event) throws Exception {
        String msg = (event.getUser() != null ? event.getUser().getNick() : "");
        msg += (event.getChannel() != null ? "/" + event.getChannel().getName() : "");
        print("[NOTICE] " + msg + ": " + event.getNotice());
    }

    @Override
    public void onPart(PartEvent event) throws Exception {
        print(event.getUser().getNick() + " has parted " + event.getChannel().getName() + "(" + event.getReason() + ")");
    }

    @Override
    public void onPrivateMessage(PrivateMessageEvent event) throws Exception {
        print("[PM] " + event.getUser().getNick() + ": " + event.getMessage());
    }

}
