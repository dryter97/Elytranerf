package co.ignitus.elytranerf;

import co.ignitus.elytranerf.events.PlayerEvents;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public final class ElytraNerf extends JavaPlugin {

    private final CommandSender cs = Bukkit.getConsoleSender();

    private static ElytraNerf instance;

    @Override
    public void onEnable() {
        instance = this;
        cs.sendMessage(ChatColor.GREEN + ChatColor.STRIKETHROUGH.toString() + "---------------------------");
        cs.sendMessage(ChatColor.GREEN + "  Enabling ElytraNerf");
        cs.sendMessage(ChatColor.GREEN + " Developed by Ignitus Co.");
        cs.sendMessage(ChatColor.GREEN + ChatColor.STRIKETHROUGH.toString() + "---------------------------");
        saveDefaultConfig();
        getServer().getPluginManager().registerEvents(new PlayerEvents(), this);
    }

    @Override
    public void onDisable() {
        cs.sendMessage(ChatColor.RED + ChatColor.STRIKETHROUGH.toString() + "---------------------------");
        cs.sendMessage(ChatColor.RED + "   Disabling ElytraNerf");
        cs.sendMessage(ChatColor.RED + "  Developed by Ignitus Co.");
        cs.sendMessage(ChatColor.RED + ChatColor.STRIKETHROUGH.toString() + "---------------------------");
    }

    public static ElytraNerf getInstance() {
        return instance;
    }
}
