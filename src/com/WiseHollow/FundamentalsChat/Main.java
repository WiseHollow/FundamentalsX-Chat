package com.WiseHollow.FundamentalsChat;

import com.WiseHollow.FundamentalsChat.Commands.CommandMute;
import com.WiseHollow.FundamentalsChat.Listeners.ChatEvents;
import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public class Main extends JavaPlugin
{
    public static Main plugin = null;
    public static Logger logger = null;
    private static Permission perms = null;
    private static Chat chat = null;

    public static Chat getChat() {
        return chat;
    }

    @Override
    public void onEnable()
    {
        plugin = this;
        logger = this.getLogger();

        logger.info("Permissions hook: " + setupPermissions());
        logger.info("Chat hook: " + setupChat());

        this.getCommand("Mute").setExecutor(new CommandMute());

        getServer().getPluginManager().registerEvents(new Settings(), this);
        getServer().getPluginManager().registerEvents(new ChatEvents(), this);

        logger.info(plugin.getName() + " is now enabled!");
    }
    @Override
    public void onDisable()
    {
        logger.info(plugin.getName() + " is now disabled!");
    }

    private boolean setupPermissions()
    {
        RegisteredServiceProvider<Permission> rsp = getServer().getServicesManager().getRegistration(Permission.class);
        try {
            perms = rsp.getProvider();
        } catch (Exception exception) { }
        return perms != null;
    }

    private boolean setupChat()
    {
        RegisteredServiceProvider<Chat> rsp = getServer().getServicesManager().getRegistration(Chat.class);
        try {
            chat = rsp.getProvider();
        } catch (Exception exception) { }
        return chat != null;
    }

}
