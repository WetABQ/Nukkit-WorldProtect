package world.protect.Command;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.data.CommandParameter;
import cn.nukkit.level.Level;
import cn.nukkit.utils.TextFormat;
import world.protect.Listener.EventListener;
import world.protect.WorldProtect;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

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
public class WorldProtectCommand extends Command {

    private WorldProtect plugin = WorldProtect.getPlugin();

    public WorldProtectCommand() {
        super("wp");
        this.setAliases(new String[]{
                "wp"
        });
        this.setDescription("World protect");
        this.setUsage("/wp <help|add|remove> [world]");
        this.setCommandParameters(new HashMap<String, CommandParameter[]>() {
            {
                put("1arg", new CommandParameter[]{
                        new CommandParameter("子命令|SubCommand", false, new String[]{"help"}),
                });
                put("2arg", new CommandParameter[]{
                        new CommandParameter("add", false, new String[]{"add"}),
                        new CommandParameter("world_name", CommandParameter.ARG_TYPE_RAW_TEXT, false)
                });
                put("3arg", new CommandParameter[]{
                        new CommandParameter("remove", false, new String[]{"remove"}),
                        new CommandParameter("world_name", CommandParameter.ARG_TYPE_RAW_TEXT, false)
                });
                put("4arg", new CommandParameter[]{
                        new CommandParameter("sc", false, new String[]{"sc"}),
                        new CommandParameter("boolean", CommandParameter.ARG_TYPE_STRING, false)
                });
            }
        });
        this.setPermission("world.protect.command.worldprotectcommand");
    }

    @Override
    public boolean execute(CommandSender sender, String s, String[] args){
        if(!sender.hasPermission(this.getPermission())){
            sender.sendMessage(TextFormat.RED+"You don't have permission.");
            return true;
        }
        if(args.length <= 0 || args[0].equals("help") || args.length >= 3){
            sendHelp(sender);
            return true;
        }
        switch(args[0]){
            case "add":
            case "添加":
                if(args.length != 2){
                    sendHelp(sender);
                    return true;
                }
                plugin.addWorld(args[1]);
                Level level = plugin.getServer().getLevelByName(args[1]);
                if(level != null) { //根据Server方法 如果世界不存在则为null
                    if (level.getPlayers().size() >= 1) {
                        for(Map.Entry<Long,Player> entry : level.getPlayers().entrySet()){
                            Player player = entry.getValue();
                            EventListener.setIf(player,args[0]);
                        }
                    }
                }
                sender.sendMessage(TextFormat.GREEN+"World Protect >> Successfully protect "+args[1]+" world");
                break;
            case "remove":
            case "删除":
                if(args.length != 2){
                    sendHelp(sender);
                    return true;
                }
                plugin.removeWorld(args[1]);
                Level level2 = plugin.getServer().getLevelByName(args[1]);
                if(level2 != null) { //根据Server方法 如果世界不存在则为null
                    if (level2.getPlayers().size() >= 1) {
                        for(Map.Entry<Long,Player> entry : level2.getPlayers().entrySet()){
                            Player player = entry.getValue();
                            if(!player.isOp()){
                                if(plugin.ifCreatemode()){
                                    player.setGamemode(0);
                                    player.getAdventureSettings().setCanFly(false);
                                    player.getAdventureSettings().setFlying(false);
                                }
                            }
                        }
                    }
                }
                sender.sendMessage(TextFormat.GREEN+"World Protect >> Successfully cancelled the protection of "+args[1]+" world");
                break;
            case "sc":
                if(args.length != 2){
                    sendHelp(sender);
                    return true;
                }
                Boolean bl = false;
                if(args[1].equals("true")){
                    bl = true;
                }
                plugin.setCreatemodeConfig(bl);
                sender.sendMessage(TextFormat.GREEN+"World Protect >> Successful change Settings | "+bl);
                break;
        }
        return true;
    }

    private void sendHelp(CommandSender sender){
        sender.sendMessage(TextFormat.GOLD+"----World Protect----");
        sender.sendMessage(TextFormat.AQUA+"/wp help - View plugin help");
        sender.sendMessage(TextFormat.AQUA+"/wp add <world_name> - Add a protected world");
        sender.sendMessage(TextFormat.AQUA+"/wp remove <world_name> - Remove a protected world");
        sender.sendMessage(TextFormat.AQUA+"/wp sc <boolean> - Set the creation mode constrained configuration");
    }

}
