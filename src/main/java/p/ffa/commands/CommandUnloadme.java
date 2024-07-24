package p.ffa.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import p.ffa.handlers.FFAPlayersHandler;
import p.ffa.objects.FFAPlayer;
import p.ffa.objects.SpawnLocation;
import p.ffa.utils.SpawnUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CommandUnloadme implements CommandExecutor
{
    private FFAPlayersHandler fph;
    private SpawnUtils sputils;

    public CommandUnloadme(FFAPlayersHandler fph, SpawnUtils sputils)
    {
        this.fph = fph;
        this.sputils = sputils;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        if (!(sender instanceof Player))
        {
            sender.sendMessage(ChatColor.RED + "Only players can execute this command.");
            return true;
        }

        Player p = (Player) sender;

        if (!p.hasPermission("ffautils.unloadme"))
        {
            p.sendMessage(ChatColor.RED + "You do not have permission to execute this command.");
            return true;
        }

        FFAPlayer ffap = FFAPlayersHandler.ffa_players.get(p.getUniqueId());
        SpawnLocation main_spawn = sputils.getMainSpawn();

        if (ffap == null)
        {
            p.sendMessage(ChatColor.RED + "You are not in FFA");
            Bukkit.getServer().getLogger().warning("Tried to unload an unloaded player");
            return true;
        }

        ffap.setPlayerKit(null);
        ffap.setPlayerKit(null);

        if (main_spawn != null)
        {
            ffap.setPlayerSpawn(main_spawn);
            ffap.setPlayerSpawn(main_spawn);
        }
        else
        {
            p.sendMessage(ChatColor.RED + "Main spawn does not exist");
        }

        fph.removePlayerFromFFA(p);
        return true;
    }
}
