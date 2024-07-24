package p.ffa.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import p.ffa.handlers.FFAPlayersHandler;
import p.ffa.utils.SpawnUtils;

public class PlayerJoinListener implements Listener {
    private final SpawnUtils sup;
    private final FFAPlayersHandler fph;

    public PlayerJoinListener(SpawnUtils sup, FFAPlayersHandler fph) {
        this.sup = sup;
        this.fph = fph;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        teleportToMainSpawn(event.getPlayer());
    }

    private void teleportToMainSpawn(Player player) {
        if (sup == null) {
            player.sendMessage("Error: SpawnUtils is not initialized.");
            return;
        }

        if (sup.getMainSpawn() == null || !fph.isOnFFA(player.getUniqueId())) {
            return;
        }

        sup.teleportEntityToSpawn(sup.getMainSpawn().getName(), player);
    }
}
