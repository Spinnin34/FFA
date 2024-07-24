package p.ffa.Listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import p.ffa.Manager.StatsManager;

public class PlayerDeathDataBase implements Listener {
    private final StatsManager statsManager;

    public PlayerDeathDataBase(StatsManager statsManager) {
        this.statsManager = statsManager;
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        String victimUUID = event.getEntity().getUniqueId().toString();
        statsManager.incrementDeaths(victimUUID);

        if (event.getEntity().getKiller() != null) {
            String killerUUID = event.getEntity().getKiller().getUniqueId().toString();
            statsManager.incrementKills(killerUUID);
        }
    }
}
