package p.ffa.commands;

import java.util.Collection;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.PotionEffect;
import p.ffa.handlers.FFAPlayersHandler;
import p.ffa.objects.FFAPlayer;
import p.ffa.objects.PlayerKit;
import p.ffa.objects.SpawnLocation;
import p.ffa.utils.KitUtils;
import p.ffa.utils.SpawnUtils;

public class CommandFFAUtils implements CommandExecutor {
    private SpawnUtils sp;
    private KitUtils ku;
    private FFAPlayersHandler fph;

    public CommandFFAUtils(SpawnUtils sp, KitUtils ku, FFAPlayersHandler fph) {
        this.sp = sp;
        this.ku = ku;
        this.fph = fph;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            sender.sendMessage(ChatColor.YELLOW + "Please specify a subcommand");
            return true;
        }

        switch (args[0].toLowerCase()) {
            case "list":
                handleList(sender, args);
                break;
            case "create":
                handleCreate(sender, args);
                break;
            case "remove":
                handleRemove(sender, args);
                break;
            case "use":
                handleUse(sender, args);
                break;
            case "reload":
                handleReload(sender, args);
                break;
            case "loadall":
                handleLoadAll(sender, args);
                break;
            case "unloadall":
                handleUnloadAll(sender, args);
                break;
            default:
                sender.sendMessage(ChatColor.RED + "Unknown subcommand");
                break;
        }
        return true;
    }

    private void handleList(CommandSender sender, String[] args) {
        if (args.length < 2) {
            sender.sendMessage(ChatColor.RED + "Bad arguments");
            return;
        }

        switch (args[1].toLowerCase()) {
            case "spawns":
                sender.sendMessage(ChatColor.AQUA + "Available spawns:");
                SpawnUtils.spawns.forEach((spawn_name, spawn) -> {
                    ChatColor color_type = spawn.getType().equals(SpawnLocation.SPAWN) ? ChatColor.AQUA : ChatColor.GRAY;
                    sender.sendMessage(spawn_name + ChatColor.YELLOW + " - " + color_type + spawn.getType());
                });
                break;
            case "kits":
                sender.sendMessage(ChatColor.AQUA + "Available kits:");
                ku.getExistingKits().forEach(sender::sendMessage);
                break;
            case "ffaplayers":
                sender.sendMessage(ChatColor.AQUA + "Active FFA Players:");
                FFAPlayersHandler.ffa_players.forEach((uuid, ffaplayer) -> {
                    Player player = Bukkit.getPlayer(uuid);
                    if (player != null) {
                        sender.sendMessage(player.getName() + ChatColor.YELLOW + " - " + ChatColor.GRAY + ffaplayer.getPlayerKit().getName() + ChatColor.DARK_GRAY + "(" + ffaplayer.getLastPlayerKit().getName() + ")");
                    }
                });
                break;
            default:
                sender.sendMessage(ChatColor.RED + "Unknown list subcommand");
                break;
        }
    }

    private void handleCreate(CommandSender sender, String[] args) {
        if (args.length < 3 || !(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Bad arguments or not a player");
            return;
        }

        Player player = (Player) sender;
        switch (args[1].toLowerCase()) {
            case "kit":
                createKit(player, args[2]);
                break;
            case "spawn":
                if (args.length < 4) {
                    sender.sendMessage(ChatColor.RED + "Bad arguments");
                    return;
                }
                createSpawn(player, args[2], args[3]);
                break;
            default:
                sender.sendMessage(ChatColor.RED + "Unknown create subcommand");
                break;
        }
    }

    private void createKit(Player player, String kitName) {
        PlayerInventory inventory = player.getInventory();
        if (inventory.isEmpty()) {
            player.sendMessage(ChatColor.RED + "Cannot create an empty kit");
            return;
        }

        ItemStack[] items = inventory.getContents();
        Collection<PotionEffect> potionEffects = player.getActivePotionEffects();

        if (ku.createKit(kitName.toUpperCase(), items, potionEffects)) {
            player.sendMessage(ChatColor.AQUA + "Kit " + kitName + " created");
        } else {
            player.sendMessage(ChatColor.RED + "Kit " + kitName + " already exists");
        }
    }

    private void createSpawn(Player player, String spawnName, String type) {
        if (sp.createSpawn(spawnName.toUpperCase(), player.getLocation(), type.toLowerCase())) {
            player.sendMessage(ChatColor.AQUA + "Spawn " + spawnName + " created");
        } else {
            player.sendMessage(ChatColor.RED + "This spawn already exists");
        }
    }

    private void handleRemove(CommandSender sender, String[] args) {
        if (args.length < 3) {
            sender.sendMessage(ChatColor.RED + "Bad arguments");
            return;
        }

        switch (args[1].toLowerCase()) {
            case "kit":
                removeKit(sender, args[2]);
                break;
            case "spawn":
                removeSpawn(sender, args[2]);
                break;
            default:
                sender.sendMessage(ChatColor.RED + "Unknown remove subcommand");
                break;
        }
    }

    private void removeKit(CommandSender sender, String kitName) {
        if (ku.removeKit(kitName.toUpperCase())) {
            sender.sendMessage(ChatColor.AQUA + "Kit " + kitName + " deleted successfully");
        } else {
            sender.sendMessage(ChatColor.RED + "Kit " + kitName + " does not exist");
        }
    }

    private void removeSpawn(CommandSender sender, String spawnName) {
        if (sp.removeSpawn(spawnName.toUpperCase())) {
            sender.sendMessage(ChatColor.AQUA + "Spawn " + spawnName + " deleted successfully");
        } else {
            sender.sendMessage(ChatColor.RED + "Spawn " + spawnName + " does not exist");
        }
    }

    private void handleUse(CommandSender sender, String[] args) {
        if (args.length < 3) {
            sender.sendMessage(ChatColor.RED + "Bad arguments");
            return;
        }

        switch (args[1].toLowerCase()) {
            case "kit":
                useKit(sender, args);
                break;
            case "spawn":
                useSpawn(sender, args);
                break;
            default:
                sender.sendMessage(ChatColor.RED + "Unknown use subcommand");
                break;
        }
    }

    private void useKit(CommandSender sender, String[] args) {
        if (args.length < 3) {
            sender.sendMessage(ChatColor.RED + "Bad arguments");
            return;
        }

        String kitName = args[2];
        Player target = args.length > 3 ? Bukkit.getPlayer(args[3]) : (sender instanceof Player ? (Player) sender : null);

        if (target != null) {
            if (ku.setPlayerKit(kitName.toUpperCase(), target)) {
                sender.sendMessage(ChatColor.AQUA + "Kit " + kitName + " loaded to " + target.getName());
            } else {
                sender.sendMessage(ChatColor.RED + "Kit " + kitName + " does not exist");
            }
        } else {
            sender.sendMessage(ChatColor.RED + "Player not found or bad command syntax for non-player executor");
        }
    }

    private void useSpawn(CommandSender sender, String[] args) {
        if (args.length < 3) {
            sender.sendMessage(ChatColor.RED + "Bad arguments");
            return;
        }

        String spawnName = args[2];
        Player target = args.length > 3 ? Bukkit.getPlayer(args[3]) : (sender instanceof Player ? (Player) sender : null);

        if (target != null) {
            if (sp.teleportEntityToSpawn(spawnName.toUpperCase(), target)) {
                sender.sendMessage(ChatColor.AQUA + target.getName() + " teleported to " + spawnName);
            } else {
                sender.sendMessage(ChatColor.RED + "Spawn " + spawnName + " does not exist");
            }
        } else {
            sender.sendMessage(ChatColor.RED + "Player not found or bad command syntax for non-player executor");
        }
    }

    private void handleReload(CommandSender sender, String[] args) {
        String reloadedKits = String.valueOf(ku.reloadKits());
        String reloadedSpawns = String.valueOf(sp.reloadSpawns());

        if (args.length < 2) {
            sender.sendMessage(ChatColor.AQUA + "Reloading Kits and Spawns");
            sender.sendMessage(ChatColor.AQUA + reloadedKits + " kits reloaded");
            sender.sendMessage(ChatColor.AQUA + reloadedSpawns + " spawns reloaded");
        } else {
            switch (args[1].toLowerCase()) {
                case "kits":
                    sender.sendMessage(ChatColor.AQUA + "Reloading Kits");
                    sender.sendMessage(ChatColor.AQUA + reloadedKits + " kits reloaded");
                    break;
                case "spawns":
                    sender.sendMessage(ChatColor.AQUA + "Reloading Spawns");
                    sender.sendMessage(ChatColor.AQUA + reloadedSpawns + " spawns reloaded");
                    break;
                default:
                    sender.sendMessage(ChatColor.RED + "Unknown reload subcommand");
                    break;
            }
        }
    }

    private void handleLoadAll(CommandSender sender, String[] args) {
        if (args.length < 4) {
            sender.sendMessage(ChatColor.RED + "Bad arguments");
            return;
        }

        String kit = args[2];
        String spawn = args[3];

        if (!KitUtils.kits.containsKey(kit.toUpperCase())) {
            sender.sendMessage(ChatColor.RED + "Kit " + kit + " does not exist");
            return;
        }

        if (!SpawnUtils.spawns.containsKey(spawn.toUpperCase())) {
            sender.sendMessage(ChatColor.RED + "Spawn " + spawn + " does not exist");
            return;
        }

        if (SpawnUtils.spawns.get(spawn.toUpperCase()).getType().equals(SpawnLocation.SPAWN)) {
            sender.sendMessage(ChatColor.RED + "You can't load to the main spawn");
            return;
        }

        PlayerKit playerKit = KitUtils.kits.get(kit.toUpperCase());
        SpawnLocation spawnLocation = SpawnUtils.spawns.get(spawn.toUpperCase());

        switch (args[1].toLowerCase()) {
            case "loaded":
                loadAllLoadedPlayers(sender, playerKit, spawnLocation);
                break;
            case "unloaded":
                loadAllUnloadedPlayers(sender, playerKit, spawnLocation);
                break;
            case "all":
                loadAllPlayers(sender, playerKit, spawnLocation);
                break;
            default:
                sender.sendMessage(ChatColor.RED + "Unknown loadall subcommand");
                break;
        }
    }

    private void loadAllLoadedPlayers(CommandSender sender, PlayerKit kit, SpawnLocation spawn) {
        FFAPlayersHandler.ffa_players.forEach((uuid, ffaplayer) -> {
            ffaplayer.setPlayerKit(kit);
            ffaplayer.setPlayerSpawn(spawn);
            ku.setPlayerKit(kit.getName(), ffaplayer.getPlayer());
            sp.teleportEntityToSpawn(spawn.getName(), ffaplayer.getPlayer());
        });
    }

    private void loadAllUnloadedPlayers(CommandSender sender, PlayerKit kit, SpawnLocation spawn) {
        Bukkit.getOnlinePlayers().forEach(player -> fph.addPlayerToFFA(player, kit, spawn));
    }

    private void loadAllPlayers(CommandSender sender, PlayerKit kit, SpawnLocation spawn) {
        Bukkit.getOnlinePlayers().forEach(player -> {
            FFAPlayer ffaplayer;
            if (!FFAPlayersHandler.ffa_players.containsKey(player.getUniqueId())) {
                ffaplayer = new FFAPlayer(player, kit, spawn);
                ffaplayer.setPlayerKit(kit);
                ffaplayer.setPlayerSpawn(spawn);
                fph.addPlayerToFFA(ffaplayer.getPlayer(), kit, spawn);
            } else {
                ffaplayer = FFAPlayersHandler.ffa_players.get(player.getUniqueId());
                ffaplayer.setPlayerKit(kit);
                ffaplayer.setPlayerSpawn(spawn);
                ku.setPlayerKit(kit.getName(), ffaplayer.getPlayer());
                sp.teleportEntityToSpawn(spawn.getName(), ffaplayer.getPlayer());
            }
        });
    }

    private void handleUnloadAll(CommandSender sender, String[] args) {
        SpawnLocation mainSpawn = sp.getMainSpawn();
        if (mainSpawn == null) {
            sender.sendMessage(ChatColor.RED + "There is no main spawn defined");
            return;
        }

        FFAPlayersHandler.ffa_players.forEach((uuid, ffaplayer) -> {
            ffaplayer.setPlayerKit(null);
            ffaplayer.setPlayerSpawn(mainSpawn);
            fph.removePlayerFromFFA(ffaplayer.getPlayer());
        });
    }
}
