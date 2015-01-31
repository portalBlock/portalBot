package net.portalblock.portalbot.command;

import net.portalblock.portalbot.command.commands.Echo;
import net.portalblock.portalbot.senders.CommandSender;

import java.util.HashMap;

/**
 * Created by portalBlock on 1/31/2015.
 */
public class CommandManager {

    private HashMap<String, Command> commands = new HashMap<String, Command>();
    /*private ServerSettings serverSettings;

    public CommandManager(ServerSettings serverSettings) {
        this.serverSettings = serverSettings;
    }*/

    public CommandManager(){
        commands.put("echo", new Echo());
    }

    public void handle(CommandSender sender, String msg){
        if(msg == null || msg.split(" ").length == 0) return;
        String[] data = msg.split(" ");
        String command = data[0].toLowerCase();
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
