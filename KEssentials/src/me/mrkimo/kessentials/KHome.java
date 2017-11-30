
package me.mrkimo.kessentials;

import java.util.UUID;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class KHome implements CommandExecutor{
    
    private final JavaPlugin plg;
    
    public KHome(JavaPlugin plg){
        this.plg = plg;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String cmdLabel, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.GOLD + "##### [KEssentials] #####");
            sender.sendMessage(ChatColor.RED + "Dies sind keine Konsolenbefehle -.-");
            sender.sendMessage(ChatColor.RED + "+rep 4 Trying");
            return true;
        }
        Player p = (Player) sender;
        
        UUID uuid = p.getUniqueId();
        //home command
        if (cmd.getName().equalsIgnoreCase("home")) {
            if (args.length != 1) {
                p.sendMessage(ChatColor.RED + "ERROR: Es muss ein Argument angegeben werden!");
                return false;
            }
            if (!p.hasPermission("kessentials.home")) {
                p.sendMessage(ChatColor.RED + "ERROR: Keine Berechtigung! ");
                return true;
            }
            Location homeLoc = KEssentials.homeMgr.getHome(uuid, args[0]);
            if (homeLoc == null) {
                p.sendMessage(ChatColor.RED + "ERROR: Dieses Zuhause existiert nicht!");
                return true;
            }
            p.sendMessage(ChatColor.GREEN + " Beame zu Zuhause: " + ChatColor.AQUA + args[0]);
            p.teleport(homeLoc);
            p.playSound(homeLoc, (Sound) Sound.ENTITY_ENDERMEN_TELEPORT, (float) 1, (float) 1);
            p.sendMessage(ChatColor.GREEN + " Sie haben ihr Ziel erreicht!");
        }
        //sethome command
        if (cmd.getName().equalsIgnoreCase("sethome")) {
            if (args.length != 1) {
                p.sendMessage(ChatColor.RED + "ERROR: Es muss ein Argument angegeben werden!");
                return false;
            }
            if (!p.hasPermission("kessentials.home")) {
                p.sendMessage(ChatColor.RED + "ERROR: Keine Berechtigung!");
                return true;
            }
            p.sendMessage(ChatColor.GREEN + " Setze Zuhause...");
            if (KEssentials.homeMgr.addHome(uuid, args[0], p.getLocation()) == 0) {
                p.sendMessage(ChatColor.GREEN + " Zuhause: " + ChatColor.AQUA + args[0] + ChatColor.GREEN + " gesetzt!");
            } else {
                p.sendMessage(ChatColor.RED + "ERROR: Zuhause existiert bereits oder sonstiger Fehler!");
                return true;
            }
        }
        //delhome command
        if (cmd.getName().equalsIgnoreCase("delhome")) {
            if (args.length != 1) {
                p.sendMessage(ChatColor.RED + "ERROR: Es muss ein Argument angegeben werden!");
                return false;
            }
            if (!p.hasPermission("kessentials.home")) {
                p.sendMessage(ChatColor.RED + "ERROR: Keine Berechtigung!");
                return true;
            }
            KEssentials.homeMgr.removeHome(uuid, args[0]);
            p.sendMessage(ChatColor.GREEN + " Zuhause: " + ChatColor.AQUA + args[0] + ChatColor.GREEN + " gelöscht!");
        }
        //homelist command
        if (cmd.getName().equalsIgnoreCase("homes")) {
            String str_uuid = uuid.toString();
            if (args.length != 0) {
                p.sendMessage(ChatColor.RED + "ERROR: Es dürfen keine Argument angegeben werden!");
                return false;
            }
            if (!p.hasPermission("kessentials.home")) {
                p.sendMessage(ChatColor.RED + "ERROR: Keine Berechtigung!");
                return true;
            }
            String str_homes = "";
            Object[] obj_homes = KEssentials.homeMgr.getHomeList();
            for (byte i = 0; i < obj_homes.length; i++) {
                if (String.valueOf(obj_homes[i]).contains(str_uuid)) {
                    str_homes += String.valueOf(obj_homes[i]);
                    if (!(i == obj_homes.length - 1)) {
                        str_homes += ", ";
                    }
                }
            }
            if (str_homes.contains(str_uuid)) {
                str_homes = str_homes.replace(str_uuid, "");
            }
            p.sendMessage(ChatColor.GREEN + " Ihre R\u00fcckzugsorte: " + ChatColor.AQUA + str_homes);
        }
        return true;
    }
    
}
