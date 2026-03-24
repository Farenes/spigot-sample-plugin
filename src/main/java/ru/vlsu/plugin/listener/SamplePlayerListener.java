package ru.vlsu.plugin.listener;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;
import ru.vlsu.plugin.SamplePlugin;

public class SamplePlayerListener implements Listener {
    private final SamplePlugin plugin;

    public SamplePlayerListener(SamplePlugin instance) {
        plugin = instance;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        plugin.getLogger().info(event.getPlayer().getName() + " joined the server! :D");
    }

    @EventHandler
    public void onPlayerQuit(PlayerItemDamageEvent event) {
        plugin.getLogger().info(event.getPlayer().getName() + " left the server! :'(");
    }

    @EventHandler
    public void onGettingDamage(EntityDamageEvent event) {
        Entity entity = event.getEntity();
        if (entity instanceof Player) {
            Player player = (Player) entity;
            player.sendMessage("Ouuuch! I got damage of type: " + event.getDamageSource().getDamageType().getKeyOrNull());
            Vector v = new Vector(0, 1.2f, 0);
            player.setVelocity(v);
            PotionEffect effect1 = new PotionEffect(PotionEffectType.SLOW_FALLING, 60, 400);
            PotionEffect effect2 = new PotionEffect(PotionEffectType.REGENERATION, 60, 400);
            player.addPotionEffect(effect1);
            player.addPotionEffect(effect2);
            player.spawnParticle(Particle.EXPLOSION, player.getLocation(), 20);
        }
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        if (plugin.isDebugging(event.getPlayer())) {
            Location from = event.getFrom();
            Location to = event.getTo();

            plugin.getLogger().info(String.format("From %.2f,%.2f,%.2f to %.2f,%.2f,%.2f", from.getX(), from.getY(), from.getZ(), to.getX(), to.getY(), to.getZ()));
        }
    }

}
