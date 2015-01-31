package net.portalblock.portalbot.config;

import com.google.gson.annotations.Expose;
import lombok.Getter;
import net.portalblock.portalbot.PortalBot;
import org.pircbotx.Channel;
import org.pircbotx.User;

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

}
