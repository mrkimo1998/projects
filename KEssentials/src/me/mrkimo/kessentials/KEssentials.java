package me.mrkimo.kessentials;

import java.util.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class KEssentials extends JavaPlugin implements Listener{

    public static WarpManager warpMgr;
    public static HomeManager homeMgr;
    
    public static final Logger log = Bukkit.getServer().getLogger();

    //final String path1 = "Configuration.serverteam";
    //final String path2 = "Configuration.owner";
    //String serverteam = "TEAM";
    //String owner = "MrKimo";

    @Override
    public void onDisable() {
        log.info("Plugin deactivated");
    }

    @Override
    public void onEnable() {        
        
        log.info("==========[KEssentials]==========");
        log.info("Plugin loading.");
        //Loading Classes
        KVanish vanish = new KVanish(this);
        KSign sign = new KSign();
        KTeleport teleport = new KTeleport(this);
        KHome home = new KHome(this);
        KWarp warp = new KWarp(this);
        //Loading Config
        loadConfig();
        log.info("Config loaded.");
        //Loading Managers
        warpMgr = new WarpManager("warps.hashmap", this);
        log.info("WarpManager loaded.");
        homeMgr = new HomeManager("homes.hashmap", this);
        log.info("HomeManager loaded.");
        //Registering Events
        getServer().getPluginManager().registerEvents(this, this);
        getServer().getPluginManager().registerEvents(vanish, this);
        getServer().getPluginManager().registerEvents(sign, this);
        log.info("Events registered.");
        //Registering CommandExecutors
        this.getCommand("vanish").setExecutor(vanish);
        this.getCommand("v").setExecutor(vanish);
        
        this.getCommand("tp").setExecutor(teleport);
        this.getCommand("s").setExecutor(teleport);
        this.getCommand("tphere").setExecutor(teleport);
        
        this.getCommand("home").setExecutor(home);
        this.getCommand("sethome").setExecutor(home);
        this.getCommand("delhome").setExecutor(home);
        this.getCommand("homes").setExecutor(home);
        
        this.getCommand("warp").setExecutor(warp);
        this.getCommand("setwarp").setExecutor(warp);
        this.getCommand("delwarp").setExecutor(warp);
        this.getCommand("warps").setExecutor(warp);
        log.info("Commands registered.");
        log.info("Done!");
        log.info("Plugin von MrKimo.");
        log.info("mrkimo.ddns.net");
        log.info("==========[KEssentials]==========");
    }

    /**@EventHandler
    public void onJoin(PlayerJoinEvent ejoin) {
        if (ejoin.getPlayer().getName().equals(owner)) {
            ejoin.setJoinMessage(ChatColor.RED + "Der Servereigentümer " + ChatColor.AQUA + ejoin.getPlayer().getName() + ChatColor.RED + " hat sich eingeloggt!");
        } else {
            ejoin.setJoinMessage(ChatColor.GREEN + "Spieler " + ChatColor.AQUA + ejoin.getPlayer().getName() + ChatColor.GREEN + " hat sich eingeloggt!");
        }
    }**/

    
    
    public void loadConfig() {
        /**this.getConfig().addDefault(path1, serverteam);
        this.getConfig().addDefault(path2, owner);
        this.getConfig().options().copyDefaults(true);
        this.saveConfig();
        serverteam = this.getConfig().getString(path1);
        owner = this.getConfig().getString(path2);**/
        log.info("No Config Yet!");
    }
}
