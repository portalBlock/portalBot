package net.portalblock.portalbot.config;

import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import lombok.Getter;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by portalBlock on 1/30/2015.
 */
public class ChannelSettings {

    @Getter
    @Expose
    private String name, prefix;

    @Expose private ArrayList<String> staff;

    public ChannelSettings(){
        prefix = "`";
        ArrayList<String> staff = new ArrayList<String>();
        staff.add("portalBlock");
        this.staff = staff;
    }

    public boolean isStaff(String name){
        for(String s : staff)
            if(s.equalsIgnoreCase(name)) return true;
        return false;
    }


    public static ChannelSettings makeDefaults(String name, ServerSettings settings){
        JSONObject object = new JSONObject();
        object.put("name", name);
        object.put("prefix", settings.getDefaultPrefix());
        JSONArray staff = new JSONArray();
        staff.put(settings.getOwner());
        object.put("staff", staff);
        return new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create().fromJson(object.toString(), ChannelSettings.class);
    }

}
