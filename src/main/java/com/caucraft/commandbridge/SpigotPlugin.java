package com.caucraft.commandbridge;

import java.util.logging.Level;
import net.md_5.bungee.config.YamlConfiguration;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;
import org.bukkit.plugin.java.JavaPlugin;

/**
 *
 * @author caucow
 */
public class SpigotPlugin extends JavaPlugin {
    
    public static final String CHANNEL = "cccmdcridge:cmd";
    
    public Permission bridgePerm;
    public Permission bridgeAdminPerm;
    
    public boolean requirePerm;
    
    @Override
    public void onLoad() {
        getLogger().log(Level.INFO, "Plugin loaded");
    }
    
    @Override
    public void onEnable() {
        saveDefaultConfig();
        FileConfiguration conf = getConfig();
        requirePerm = conf.getBoolean("require-perm", false);
        
        getServer().getMessenger().registerOutgoingPluginChannel(this, CHANNEL);
        bridgePerm = new Permission("cccmdbridge.cmd.bridge", "Use Bungee Bridge Command", PermissionDefault.TRUE);
        // unused because lmao effort but maybe useful later?
        bridgeAdminPerm = new Permission("cccmdbridge.cmd.bridgeadmin", "Use Bungee Bridge Admin Command", PermissionDefault.OP);
        getCommand("bridge").setExecutor(new SCmdBridge(this));
//        getCommand("bridgeadmin").setExecutor(new SCmdBridgeAdmin(this));
        getLogger().log(Level.INFO, "Plugin enabled");
    }
}
