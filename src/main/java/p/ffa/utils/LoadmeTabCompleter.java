package p.ffa.utils;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import p.ffa.objects.PlayerKit;
import p.ffa.objects.SpawnLocation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class LoadmeTabCompleter implements TabCompleter {
    private final Map<String, PlayerKit> kits;
    private final Map<String, SpawnLocation> spawns;

    public LoadmeTabCompleter(Map<String, PlayerKit> kits, Map<String, SpawnLocation> spawns) {
        this.kits = kits;
        this.spawns = spawns;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 1) {
            return kits.keySet().stream()
                    .map(String::toLowerCase)
                    .filter(kit -> kit.startsWith(args[0].toLowerCase()))
                    .collect(Collectors.toList());
        } else if (args.length == 2) {
            return spawns.keySet().stream()
                    .map(String::toLowerCase)
                    .filter(spawn -> spawn.startsWith(args[1].toLowerCase()))
                    .collect(Collectors.toList());
        }

        return new ArrayList<>();
    }
}
