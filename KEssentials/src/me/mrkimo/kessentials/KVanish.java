package me.mrkimo.kessentials;

import java.util.ArrayList;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class KVanish implements CommandExecutor, Listener{
    
    private final ArrayList<Player> vanished = new ArrayList<>();
    private final JavaPlugin plg;
    
    public KVanish(JavaPlugin plg){
        this.plg = plg;
    }
    
    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent equit) {
        vanished.remove(equit.getPlayer());
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent ejoin) {
        vanished.forEach((p) -> {
            ejoin.getPlayer().hidePlayer(p);
        });
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
        //vanish from https://pastebin.com/1vuNUyCr
        if (cmd.getName().equalsIgnoreCase("vanish") || cmd.getName().equalsIgnoreCase("v")) {
            if (!p.hasPermission("kessentials.vanish")) {
                p.sendMessage(ChatColor.RED + "ERROR: Keine Berechtigung!");
                return true;
            }
            if (!vanished.contains(p)) {
                Bukkit.getServer().getOnlinePlayers().forEach((curp) -> {
                    curp.hidePlayer(p);
                });
                vanished.add(p);
                p.sendMessage(ChatColor.GREEN + " Sie sind nun unsichtbar!");
                return true;
            } else {
                Bukkit.getServer().getOnlinePlayers().forEach((pl) -> {
                    pl.showPlayer(p);
                });
                vanished.remove(p);
                p.sendMessage(ChatColor.GREEN + " Sie sind wieder sichtbar!");
                return true;
            }
        }
        return true;
    }
    
}
