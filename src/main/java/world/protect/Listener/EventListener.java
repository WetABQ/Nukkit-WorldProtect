package world.protect.Listener;


import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.block.BlockBreakEvent;
import cn.nukkit.event.block.BlockPlaceEvent;
import cn.nukkit.event.player.PlayerJoinEvent;
import cn.nukkit.event.player.PlayerTeleportEvent;
import cn.nukkit.utils.TextFormat;
import world.protect.WorldProtect;

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
public class EventListener implements Listener{
    private static WorldProtect plugin = WorldProtect.getPlugin();

    @EventHandler
    public void onBlockBreakEvent(final BlockBreakEvent event){
        Player player = event.getPlayer();
        if(plugin.ifProtectToPlayer(player,player.getLevel().getFolderName())){
            player.sendTip(TextFormat.RED+"\n\n\n\nYou don't have permission to break this block");
        }
    }

    @EventHandler
    public void onBlockPlaceEvent(final BlockPlaceEvent event){
        Player player = event.getPlayer();
        if(plugin.ifProtectToPlayer(player,player.getLevel().getFolderName())){
            player.sendTip(TextFormat.RED+"\n\n\n\nYou don't have permission to place this block");
        }
    }

    @EventHandler
    public void onPlayerTeleportEvent(final PlayerTeleportEvent event){
        Player player = event.getPlayer();
        setIf(player,event.getTo().getLevel().getFolderName());
    }

    @EventHandler
    public void onPlayerJoinEvent(PlayerJoinEvent event){
        Player player = event.getPlayer();
        setIf(player,event.getPlayer().getLevel().getFolderName());
    }

    private static void setGamemode(Player player,int gamemode){
        player.setGamemode(gamemode);
        player.getAdventureSettings().setCanFly(false);
        player.getAdventureSettings().setFlying(false);
    }

    public static void setIf(Player player,String world_name){
        if(plugin.ifProtectToPlayer(player,world_name)){
            setGamemode(player,2);
        }else if(!player.isOp()){
            if(!player.isCreative()){
                setGamemode(player,0);
            } else if(plugin.ifCreatemode()){
                setGamemode(player,0);
            }
        }
    }
}
