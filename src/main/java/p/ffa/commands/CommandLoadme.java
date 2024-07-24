package p.ffa.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import p.ffa.handlers.FFAPlayersHandler;
import p.ffa.objects.FFAPlayer;
import p.ffa.objects.PlayerKit;
import p.ffa.objects.SpawnLocation;
import p.ffa.utils.KitUtils;
import p.ffa.utils.SpawnUtils;

public class CommandLoadme implements CommandExecutor {
    private final FFAPlayersHandler fph;

    public CommandLoadme(FFAPlayersHandler fph) {
        this.fph = fph;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Only players can execute this command.");
            return true;
        }

        Player p = (Player) sender;

        if (!p.hasPermission("ffautils.loadme")) {
            p.sendMessage(ChatColor.RED + "You do not have permission to execute this command.");
            return true;
        }

        if (args.length < 2) {
            p.sendMessage(ChatColor.RED + "Usage: /loadme <kit_name> <spawn_name>");
            return true;
        }

        String kit = args[0].toUpperCase();
        String spawn = args[1].toUpperCase();

        // Debug messages
        p.sendMessage(ChatColor.YELLOW + "Debug: Kit - " + kit + ", Spawn - " + spawn);

        SpawnLocation sa = SpawnUtils.spawns.get(spawn);
        PlayerKit ka = KitUtils.kits.get(kit);
        FFAPlayer ffap = FFAPlayersHandler.ffa_players.get(p.getUniqueId());

        if (ffap != null) {
            Bukkit.getServer().getLogger().warning("Tried to load a loaded player");
            p.sendMessage(ChatColor.RED + "You are already in FFA");
            return true;
        }

        if (sa == null) {
            p.sendMessage(ChatColor.RED + "This spawn does not exist");
            return true;
        }

        if (ka == null) {
            p.sendMessage(ChatColor.RED + "This kit does not exist");
            return true;
        }

        if (!p.hasPermission("ffautils.loadme.kit." + kit)) {
            p.sendMessage(ChatColor.RED + "You don't have permission to use this kit");
            return true;
        }

        if (!p.hasPermission("ffautils.loadme.spawn." + spawn)) {
            p.sendMessage(ChatColor.RED + "You don't have permission to use this spawn");
            return true;
        }

        if (!sa.getType().equals(SpawnLocation.SPAWN)) {
            fph.addPlayerToFFA(p, ka, sa);
            p.sendMessage(ChatColor.GREEN + "You have been loaded with kit " + kit + " at spawn " + spawn);
        } else {
            p.sendMessage(ChatColor.RED + "You can't load yourself to the main spawn");
        }

        return true;
    }
}
