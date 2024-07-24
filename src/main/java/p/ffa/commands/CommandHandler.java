package p.ffa.commands;

import org.bukkit.plugin.java.JavaPlugin;
import p.ffa.FFA;
import p.ffa.handlers.FFAPlayersHandler;
import p.ffa.utils.KitUtils;
import p.ffa.utils.SpawnUtils;

public class CommandHandler
{
    private FFA plugin;

    public CommandHandler(FFA plugin)
    {
        this.plugin = plugin;
    }

    public void registerCommands(SpawnUtils sp, KitUtils ku, FFAPlayersHandler fph, SpawnUtils sputils)
    {
        plugin.getCommand("ffautils").setExecutor(new CommandFFAUtils(sp, ku, fph));
        plugin.getCommand("loadme").setExecutor(new CommandLoadme(fph));
        plugin.getCommand("unloadme").setExecutor(new CommandUnloadme(fph, sputils));
    }
}
