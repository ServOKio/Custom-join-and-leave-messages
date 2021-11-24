package net.servokio.customJaLm;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class Main extends JavaPlugin implements CommandExecutor, Listener {

    public void onLoad() {
        File config = new File(this.getDataFolder(), "config.yml");
        if (!config.exists()) {
            this.getLogger().info(sc("The configuration file created"));
            this.getConfig().options().copyDefaults(true);
            this.saveDefaultConfig();
        }
    }

    public void onEnable() {
        PluginCommand cjmreload =  getCommand("cjmreload");
        if(cjmreload == null){
            sc("Error connecting the \"cjmreload\" command");
        } else cjmreload.setExecutor(this);
        getServer().getPluginManager().registerEvents(this, this);
    }

    public boolean onCommand(CommandSender sender, Command cmd, String tabel, String[] args) {
        if (cmd.getName().equalsIgnoreCase("cjmreload")) {
            if(sender.hasPermission("cjmreload")){
                reloadConfig();
                sender.sendMessage(sc(getConfig().getString("reload-config")));
            } else sender.sendMessage(sc(getConfig().getString("no-rights")));
            return true;
        } else return false;
    }

    @EventHandler
    public void playerJoinEvent(PlayerJoinEvent event) {
        event.setJoinMessage(sc(getConfig().getString("join-message").replace("%name%", event.getPlayer().getName()).replace("%displayname%", event.getPlayer().getDisplayName())));
    }

    @EventHandler
    public void playerQuitEvent(PlayerQuitEvent event) {
        event.setQuitMessage(sc(getConfig().getString("leave-message").replace("%name%", event.getPlayer().getName()).replace("%displayname%", event.getPlayer().getDisplayName())));
    }

    public String sc(String message){
        return ChatColor.translateAlternateColorCodes('&', message);
    }
}
