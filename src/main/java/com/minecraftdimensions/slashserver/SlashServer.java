package com.minecraftdimensions.slashserver;

import com.minecraftdimensions.slashserver.configlibrary.Config;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Plugin;

public class SlashServer extends Plugin {

    ProxyServer proxy;
    public static Config config;
    private static ArrayList<ProxiedPlayer> tasks = new ArrayList<>();
    private static HashMap<String, Integer> time = new HashMap<>();
    public static SlashServer INSTANCE;
    public static String ALREADY_ON_SERVER;
    public static String TELEPORTING;
    public static String ALREADY_TELEPORTING;

    public void onEnable() {
        INSTANCE = this;
        proxy = ProxyServer.getInstance();
        registerCommands();
        setupConfig();
    }

    private void setupConfig() {
        String configpath = File.separator + "plugins" + File.separator + "SlashServer" + File.separator + "config.yml";
        config = new Config(configpath);
        for (String data : proxy.getServers().keySet()) {
            time.put(data, config.getInt(data, 0));
        }
        ALREADY_ON_SERVER = config.getString("ALREADY_ON_SERVER", "&cYou are already on that server!");
        TELEPORTING = config.getString("TELEPORTING", "&2Teleporting you to {name}");
        ALREADY_TELEPORTING = config.getString("ALREADY_TELEPORTING", "&cAlready teleporting you to a server");
    }

    private void registerCommands() {
        for (String data : proxy.getServers().keySet()) {
            ProxyServer.getInstance().getPluginManager().registerCommand(this, new ServerCommand(data, "slashserver." + data));
        }

        ProxyServer.getInstance().getPluginManager().registerCommand(this, new SlashServerReloadCommand("reloadss", "slashserver.reload", "reloadslashserver", "slashserverreload", "ssreload"));
    }

    public static HashMap<String, Integer> getTime() {
        return time;
    }

    public static ArrayList<ProxiedPlayer> getTasks() {
        return tasks;
    }

    public static Config getConfig() {
        return config;
    }
}
