package me.mrkimo.kessentials;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class KWarp implements CommandExecutor{
    
    private final JavaPlugin plg;
    
    public KWarp(JavaPlugin plg){
        this.plg = plg;
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String cmdLabel, String[] args){
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.GOLD + "##### [KEssentials] #####");
            sender.sendMessage(ChatColor.RED + "Dies sind keine Konsolenbefehle -.-");
            sender.sendMessage(ChatColor.RED + "+rep 4 Trying");
            return true;
        }
        Player p = (Player) sender;
        //warp command
        if (cmd.getName().equalsIgnoreCase("warp")) {
            if (args.length != 1) {
                p.sendMessage(ChatColor.RED + "ERROR: Es muss ein Argument angegeben werden!");
                return false;
            }
            if (!p.hasPermission("kessentials.warp")) {
                p.sendMessage(ChatColor.RED + "ERROR: Keine Berechtigung! ");
                return true;
            }
            Location warpLoc = KEssentials.warpMgr.getWarp(args[0]);
            if (warpLoc == null) {
                p.sendMessage(ChatColor.RED + "ERROR: Dieser Warp existiert nicht!");
                return true;
            }
            p.sendMessage(ChatColor.GREEN + " Beame zu Warp: " + ChatColor.AQUA + args[0]);
            p.teleport(warpLoc);
            p.playSound(warpLoc, (Sound) Sound.ENTITY_ENDERMEN_TELEPORT, (float) 1, (float) 1);
            p.sendMessage(ChatColor.GREEN + " Sie haben ihr Ziel erreicht!");
        }
        //setwarp command
        if (cmd.getName().equalsIgnoreCase("setwarp")) {
            if (args.length != 1) {
                p.sendMessage(ChatColor.RED + "ERROR: Es muss ein Argument angegeben werden!");
                return false;
            }
            if (!p.hasPermission("kessentials.warp.mod")) {
                p.sendMessage(ChatColor.RED + "ERROR: Keine Berechtigung!");
                return true;
            }
            p.sendMessage(ChatColor.GREEN + " Setze Warp...");
            if (KEssentials.warpMgr.addWarp(args[0], p.getLocation()) == 0) {
                p.sendMessage(ChatColor.GREEN + " Warp: " + ChatColor.AQUA + args[0] + ChatColor.GREEN + " gesetzt!");
            } else {
                p.sendMessage(ChatColor.RED + "ERROR: Warp existiert bereits oder sonstiger Fehler!");
                return true;
            }
        }
        //delwarp command
        if (cmd.getName().equalsIgnoreCase("delwarp")) {
            if (args.length != 1) {
                p.sendMessage(ChatColor.RED + "ERROR: Es muss ein Argument angegeben werden!");
                return false;
            }
            if (!p.hasPermission("kessentials.warp.mod")) {
                p.sendMessage(ChatColor.RED + "ERROR: Keine Berechtigung!");
                return true;
            }
            KEssentials.warpMgr.removeWarp(args[0]);
            p.sendMessage(ChatColor.GREEN + " Warp: " + ChatColor.AQUA + args[0] + ChatColor.GREEN + " gelöscht!");
        }
        //warplist command
        if (cmd.getName().equalsIgnoreCase("warps")) {
            if (args.length != 0) {
                p.sendMessage(ChatColor.RED + "ERROR: Es dürfen keine Argument angegeben werden!");
                return false;
            }
            if (!p.hasPermission("kessentials.warp")) {
                p.sendMessage(ChatColor.RED + "ERROR: Keine Berechtigung!");
                return true;
            }
            String str_warps = "";
            Object[] obj_warps = KEssentials.warpMgr.getWarpList();
            for (int i = 0; i < obj_warps.length; i++) {
                str_warps += String.valueOf(obj_warps[i]);
                if (!(i == obj_warps.length - 1)) {
                    str_warps += ", ";
                }
            }
            p.sendMessage(ChatColor.GREEN + " Warps: " + ChatColor.AQUA + str_warps);
        }
        return true;
    }
}
