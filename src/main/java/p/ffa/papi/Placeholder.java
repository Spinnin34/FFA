package p.ffa.papi;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import p.ffa.FFA;
import p.ffa.Manager.StatsManager;

import java.util.UUID;

public class Placeholder extends PlaceholderExpansion {

    private FFA plugin;
    private StatsManager statsManager;

    public Placeholder(FFA plugin, StatsManager statsManager) {
        this.statsManager = statsManager;
    }

    @Override
    public String getIdentifier() {
        return "ffa";
    }

    @Override
    public String getAuthor() {
        return "Spinnin";
    }

    @Override
    public String getVersion() {
        return "1.0";
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public String onPlaceholderRequest(Player player, String identifier) {
        if (player == null) {
            return "";
        }

        UUID uuid = player.getUniqueId();

        switch (identifier) {
            case "kills":
                return String.valueOf(statsManager.getKills(uuid.toString()));
            case "deaths":
                return String.valueOf(statsManager.getDeaths(uuid.toString()));
            case "racha":
                return String.valueOf(statsManager.getStreak(uuid.toString()));
            default:
                return null;
        }
    }
}
