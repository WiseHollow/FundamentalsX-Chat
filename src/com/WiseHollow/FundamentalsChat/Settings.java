package com.WiseHollow.FundamentalsChat;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.PluginEnableEvent;

/**
 * Created by John on 10/15/2016.
 */
public class Settings implements Listener {
    public static boolean UseEmojiChat = true;
    public static boolean UsePlayerMention = true;

    private static void load() {
        FileConfiguration configuration = com.WiseHollow.Fundamentals.Main.plugin.getConfig();

        UseEmojiChat = configuration.getBoolean("Emoji_Chat");
        UsePlayerMention = configuration.getBoolean("Player_Mention");

        if (UseEmojiChat) {
            Main.plugin.getLogger().info("Registering Emojis: " + EmojiChat.Load());
        }
    }

    @EventHandler
    public void loadSettingsWhenFundamentalsLoads(PluginEnableEvent event) {
        if (event.getPlugin().getName().equalsIgnoreCase("FundamentalsX")) {
            Bukkit.getScheduler().runTaskLater(Main.plugin, () -> {
                load();
            }, 1L);
        }
    }
}
