package me.mrkimo.kessentials;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class KSign implements Listener{

    @EventHandler
    public void onSignChange(SignChangeEvent esignchange) {
        if (esignchange.getLine(0).toLowerCase().contains("warp")) {
            if (esignchange.getPlayer().hasPermission("kimotools.signwarp.create")) {
                if (!esignchange.getLine(2).isEmpty() && (KEssentials.warpMgr.getWarp(esignchange.getLine(2)) != null)) {
                    esignchange.setLine(0, ChatColor.GREEN + "[Warp]");
                    esignchange.setLine(1, "");
                    esignchange.setLine(3, "");
                    esignchange.getPlayer().sendMessage("Warpschild nach " + esignchange.getLine(2) + " wurde erstellt!");
                }
            } else {
                esignchange.getBlock().breakNaturally();
                esignchange.getPlayer().playSound(esignchange.getPlayer().getLocation(), (Sound) Sound.BLOCK_WOOD_BREAK, (float) 1, (float) 1);
                esignchange.getPlayer().sendMessage(ChatColor.RED + "ERROR: Keine Berechtigung!");
            }
        }
    }

    @EventHandler
    public void onSignClick(PlayerInteractEvent eint) {
        if (eint.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            if (eint.getClickedBlock().getState() instanceof Sign) {
                Sign s = (Sign) eint.getClickedBlock().getState();
                String[] slines = s.getLines();
                if (slines[0].contains("[Warp]")) {
                    if (!slines[2].isEmpty()) {
                        Location warpLoc = KEssentials.warpMgr.getWarp(slines[2]);
                        if (warpLoc != null) {
                            eint.getPlayer().sendMessage(ChatColor.GOLD + "[KimoTools]" + ChatColor.GREEN + " Beame zu Warp: " + ChatColor.AQUA + slines[2]);
                            eint.getPlayer().teleport(warpLoc);
                            eint.getPlayer().playSound(warpLoc, (Sound) Sound.ENTITY_ENDERMEN_TELEPORT, (float) 1, (float) 1);
                            eint.getPlayer().sendMessage(ChatColor.GOLD + "[KimoTools]" + ChatColor.GREEN + " Sie haben ihr Ziel erreicht!");
                        }
                    }
                }
            }
        }
    }
    
}
