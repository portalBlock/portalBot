package net.portalblock.portalbot;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.portalblock.portalbot.config.ServerSettings;
import net.portalblock.portalbot.listeners.ChannelListener;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.*;

/**
 * Created by portalBlock on 1/31/2015.
 */
public class ListenerDispatcher extends ListenerAdapter {

    private ServerSettings settings;

    public ListenerDispatcher(ServerSettings settings) {
        this.settings = settings;
    }

    private void loop(ActionCallback callback){
        for(ChannelListener listener : settings.getChannelListeners()){
            if(callback.getChanName() != null && !listener.getChanName().equalsIgnoreCase(callback.getChanName())) continue;
            try{
                callback.doAction(listener);
            }catch(Exception ignored){
                continue;
            }
        }
    }

    @Override
    public void onAction(final ActionEvent event) throws Exception {
        if(event.getChannel() == null) return;
        loop(new ActionCallback(event.getChannel().getName()) {
            @Override
            public void doAction(ChannelListener listener) throws Exception {
                listener.onAction(event);
            }
        });
    }

    @Override
    public void onHalfOp(final HalfOpEvent event) throws Exception {
        loop(new ActionCallback(event.getChannel().getName()) {
            @Override
            public void doAction(ChannelListener listener) throws Exception {
                listener.onHalfOp(event);
            }
        });
    }

    @Override
    public void onInvite(final InviteEvent event) throws Exception {
        loop(new ActionCallback(null) {
            @Override
            public void doAction(ChannelListener listener) throws Exception {
                listener.onInvite(event);
            }
        });
    }

    @Override
    public void onJoin(final JoinEvent event) throws Exception {
        loop(new ActionCallback(event.getChannel().getName()) {
            @Override
            public void doAction(ChannelListener listener) throws Exception {
                listener.onJoin(event);
            }
        });
    }

    @Override
    public void onKick(KickEvent event) throws Exception {

    }

    @Override
    public void onMessage(final MessageEvent event) throws Exception {
        loop(new ActionCallback(event.getChannel().getName()) {
            @Override
            public void doAction(ChannelListener listener) throws Exception {
                listener.onMessage(event);
            }
        });
    }

    @Override
    public void onMode(ModeEvent event) throws Exception {

    }

    @Override
    public void onNickChange(NickChangeEvent event) throws Exception {

    }

    @Override
    public void onOp(OpEvent event) throws Exception {

    }

    @Override
    public void onNotice(NoticeEvent event) throws Exception {

    }

    @Override
    public void onOwner(OwnerEvent event) throws Exception {

    }

    @Override
    public void onPart(PartEvent event) throws Exception {

    }

    @Override
    public void onPrivateMessage(PrivateMessageEvent event) throws Exception {

    }

    @Override
    public void onQuit(QuitEvent event) throws Exception {

    }

    @AllArgsConstructor
    private abstract class ActionCallback {
        @Getter
        private String chanName;
        public abstract void doAction(ChannelListener listener) throws Exception;
    }
}
