package me.mrkimo.kessentials;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class KTeleport implements CommandExecutor{
    
    private final JavaPlugin plg;
    
    public KTeleport(JavaPlugin plg){
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
        //tp command
        if (cmd.getName().equalsIgnoreCase("tp")) {
            switch (args.length) {
                case 0:
                    p.sendMessage(ChatColor.RED + "ERROR: Zu wenig Argumente!");
                    return false;
                case 1:
                    if (p.hasPermission("kessentials.tp")) {
                        for (Player curp : plg.getServer().getOnlinePlayers()) {
                            if (curp.getName().equalsIgnoreCase(args[0])) {
                                p.sendMessage(ChatColor.GREEN + " Beame zu " + ChatColor.AQUA + curp.getName());
                                p.playSound(curp.getLocation(), (Sound) Sound.ENTITY_ENDERMEN_TELEPORT, (float) 1, (float) 1);
                                curp.playSound(curp.getLocation(), (Sound) Sound.ENTITY_ENDERMEN_TELEPORT, (float) 1, (float) 1);
                                p.teleport(curp.getLocation());
                                p.sendMessage(ChatColor.GREEN + " Sie haben ihr Ziel erreicht!");
                                curp.sendMessage(ChatColor.AQUA + " " + p.getName() + ChatColor.GREEN + " hat sich zu ihnen gebeamt!");
                                return true;
                            }
                        }
                        p.sendMessage(ChatColor.RED + "ERROR: Spieler offline!");
                        return true;
                    } else {
                        p.sendMessage(ChatColor.RED + "ERROR: Keine Berechtigung!");
                        return true;
                    }
                case 2:
                    if (p.hasPermission("kessentials.tp.others")) {
                        for (Player target1 : plg.getServer().getOnlinePlayers()) {
                            if (target1.getName().equalsIgnoreCase(args[0])) {
                                for (Player target2 : plg.getServer().getOnlinePlayers()) {
                                    if (target2.getName().equalsIgnoreCase(args[1])) {
                                        target1.teleport(target2.getLocation());
                                        target1.playSound(target2.getLocation(), (Sound) Sound.ENTITY_ENDERMEN_TELEPORT, (float) 1, (float) 1);
                                        target2.playSound(target2.getLocation(), (Sound) Sound.ENTITY_ENDERMEN_TELEPORT, (float) 1, (float) 1);
                                        target1.sendMessage(ChatColor.GREEN + " Sie wurden zu " + ChatColor.AQUA + target2.getName() + ChatColor.GREEN + " gebeamt!");
                                        target2.sendMessage(ChatColor.AQUA + " " + target1.getName() + ChatColor.GREEN + " wurde zu ihnen gebeamt");
                                        return true;
                                    }
                                }
                            }
                        }
                        p.sendMessage(ChatColor.RED + "ERROR: Spieler offline!");
                        return true;
                    } else {
                        p.sendMessage(ChatColor.RED + "ERROR: Keine Berechtigung!");
                        return true;
                    }
                default:
                    p.sendMessage(ChatColor.RED + "ERROR: Zu viele Argumente!");
                    return false;
            }
        }
        //tphere command
        if (cmd.getName().equalsIgnoreCase("tphere") || cmd.getName().equalsIgnoreCase("s")) {
            if (args.length != 1) {
                p.sendMessage(ChatColor.RED + "ERROR Es muss ein Argument angegeben werden!");
                return false;
            }
            if (!p.hasPermission("kessentials.tp.here")) {
                p.sendMessage(ChatColor.RED + "ERROR: Keine Berechtigung!");
                return true;
            }
            for (Player curp : plg.getServer().getOnlinePlayers()) {
                if (curp.getName().equalsIgnoreCase(args[0])) {
                    p.sendMessage(ChatColor.GREEN + " Beame " + ChatColor.AQUA + curp.getName() + ChatColor.GREEN + " zu dir.");
                    curp.teleport(p.getLocation());
                    curp.playSound(p.getLocation(), (Sound) Sound.ENTITY_ENDERMEN_TELEPORT, (float) 1, (float) 1);
                    p.playSound(p.getLocation(), (Sound) Sound.ENTITY_ENDERMEN_TELEPORT, (float) 1, (float) 1);
                    p.sendMessage(ChatColor.GREEN + " Beam erfolgreich!");
                    curp.sendMessage(ChatColor.AQUA + " " + p.getName() + ChatColor.GREEN + " hat dich zu ihm gebeamt!");
                    return true;
                }
            }
            p.sendMessage(ChatColor.RED + "ERROR: Spieler offline!");
                return true;
        }
        return true;
    }
    
}
