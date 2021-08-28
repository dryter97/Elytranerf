package co.ignitus.elytranerf.events;

import co.ignitus.elytranerf.ElytraNerf;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;

import java.util.Comparator;
import java.util.List;

public class PlayerEvents implements Listener {

    private static final ElytraNerf elytraNerf = ElytraNerf.getInstance();

    @EventHandler
    public void onFireworkUse(PlayerInteractEvent event) {
        final Player player = event.getPlayer();
        if (!player.isGliding())
            return;
        if (event.getAction() != Action.RIGHT_CLICK_AIR && event.getAction() != Action.RIGHT_CLICK_BLOCK)
            return;
        final EntityEquipment equipment = player.getEquipment();
        if (equipment == null)
            return;
        final ItemStack handItem = equipment.getItemInMainHand();
        if (handItem.getType() != Material.FIREWORK_ROCKET)
            return;
        final ItemStack chestPlate = equipment.getChestplate();
        if (chestPlate == null || chestPlate.getType() != Material.ELYTRA)
            return;
        event.setCancelled(true);
        handItem.setAmount(handItem.getAmount() - 1);
        player.setVelocity(player.getLocation().getDirection().multiply(calculateBoost(player)));
    }

    private double calculateBoost(Player player) {
        final FileConfiguration config = elytraNerf.getConfig();
        final double defaultBoost = config.getDouble("boost", 1.5);
        return player.getEffectivePermissions().stream()
                .filter(permission -> permission.getPermission().startsWith("elytranerf."))
                .map(permission -> {
                    try {
                        return Double.parseDouble(permission.getPermission().replace("elytranerf.", ""));
                    } catch (NumberFormatException ex) {
                        return 0.0;
                    }
                })
                .max(Comparator.naturalOrder())
                .orElse(defaultBoost);
    }
}
