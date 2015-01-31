package net.portalblock.portalbot.listeners;

import net.portalblock.portalbot.config.ChannelSettings;
import net.portalblock.portalbot.config.ServerSettings;
import org.json.JSONArray;
import org.json.JSONObject;
import org.pircbotx.Colors;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.JoinEvent;
import org.pircbotx.hooks.events.MessageEvent;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by portalBlock on 1/30/2015.
 */
public class FeatureListener extends ListenerAdapter {

    private ServerSettings serverSettings;

    public FeatureListener(ServerSettings serverSettings) {
        this.serverSettings = serverSettings;
    }

    @Override
    public void onJoin(JoinEvent event) throws Exception {
        if(event.getUser() == null) return;
        if(event.getUser().getLogin().equalsIgnoreCase(serverSettings.getUsername())){
            serverSettings.addChannel(event.getChannel().getName(), new ChannelListener(ChannelSettings.makeDefaults(event.getChannel().getName(), serverSettings), serverSettings));
        }
    }

    @Override
    public void onMessage(MessageEvent event) throws Exception {
        String id = null;
        for(String temp : event.getMessage().split(" ")){
            if(getID(temp) != null){
                id = getID(temp);
            }
        }
        String json = "";
        JSONObject object = null;
        if(id != null){
            try{
                URL link = new URL("https://gdata.youtube.com/feeds/api/videos/"+id+"?v=2&alt=json");
                BufferedReader br = new BufferedReader(new InputStreamReader(link.openStream()));
                String input;
                while((input = br.readLine()) != null){
                    json+=input;
                    object = new JSONObject(json);
                }
                br.close();
                if(object == null){
                    return;
                }
                String title = object.getJSONObject("entry").getJSONObject("title").getString("$t");
                JSONArray array = object.getJSONObject("entry").getJSONArray("author");
                String author = array.getJSONObject(0).getJSONObject("name").getString("$t");
                int duration = object.getJSONObject("entry").getJSONObject("media$group").getJSONObject("yt$duration").getInt("seconds");
                int min = duration/60;
                double sec = duration%60;
                int views = object.getJSONObject("entry").getJSONObject("yt$statistics").getInt("viewCount");
                int dislikes = object.getJSONObject("entry").getJSONObject("yt$rating").getInt("numDislikes");
                int likes = object.getJSONObject("entry").getJSONObject("yt$rating").getInt("numLikes");
                int total = dislikes + likes;
                double percent = likes/total*100;
                String nick = (event.getUser() == null ? "" : "(" + event.getUser().getNick() + ") ");
                String text = nick + Colors.DARK_GREEN+title+Colors.BLACK+" by "+Colors.DARK_GREEN+author+Colors.BLACK+" - views: "+Colors.DARK_GREEN+views+Colors.BLACK+" - likes: "+Colors.DARK_GREEN+percent+"%"+Colors.BLACK+" - length: "+ Colors.DARK_GREEN+min+"m "+sec+"s";
                event.getChannel().send().message(text);
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    private static String getID(String str){
        for(String s : str.split(" ")){
            String regex = "(https?:\\/\\/)?(www.)?youtu\\.be\\/";
            Pattern pat = Pattern.compile(regex);
            Matcher m = pat.matcher(s);
            if(m.find()){
                return str.replaceFirst(m.group(), "");
            }
        }
        String pattern = "(?<=watch\\?v=|/videos/|embed/)[^#\\&\\?]*";
        Pattern compiledPattern = Pattern.compile(pattern);
        Matcher matcher = compiledPattern.matcher(str);
        if(matcher.find()){
            return matcher.group();
        }
        return null;
    }
}
