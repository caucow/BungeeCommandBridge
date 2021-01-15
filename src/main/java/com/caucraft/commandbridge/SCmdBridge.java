package com.caucraft.commandbridge;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 *
 * @author caucow
 */
public class SCmdBridge implements CommandExecutor {
    
    private final SpigotPlugin pl;
    
    public SCmdBridge(SpigotPlugin pl) {
        this.pl = pl;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Err: Not a player");
            return true;
        }
        if (!sender.hasPermission(pl.bridgePerm)) {
            sender.sendMessage("No perms");
            return true;
        }
        
        Player p = (Player) sender;
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF(p.getUniqueId().toString());
        StringBuilder sb = new StringBuilder();
        for (String s : args) {
            sb.append(s);
            sb.append(' ');
        }
        sb.delete(sb.length() - 1, sb.length());
        out.writeUTF(sb.toString());
        p.sendPluginMessage(pl, SpigotPlugin.CHANNEL, out.toByteArray());
        return true;
    }
}
