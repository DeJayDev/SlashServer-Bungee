package com.minecraftdimensions.slashserver;

import com.minecraftdimensions.slashserver.configlibrary.Config;
import java.io.File;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.plugin.Command;

/**
 * User: Bloodsplat Date: 13/11/13
 */
public class SlashServerReloadCommand extends Command {

    String configpath = File.separator + "plugins" + File.separator + "SlashServer" + File.separator + "config.yml";

    public SlashServerReloadCommand(String name, String permission, String... aliases) {
        super(name, permission, aliases);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        SlashServer.getTime().clear();
        SlashServer.config = new Config(configpath);
        for (String data : ProxyServer.getInstance().getServers().keySet()) {
            SlashServer.getTime().put(data, SlashServer.getConfig().getInt(data, 0));
        }
        SlashServer.ALREADY_ON_SERVER = SlashServer.getConfig().getString("ALREADY_ON_SERVER", "&cYou are already on that server!");
        SlashServer.TELEPORTING = SlashServer.getConfig().getString("TELEPORTING", "&2Teleporting you to {name}");
        SlashServer.ALREADY_TELEPORTING = SlashServer.getConfig().getString("ALREADY_TELEPORTING", "&cAlready teleporting you to a server");
        sender.sendMessage(new ComponentBuilder("Config reloaded").color(ChatColor.GREEN).create());
    }
}
