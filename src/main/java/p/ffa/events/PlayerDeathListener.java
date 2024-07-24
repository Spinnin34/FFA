package p.ffa.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import p.ffa.handlers.FFAPlayersHandler;
import p.ffa.utils.SpawnUtils;

public class PlayerDeathListener implements Listener
{
    public PlayerDeathListener(FFAPlayersHandler fph, SpawnUtils su)
    {
        
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent ev)
    {
    
    }
}
