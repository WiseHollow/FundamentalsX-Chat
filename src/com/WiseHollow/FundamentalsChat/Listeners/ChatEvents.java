package com.WiseHollow.FundamentalsChat.Listeners;

import com.WiseHollow.Fundamentals.Language;
import com.WiseHollow.FundamentalsChat.EmojiChat;
import com.WiseHollow.FundamentalsChat.Main;
import com.WiseHollow.FundamentalsChat.Settings;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by John on 10/14/2016.
 */
public class ChatEvents implements Listener {

    @EventHandler
    public void FormatPrefix(AsyncPlayerChatEvent event) {
        if (!event.isCancelled() && Main.getChat() != null) {
            String format = "<%1$s> %2$s";

            String[] gPrefixes = Main.getChat().getPlayerGroups(event.getPlayer());
            String gPrefix = null;
            if (gPrefixes.length > 0) {
                gPrefix = Main.getChat().getGroupPrefix(event.getPlayer().getWorld(), gPrefixes[0]);
                format = ChatColor.translateAlternateColorCodes('&', gPrefix) + format;
            }
            String uPrefix = Main.getChat().getPlayerPrefix(event.getPlayer());
            if (uPrefix != null && !uPrefix.equals(gPrefix)) {
                format = ChatColor.translateAlternateColorCodes('&', uPrefix) + format;
            }

            event.setFormat(format);
        }
    }

    @EventHandler
    public void EmojiConvert(AsyncPlayerChatEvent event) {
        if (event.isCancelled() || !Settings.UseEmojiChat)
            return;

        event.setMessage(EmojiChat.Convert(event.getMessage()));
    }

    @EventHandler
    public void ColorChat(AsyncPlayerChatEvent event) {
        if (event.isCancelled())
            return;
        //TODO: PERMISSIONS
        event.setMessage(ChatColor.translateAlternateColorCodes('&', event.getMessage()));
    }

    @EventHandler
    public void PlayerMention(AsyncPlayerChatEvent event) {
        if (event.isCancelled() || !Settings.UsePlayerMention)
            return;

        List<Player> mentioned = new ArrayList<>();

        String[] msg = event.getMessage().split(" ");
        for (String s : msg) {
            if (s.startsWith("@")) {
                Player target = Bukkit.getPlayer(s.replaceAll("@", ""));
                if (target != null && !mentioned.contains(target)) {
                    mentioned.add(target);
                    target.sendMessage(Language.PREFIX_COLOR + "You were mentioned by: " + ChatColor.RESET + event.getPlayer().getName());
                    target.playSound(target.getLocation(), Sound.BLOCK_NOTE_BLOCK_HARP, 10, 1);
                }
            }
        }
    }
}
