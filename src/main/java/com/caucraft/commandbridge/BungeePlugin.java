package com.caucraft.commandbridge;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import java.util.UUID;
import java.util.logging.Level;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.connection.Server;
import net.md_5.bungee.api.event.PluginMessageEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.event.EventHandler;

/**
 *
 * @author caucow
 */
public class BungeePlugin extends Plugin implements Listener {
    
    public static final String CHANNEL = "cccmdcridge:cmd";
    
    @Override
    public void onLoad() {
        getLogger().log(Level.INFO, "Plugin loaded");
    }
    
    @Override
    public void onEnable() {
        getProxy().getPluginManager().registerListener(this, this);
        getProxy().registerChannel(CHANNEL);
        getLogger().log(Level.INFO, "Plugin enabled");
    }
    
    @EventHandler
    public void onPluginMessage(PluginMessageEvent evt) {
        if (!CHANNEL.equals(evt.getTag())) {
            return;
        }
        evt.setCancelled(true);
        // The player shouldn't ever be sending transfer/sharding packets,
        // otherwise we're just giving them free item spawning and
        // teleport-anywhere backdoors. Also servers should only send messages
        // to players. Dunno how that could go wrong but okay.
        if (!(evt.getSender() instanceof Server) || !(evt.getReceiver() instanceof ProxiedPlayer)) {
            System.out.println("L");
            return;
        }
        ByteArrayDataInput in = ByteStreams.newDataInput(evt.getData());
        UUID id = UUID.fromString(in.readUTF());
        String command = in.readUTF();
        ProxiedPlayer p = getProxy().getPlayer(id);
        if (p == null || !p.isConnected()) {
            getLogger().log(Level.SEVERE, "Player not online with UUID: {0}", id);
        } else {
            getLogger().log(Level.INFO, "Running command ''{0}'' as player {1} with uuid {2}", new Object[]{command, p.getName(), id});
        }
        getProxy().getPluginManager().dispatchCommand(p, command);
    }
}
