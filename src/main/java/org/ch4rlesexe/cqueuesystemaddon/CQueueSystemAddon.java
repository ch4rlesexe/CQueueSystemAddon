// src/main/java/org/ch4rlesexe/cqueuesystemaddon/CQueueSystemAddon.java
package org.ch4rlesexe.cqueuesystemaddon;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class CQueueSystemAddon extends JavaPlugin implements CommandExecutor {
    private String msgOnlyPlayers;
    private String msgUsage;
    private String msgJoinUsage;
    private String msgUnknown;
    private String msgForwarded;

    @Override
    public void onEnable() {
        // save default config.yml if missing
        saveDefaultConfig();
        // load messages
        FileConfiguration cfg = getConfig();
        msgOnlyPlayers   = ChatColor.translateAlternateColorCodes('&', cfg.getString("messages.only_players"));
        msgUsage         = ChatColor.translateAlternateColorCodes('&', cfg.getString("messages.usage"));
        msgJoinUsage     = ChatColor.translateAlternateColorCodes('&', cfg.getString("messages.join_usage"));
        msgUnknown       = ChatColor.translateAlternateColorCodes('&', cfg.getString("messages.unknown_subcommand"));
        msgForwarded     = ChatColor.translateAlternateColorCodes('&', cfg.getString("messages.forwarded"));

        getLogger().info("CQueueSystemAddon enabled!");
        getCommand("queueaddon").setExecutor(this);
        getServer().getMessenger().registerOutgoingPluginChannel(this, "CQueue");
        getLogger().info("Registered outgoing channel: CQueue");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(msgOnlyPlayers);
            return true;
        }
        Player player = (Player) sender;

        if (args.length < 1) {
            player.sendMessage(msgUsage);
            return true;
        }

        String action = args[0].toLowerCase();
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        String queueParam = "";

        switch (action) {
            case "join":
                if (args.length != 2) {
                    player.sendMessage(msgJoinUsage);
                    return true;
                }
                queueParam = " " + args[1];
                out.writeUTF("join");
                out.writeUTF(args[1]);
                out.writeUTF(player.getName());
                break;

            case "leave":
                out.writeUTF("leave");
                out.writeUTF("");
                out.writeUTF(player.getName());
                break;

            default:
                player.sendMessage(msgUnknown);
                return true;
        }

        // send to proxy
        player.sendPluginMessage(this, "CQueue", out.toByteArray());

        String forwardedMsg = msgForwarded
                .replace("{action}", action)
                .replace("{queue}", queueParam);
        player.sendMessage(forwardedMsg);

        return true;
    }
}
