package p.ffa.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import p.ffa.events.custom.FFAPlayerLoadEvent;
import p.ffa.objects.FFAPlayer;
import p.ffa.utils.KitUtils;
import p.ffa.utils.SpawnUtils;

public class FFAPlayerLoadListener implements Listener
{
    private KitUtils ku;
    private SpawnUtils su;

    public FFAPlayerLoadListener(KitUtils ku, SpawnUtils su)
    {
        this.su = su;
        this.ku = ku;
    }
    @EventHandler
    public void onFFAPlayerLoad(FFAPlayerLoadEvent event)
    {
        FFAPlayer fp = event.getFFAPlayer();
        Player p = fp.getPlayer();
        ku.setPlayerKit(fp.getLastPlayerKit().getName(), p);
        su.teleportEntityToSpawn(fp.getPlayerChosenSpawn().getName(), p);
    }
}
