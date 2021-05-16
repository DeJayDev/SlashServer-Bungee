package com.minecraftdimensions.slashserver;

import java.util.concurrent.TimeUnit;
import net.md_5.bungee.api.Callback;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class ServerCommand extends Command {

    String name;
    private final ServerInfo server;

    public ServerCommand(String name, String permission, String... aliases) {
        super(name, permission, aliases);
        this.name = name;
        server = ProxyServer.getInstance().getServerInfo(name);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!(sender instanceof ProxiedPlayer)) {
            return;
        }

        final ProxiedPlayer pp = (ProxiedPlayer) sender;

        if (pp.getServer().getInfo().getName().equalsIgnoreCase(name)) {
            pp.sendMessage(TextComponent.fromLegacyText(SlashServer.ALREADY_ON_SERVER.replace("{name}", name)));
        } else {
            if (SlashServer.getTasks().contains(pp)) {
                pp.sendMessage(TextComponent.fromLegacyText(SlashServer.ALREADY_TELEPORTING.replace("{name}", name)));
                return;
            }

            if(!SlashServer.TELEPORTING.equals("")) {
                pp.sendMessage(TextComponent.fromLegacyText(
                    SlashServer.TELEPORTING
                        .replace("{name}", name)
                        .replace("{time}", (SlashServer.getTime().get(name) / 1000) + "")));
            }

            SlashServer.getTasks().add(pp);
            ProxyServer.getInstance().getScheduler().schedule(SlashServer.INSTANCE, () -> {
                if (SlashServer.getTasks().contains(pp)) {
                    pp.connect(server);
                    SlashServer.getTasks().remove(pp);
                }

            }, SlashServer.getTime().get(name), TimeUnit.MILLISECONDS);

        }

    }

}
