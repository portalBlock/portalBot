package net.portalblock.portalbot.command;

import net.portalblock.portalbot.Ignorable;
import net.portalblock.portalbot.command.commands.*;
import net.portalblock.portalbot.senders.CommandSender;

import java.util.HashMap;

/**
 * Created by portalBlock on 1/31/2015.
 */
public class CommandManager {

    private HashMap<String, Command> commands = new HashMap<String, Command>();
    private Ignorable ignorable;

    public CommandManager(Ignorable ignorable) {
        this.ignorable = ignorable;
        commands.put("echo", new Echo());
        commands.put("ignore", new Ignore(this.ignorable));
        commands.put("unignore", new UnIgnore(this.ignorable));
        commands.put("ban", new Ban());
        commands.put("unban", new UnBan());
        commands.put("mute", new Mute());
        commands.put("unmute", new UnMute());
        commands.put("msg", new Msg());
        commands.put("join", new Join());
        commands.put("stop", new Stop());
    }

    public void handle(CommandSender sender, String msg){
        if(msg == null || msg.split(" ").length == 0) return;
        String[] data = msg.split(" ");
        String command = data[0].toLowerCase();
        if(!command.equalsIgnoreCase("unignore") && ignorable.isIgnoring(sender.getHostMask())) return;
        String[] args = new String[0];
        if(data.length > 1){
            args = new String[data.length - 1];
            for(int i = 1; i < data.length; i++){
                args[i-1] = data[i];
            }
        }
        if(commands.containsKey(command)) commands.get(command).execute(sender, args);
    }

}
