package p.ffa;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.TabCompleter;
import org.bukkit.plugin.java.JavaPlugin;
import p.ffa.Listener.PlayerDeathDataBase;
import p.ffa.Listener.PlayerDeathListener;
import p.ffa.Manager.Database;
import p.ffa.Manager.StatsManager;
import p.ffa.commands.CommandFFAUtils;
import p.ffa.commands.CommandLoadme;
import p.ffa.commands.CommandUnloadme;
import p.ffa.config.KitsConfig;
import p.ffa.config.SpawnsConfig;
import p.ffa.events.*;
import p.ffa.handlers.FFAPlayersHandler;
import p.ffa.papi.Placeholder;
import p.ffa.utils.*;

import java.sql.SQLException;

public final class FFA extends JavaPlugin {

    private Database database;
    private StatsManager statsManager;
    private SpawnsConfig spawnsConfig;
    private KitsConfig kitsConfig;
    private SpawnUtils spawnUtils;
    private KitUtils kitUtils;
    private FFAPlayersHandler ffaPlayersHandler;

    @Override
    public void onEnable() {
        // Inicializar la base de datos
        database = new Database(getDataFolder());
        try {
            database.connect();
        } catch (SQLException e) {
            e.printStackTrace();
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        statsManager = new StatsManager(database);

        getServer().getPluginManager().registerEvents(new PlayerDeathDataBase(statsManager), this);

        // Verificar y crear carpetas de datos
        if (!getDataFolder().exists()) {
            getDataFolder().mkdirs();
        }

        /*
        // Inicializar configuraciones
        spawnsConfig = new SpawnsConfig(this);
        spawnsConfig.createSpawnConfiguration();

        kitsConfig = new KitsConfig(this);
        kitsConfig.createKitsConfiguration();

        // Inicializar utilidades
        spawnUtils = new SpawnUtils(this, spawnsConfig);
        kitUtils = new KitUtils(this, kitsConfig);
        ffaPlayersHandler = new FFAPlayersHandler();

         */
        // Registrar placeholders si PlaceholderAPI está presente
        if (getServer().getPluginManager().getPlugin("PlaceholderAPI") != null) {
            new Placeholder(this, statsManager).register();
        }

        // Registrar eventos y comandos
        registerFFAUtilsEvents();
        registerFFAUtilsCommands();
    }

    @Override
    public void onDisable() {
        try {
            if (database != null) {
                database.disconnect();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void registerFFAUtilsEvents() {
        /*
        getServer().getPluginManager().registerEvents(new PlayerJoinListener(spawnUtils, ffaPlayersHandler), this);

        getServer().getPluginManager().registerEvents(new FFAPlayerLoadListener(kitUtils, spawnUtils), this);
        getServer().getPluginManager().registerEvents(new FFAPlayerUnloadListener(spawnUtils), this);
        getServer().getPluginManager().registerEvents(new PlayerDisconnectListener(ffaPlayersHandler, spawnUtils), this);
        getServer().getPluginManager().registerEvents(new PlayerRespawnListener(ffaPlayersHandler, spawnUtils), this);
        */

        // Spinnin34

        getServer().getPluginManager().registerEvents(new PlayerDeathListener(), this);
    }

    private void registerFFAUtilsCommands() {
        /*
        CommandExecutor commandExecutor = new CommandFFAUtils(spawnUtils, kitUtils, ffaPlayersHandler);
        TabCompleter tabCompleter = new FFAUtilsTabCompleter();
        FFAPlayersHandler fph = new FFAPlayersHandler();192.168.1.135
        SpawnUtils sputils = new SpawnUtils(this, new SpawnsConfig(this));

        getCommand("futils").setExecutor(commandExecutor);
        getCommand("futils").setTabCompleter(tabCompleter);

        getCommand("ffa").setExecutor(commandExecutor);
        getCommand("ffa").setTabCompleter(tabCompleter);

        getCommand("ffautils").setExecutor(commandExecutor);
        getCommand("ffautils").setTabCompleter(tabCompleter);

        getCommand("loadme").setExecutor(new CommandLoadme(fph));
        getCommand("loadme").setTabCompleter(new LoadmeTabCompleter());

        getCommand("unloadme").setExecutor(new CommandUnloadme(fph, sputils));
        getCommand("unloadme").setTabCompleter(new UnloadmeTabCompleter());
        */
    }

    public StatsManager getStatsManager() {
        return statsManager;
    }
}
