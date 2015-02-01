/*
 * Copyright (c) 2014 portalBlock. This work is provided AS-IS without any warranty.
 * You must provide a link back to the original project and clearly point out any changes made to this project.
 * This license must be included in all project files.
 * Any changes merged with this project are property of the copyright holder but may include the author's name.
 */

package net.portalblock.portalbot.moderating;

import net.portalblock.portalbot.Bot;
import org.json.JSONArray;
import org.json.JSONObject;
import org.pircbotx.Colors;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by portalBlock on 9/14/2014.
 */
public class Moderator {

    private final int MAX_WARNS = 3;
    private static Map<String, IRCUser> users = new HashMap<String, IRCUser>();
    private Map<String, String> lastSaid = new HashMap<String, String>();
    private Map<String, Long> chatCooldown = new HashMap<String, Long>();

    public void receiveEvent(EventWrapper wrapper) {
        if(users.get(wrapper.getSender().getName().toLowerCase()) == null) users.put(wrapper.getSender().getName().toLowerCase(), new IRCUser(wrapper.getBot(), wrapper.getSender().getName()));
        //Check and use return to not spam warnings if multiple violations in one message.
        //if(checkBlacklist(wrapper)) return;
        if(checkLastSaid(wrapper)) return;
        if(checkCoolCheck(wrapper)) return;
        if(checkCaps(wrapper)) return;
    }

    private boolean checkBlacklist(EventWrapper e){
        /*boolean bl = JSONConfigManager.getInstance().isBlacklistedWord(e.getMessage().split(" "));
        if(bl){
            e.getBot().kick(e.getChannel(), e.getSender(), "Do not use black listed words!");
            Ban.ban(e.getBot(), e.getChannel(), e.getSender(), "Do not use black listed words!");
            return true;
        }*/
        return false;
    }

    private boolean checkCoolCheck(EventWrapper e){
        if(e.getSender().isStrictStaff()) return false;
        if(coolCheck(e.getSender().getName())){
            chatCooldown.put(e.getSender().getName(), System.currentTimeMillis());
            return false;
        }else{
            IRCUser user = users.get(e.getSender().getName().toLowerCase());
            user.setSpam(user.getSpam() + 1);
            if(user.getSpam() >= MAX_WARNS){
                e.getChannel().send().kick(user, "Please do not spam the chat!");
            }else{
                e.getSender().sendMessage(Colors.RED + "Please do not spam things! Warning " + user.getSpam() + "/" + MAX_WARNS);
            }
            System.out.println("Warned " + e.getSender().getName() + " for spamming!");
            return true;
        }
    }

    private boolean checkLastSaid(EventWrapper e){
        if(e.getSender().isStrictStaff()) return false;
        String lastMessage, message;
        message = e.getMessage();
        lastMessage = lastSaid.get(e.getSender().getName());
        if(lastMessage != null) {
            int diff = message.length() - lastMessage.length();
            if (message.equalsIgnoreCase(lastMessage) || message.contains(lastMessage) && diff <= 3) {
                IRCUser user = users.get(e.getSender().getName().toLowerCase());
                user.setRepeat(user.getRepeat()+1);
                if(user.getRepeat() >= MAX_WARNS){
                    e.getChannel().send().kick(user, "Please do not repeat things more then 3 times!");
                }else{
                    e.getSender().sendMessage(Colors.RED + "Please do not repeat things! Warning " + user.getRepeat() + "/" + MAX_WARNS);
                    lastSaid.remove(e.getSender().getName());
                }
                System.out.println("Warned " + e.getSender().getName() + " for repeating!");
                return true;
            }
        }
        lastSaid.put(e.getSender().getName(), e.getMessage());
        return false;
    }

    public boolean checkCaps(EventWrapper e){
        if(e.getSender().isStrictStaff()) return false;
        double upperCaseCount = 0;
        String message = e.getMessage();
        for (int i = 0; i < message.length(); i++){
            if(Character.isUpperCase(message.charAt(i))){
                upperCaseCount++;
            }
        }
        double maxPer = 50;
        double tempNum = upperCaseCount / message.length();
        double percent = tempNum * 100;
        if (percent >= maxPer && message.length() > 5) {
            IRCUser user = users.get(e.getSender().getName().toLowerCase());
            user.setCaps(user.getCaps()+1);
            if(user.getCaps() >= MAX_WARNS){
                e.getChannel().send().kick(user, "Please do not use so much caps!");
            }else{
                e.getSender().sendMessage(Colors.RED + "Please don't use more then 50% caps in a message! Warning " + user.getCaps() + "/" + MAX_WARNS);
            }
            System.out.println("Warned " + e.getSender().getName() + " for caps abuse!");
            return true;
        }
        return false;
    }

    public boolean coolCheck(String name) {
        final int delay = 2;
        if (chatCooldown.get(name) != null) {
            return chatCooldown.get(name) < (System.currentTimeMillis() - delay * 1000);
        } else {
            return true;
        }
    }

    public static String getJSONInfo(){
        JSONArray content = new JSONArray();
        for(Map.Entry<String, IRCUser> entry : users.entrySet()){
            JSONObject user = new JSONObject();
            user.put("name", entry.getValue().getName());
            user.put("caps", entry.getValue().getCaps());
            user.put("spam", entry.getValue().getSpam());
            user.put("repeat", entry.getValue().getRepeat());
            content.put(user);
        }
        return content.toString();
    }

    public static void flushUser(String name){
        users.remove(name.toLowerCase());
    }

    private class MessageEvent {
        private String channel, sender, message;
        private Bot bot;

        public MessageEvent(String channel, String sender, String message, Bot bot) {
            this.channel = channel;
            this.sender = sender;
            this.message = message;
            this.bot = bot;
        }

        public String getChannel() {
            return channel;
        }

        public String getSender() {
            return sender;
        }

        public String getMessage() {
            return message;
        }

        public Bot getBot() {
            return bot;
        }
    }

}
