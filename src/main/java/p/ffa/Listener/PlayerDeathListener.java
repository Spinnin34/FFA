package p.ffa.Listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

public class PlayerDeathListener implements Listener{


    private final String lobbyWorldName = "Lobby";

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        player.setSaturation(0);
    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        event.getPlayer().setSaturation(0.0f);
    }

    @EventHandler
    public void onFoodLevelChange(FoodLevelChangeEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            if (player.getWorld().getName().equalsIgnoreCase(lobbyWorldName)) {
                player.setFoodLevel(20);
                player.setSaturation(0);
                event.setCancelled(true);
            } else {
                player.setSaturation(0);
            }
        }
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        if (player.getWorld().getName().equalsIgnoreCase(lobbyWorldName)) {
            player.setFoodLevel(20);
            player.setSaturation(0);
        }
    }
}
