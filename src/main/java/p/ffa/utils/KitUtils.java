package p.ffa.utils;

import java.util.Map;
import java.util.Set;
import java.util.Collection;
import java.util.HashMap;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

import p.ffa.FFA;
import p.ffa.config.KitsConfig;
import p.ffa.objects.PlayerKit;

public class KitUtils 
{
    public static Map<String, PlayerKit> kits;
    private FFA plugin;
    private KitsConfig kitsconf;

    public KitUtils(FFA pl, KitsConfig kcfg)
    {
        this.plugin = pl;
        this.kitsconf = kcfg;
        kits = new HashMap<>();
        reloadKits();
    }

    public Map<String, PlayerKit> getKits() {
        return kits;
    }

    public boolean createKit(String name, ItemStack[] inv, Collection<PotionEffect> pf)
    {
        if (kits.containsKey(name)) return false;

        PlayerKit kit = new PlayerKit(name, inv, pf);
        if (!kitsconf.createKitConfigEntry(kit)) return false;
        kits.put(name, kit);
        return true;
    }

    public boolean removeKit(String kitname)
    {
        if (!kitsconf.removeKitConfigEntry(kitname)) return false;
        kits.remove(kitname);
        return true;
    }

    public boolean setPlayerKit(String k_name, Player p)
    {
        if (!kits.containsKey(k_name))
        {
            plugin.getLogger().info("Kit " + k_name + " does not exists");
            return false;
        }
        PlayerKit k = kits.get(k_name);

        p.getInventory().setContents(k.getInventoryContents());
        p.addPotionEffects(k.getEffects());
        for (PotionEffect effect : p.getActivePotionEffects()) {
            p.removePotionEffect(effect.getType());
        }
        
        return true;
    }

    public int reloadKits()
    {
        int loaded_kits = 0;
        kits.clear();
        kits.putAll(kitsconf.getKitObjects());
        loaded_kits = kits.size();
        plugin.getLogger().info(loaded_kits + " kits loaded");
        return loaded_kits;
    }

    public Set<String> getExistingKits()
    {
        return kits.keySet();
    }
}
