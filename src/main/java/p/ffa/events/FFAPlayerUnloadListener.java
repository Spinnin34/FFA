package p.ffa.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.potion.PotionEffect;
import p.ffa.events.custom.FFAPlayerUnloadEvent;
import p.ffa.handlers.FFAPlayersHandler;
import p.ffa.objects.FFAPlayer;
import p.ffa.utils.SpawnUtils;


public class FFAPlayerUnloadListener implements Listener
{
    private SpawnUtils su;

    public FFAPlayerUnloadListener(SpawnUtils su)
    {
        this.su = su;
    }

    @EventHandler
    public void onPlayerUnload(FFAPlayerUnloadEvent p)
    {
        Player pj = p.getPlayer();
        FFAPlayer pf = FFAPlayersHandler.ffa_players.get(pj.getUniqueId());
        pf.getPlayer().getInventory().clear();
        su.teleportEntityToSpawn(pf.getPlayerChosenSpawn().getName(), pj);
        for (PotionEffect effect : pf.getPlayer().getActivePotionEffects()) {
            pf.getPlayer().removePotionEffect(effect.getType());
        }
    }
}
