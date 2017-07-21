package world.protect;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.permission.Permission;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.utils.Config;
import world.protect.Command.WorldProtectCommand;
import world.protect.Listener.EventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Copyright © 2016 WetABQ&DreamCityAdminGroup All right reserved.
 * Welcome to DreamCity Server Address:dreamcity.top:19132
 * Created by WetABQ(Administrator) on 2017/7/21.
 * |||    ||    ||||                           ||        ||||||||     |||||||
 * |||   |||    |||               ||         ||  |      |||     ||   |||    |||
 * |||   |||    ||     ||||||  ||||||||     ||   ||      ||  ||||   |||      ||
 * ||  |||||   ||   |||   ||  ||||        ||| |||||     ||||||||   |        ||
 * ||  || ||  ||    ||  ||      |        |||||||| ||    ||     ||| ||      ||
 * ||||   ||||     ||    ||    ||  ||  |||       |||  ||||   |||   ||||||||
 * ||     |||      |||||||     |||||  |||       |||| ||||||||      |||||    |
 * ||||
 */
public class WorldProtect extends PluginBase{
    private Config config; //Config
    private List<String> worlds; //Protect worlds
    private static WorldProtect plugin;

    public static WorldProtect getPlugin() {
        return plugin;
    }

    public void onEnable(){
        plugin = this;
        this.getLogger().notice("WorldProtect is enable");
        this.getServer().getPluginManager().registerEvents(new EventListener(),this);
        this.config = new Config(this.getDataFolder()+"/worlds.yml",Config.YAML);
        if (!this.config.exists("worlds")){
            this.config.set("#worlds(保护列表)","#世界名称,分割(英文)");
            this.config.set("worlds","");
            this.config.set("createmode","false");
            this.config.save();
        }

        HashMap<String, String> permissions = new HashMap<>();
        permissions.put("world.protect.command.worldprotectcommand", "op");
        permissions.forEach((name, permission) -> Server.getInstance().getPluginManager().addPermission(new Permission(name, permission)));
        Server.getInstance().getCommandMap().register("", new WorldProtectCommand());

        List<String> list = new ArrayList<>();
        list.addAll(Arrays.asList(this.config.get("worlds").toString().split(",")));
        this.worlds = list;
    }

    public void onDisable(){
        this.getLogger().notice("WorldProtect is disable");
        saveDate();
        this.config.save();
    }

    /**
     * @param world_name World name
     */
    public void addWorld(String world_name){
        this.worlds.add(world_name);
    }

    /**
     * @param world_name World name
     */
    public void removeWorld(String world_name){
        this.worlds.remove(world_name);
    }

    /**
     *
     * @param world_name World name
     * @return Boolean
     */

    public boolean ifProtect(String world_name){
        return this.worlds.contains(world_name);
    }

    /**
     * @param player Player
     * @param world_name World name
     * @return False 受限 true 不受限
     */
    public boolean ifProtectToPlayer(Player player,String world_name){ //玩家是否受限 false受限 true不受限
        if(ifProtect(world_name)){
            if(player.isOp()){
               return false;
            }
            if(player.isCreative()){
                if(this.config.get("createmode").toString().equals("false")){
                    return false;
                }
            }
            return true;
        }else{
            return false;
        }
    }

    /**
     * 读取配置 创造模式是否受限
     * @return Boolean
     */
    public boolean ifCreatemode(){
        return !this.config.get("createmode").toString().equals("false");
    }

    public void setCreatemodeConfig(Boolean bl){
        this.config.set("createmode",""+bl);
    }


    /**
     * Save Date
     */
    private void saveDate(){
        String str = "";
        for(String s:this.worlds){
            str = str + s +",";
            // 原字符 + world + ,
            // str, + str2 +, = str,str2,
        }
        this.config.set("worlds",str);
    }

}
