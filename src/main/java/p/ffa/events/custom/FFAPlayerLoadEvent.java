package p.ffa.events.custom;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Cancellable;
import p.ffa.objects.FFAPlayer;

public class FFAPlayerLoadEvent extends Event implements Cancellable {
    private boolean isCancelled;
    private final FFAPlayer ffpl;
    private static final HandlerList HANDLERS = new HandlerList();

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    public FFAPlayerLoadEvent(FFAPlayer a) {
        this.isCancelled = false;
        this.ffpl = a;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    @Override
    public boolean isCancelled() {
        return this.isCancelled;
    }

    @Override
    public void setCancelled(boolean isCancelled) {
        this.isCancelled = isCancelled;
    }

    public FFAPlayer getFFAPlayer() {
        return this.ffpl;
    }
}
