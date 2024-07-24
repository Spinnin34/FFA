package p.ffa.utils;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

public class FFAUtilsTabCompleter implements TabCompleter {

    private static final List<String> COMMANDS = Arrays.asList(
            "list", "create", "remove", "use", "reload", "loadall", "unloadall"
    );

    private static final List<String> LIST_SUBCOMMANDS = Arrays.asList(
            "spawns", "kits", "ffaplayers"
    );

    private static final List<String> CREATE_SUBCOMMANDS = Arrays.asList(
            "kit", "spawn"
    );

    private static final List<String> REMOVE_SUBCOMMANDS = Arrays.asList(
            "kit", "spawn"
    );

    private static final List<String> USE_SUBCOMMANDS = Arrays.asList(
            "kit", "spawn"
    );

    private static final List<String> LOADALL_SUBCOMMANDS = Arrays.asList(
            "loaded", "unloaded", "all"
    );

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 1) {
            return getMatchingStrings(args[0], COMMANDS);
        }

        switch (args[0].toLowerCase()) {
            case "list":
                if (args.length == 2) {
                    return getMatchingStrings(args[1], LIST_SUBCOMMANDS);
                }
                break;
            case "create":
                if (args.length == 2) {
                    return getMatchingStrings(args[1], CREATE_SUBCOMMANDS);
                }
                break;
            case "remove":
                if (args.length == 2) {
                    return getMatchingStrings(args[1], REMOVE_SUBCOMMANDS);
                }
                break;
            case "use":
                if (args.length == 2) {
                    return getMatchingStrings(args[1], USE_SUBCOMMANDS);
                }
                break;
            case "reload":
                if (args.length == 2) {
                    return getMatchingStrings(args[1], Arrays.asList("kits", "spawns"));
                }
                break;
            case "loadall":
                if (args.length == 2) {
                    return getMatchingStrings(args[1], LOADALL_SUBCOMMANDS);
                }
                break;
        }

        return Collections.emptyList();
    }

    private List<String> getMatchingStrings(String input, List<String> options) {
        List<String> result = new ArrayList<>();
        for (String option : options) {
            if (option.toLowerCase().startsWith(input.toLowerCase())) {
                result.add(option);
            }
        }
        return result;
    }
}

